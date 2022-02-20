package com.martinlacorrona.ryve.api.service.impl;

import com.martinlacorrona.ryve.api.dao.FuelTypeDao;
import com.martinlacorrona.ryve.api.dao.StationServiceDao;
import com.martinlacorrona.ryve.api.dao.SubscribeNotificationDao;
import com.martinlacorrona.ryve.api.entities.FuelTypeEntity;
import com.martinlacorrona.ryve.api.entities.StationServiceEntity;
import com.martinlacorrona.ryve.api.entities.SubscribeNotificationEntity;
import com.martinlacorrona.ryve.api.entities.UserEntity;
import com.martinlacorrona.ryve.api.exception.ErrorMessage;
import com.martinlacorrona.ryve.api.exception.RestException;
import com.martinlacorrona.ryve.api.model.SubscribeNotificationModel;
import com.martinlacorrona.ryve.api.security.DataUserSecurity;
import com.martinlacorrona.ryve.api.service.SubscribeNotificationService;
import com.martinlacorrona.ryve.api.utils.ModelMapperExtended;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubscribeNotificationServiceImpl implements SubscribeNotificationService {

    @Autowired
    private DataUserSecurity dataUserSecurity;

    @Autowired
    private SubscribeNotificationDao subscribeNotificationDao;

    @Autowired
    private StationServiceDao stationServiceDao;

    @Autowired
    private FuelTypeDao fuelTypeDao;

    //Mapper
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<SubscribeNotificationModel> get() {
        UserEntity userEntity = dataUserSecurity.getUserEntity();

        return ModelMapperExtended.mapList(subscribeNotificationDao.findAllByUserId(userEntity.getId()),
                SubscribeNotificationModel.class, modelMapper);
    }

    @Override
    public List<SubscribeNotificationModel> suscribeStationServiceByIdAndFuelIdAndPeriod(Long stationServiceId,
                                                                                         Long fuelTypeId, int periodInDays) {
        UserEntity userEntity = dataUserSecurity.getUserEntity();

        if(periodInDays < 1 || periodInDays > 30)
            throw new RestException(HttpStatus.BAD_REQUEST, ErrorMessage.MIN_AND_MAX_PERIOD_IN_DAYS);
        Optional<FuelTypeEntity> fuelTypeEntity = fuelTypeDao.findById(fuelTypeId);
        if(!fuelTypeEntity.isPresent())
            throw new RestException(HttpStatus.BAD_REQUEST, ErrorMessage.FUEL_TYPE_DOESNT_EXIST);
        Optional<StationServiceEntity> stationServiceEntity = stationServiceDao.findById(stationServiceId);
        if(!stationServiceEntity.isPresent())
            throw new RestException(HttpStatus.BAD_REQUEST, ErrorMessage.STATION_SERVICE_DOESNT_EXIST);
        if(subscribeNotificationDao
                .findByUserIdAndStationServiceIdAndPeriodInDays(userEntity.getId(), stationServiceId, periodInDays) != null) {
            throw new RestException(HttpStatus.BAD_REQUEST, ErrorMessage.CANNOT_CREATE_SIMILAR_ALERT);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, periodInDays);
        Date datePeriodInDays = calendar.getTime();
        subscribeNotificationDao.save(
                SubscribeNotificationEntity.builder()
                        .stationService(stationServiceEntity.get())
                        .user(userEntity)
                        .fuelType(fuelTypeEntity.get())
                        .lastNotified(new Date())
                        .nextNotify(datePeriodInDays)
                        .periodInDays(periodInDays)
                        .status(true)
                        .build()
        );

        return get();
    }

    @Override
    public List<SubscribeNotificationModel> unsuscribeBySubscribeNotificationId(Long subscribeNotificationId) {
        UserEntity userEntity = dataUserSecurity.getUserEntity();

        Optional<SubscribeNotificationEntity> subscribeNotificationEntity = subscribeNotificationDao.findById(subscribeNotificationId);
        if(!subscribeNotificationEntity.isPresent())
            throw new RestException(HttpStatus.BAD_REQUEST, ErrorMessage.CANNOT_DELETE_ALERT_THAT_DOESNT_EXIST);
        if(subscribeNotificationEntity.get().getUser().getId() != userEntity.getId())
            throw new RestException(HttpStatus.BAD_REQUEST, ErrorMessage.CANNOT_DELETE_ALERT_THAT_ISNT_YOURTS);

        subscribeNotificationDao.deleteById(subscribeNotificationId);

        return get();
    }
}
