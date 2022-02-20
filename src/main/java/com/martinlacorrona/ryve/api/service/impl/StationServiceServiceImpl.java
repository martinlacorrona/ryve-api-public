package com.martinlacorrona.ryve.api.service.impl;

import com.martinlacorrona.ryve.api.dao.HistoryStationServiceDao;
import com.martinlacorrona.ryve.api.dao.LastUpdateDao;
import com.martinlacorrona.ryve.api.dao.StationServiceDao;
import com.martinlacorrona.ryve.api.entities.HistoryStationServiceEntity;
import com.martinlacorrona.ryve.api.entities.StationServiceEntity;
import com.martinlacorrona.ryve.api.exception.ErrorMessage;
import com.martinlacorrona.ryve.api.exception.RestException;
import com.martinlacorrona.ryve.api.model.HistoryStationServiceModel;
import com.martinlacorrona.ryve.api.model.StationServiceModel;
import com.martinlacorrona.ryve.api.model.reduced.HistoryStationServiceModelReduced;
import com.martinlacorrona.ryve.api.service.FuelTypeService;
import com.martinlacorrona.ryve.api.service.StationServiceService;
import com.martinlacorrona.ryve.api.utils.DateUtils;
import com.martinlacorrona.ryve.api.utils.ModelMapperExtended;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class StationServiceServiceImpl implements StationServiceService {

    private static final Logger logger = LoggerFactory.getLogger(StationServiceServiceImpl.class);

    @Autowired
    private StationServiceDao stationServiceDao;

    @Autowired
    private HistoryStationServiceDao historyStationServiceDao;

    @Autowired
    private FuelTypeService fuelTypeService;

    @Autowired
    private LastUpdateDao lastUpdateDao;

    //Mapper
    private ModelMapper modelMapper = new ModelMapper();

    //Cache (id de fuel y lista de estaciones)
    private HashMap<Long, List<HistoryStationServiceModelReduced>> cacheHistory = new HashMap();
    //Cache (lista de estaciones)
    private List<StationServiceModel> cacheStationService = new ArrayList<>();

    @Override
    public List<StationServiceModel> getAllStationService() {
        if (!cacheStationService.isEmpty()) { //Comprobamos si hay en la cache
            return cacheStationService;
        }
        List<StationServiceEntity> allStationService = new ArrayList<>();
        stationServiceDao.findAll().forEach(allStationService::add);
        cacheStationService.addAll(ModelMapperExtended.mapList(allStationService, StationServiceModel.class, modelMapper));
        return cacheStationService;
    }

    @Override
    public List<HistoryStationServiceModelReduced> getAllHistoryStationServiceLastestByFuelType(Long fuelTypeId) {
        if (cacheHistory.containsKey(fuelTypeId)) { //Comprobamos si hay en cache
            return cacheHistory.get(fuelTypeId);
        }

        Date lastUpdate = lastUpdateDao.findById(1L).get().getDatetime();
        List<HistoryStationServiceEntity> historyStationServiceToday =
                historyStationServiceDao.findAllByDateEqualsAndFuelTypeId(lastUpdate, fuelTypeId);

        cacheHistory.put(fuelTypeId, ModelMapperExtended.mapList(historyStationServiceToday, HistoryStationServiceModelReduced.class, modelMapper));

        return cacheHistory.get(fuelTypeId);
    }

    @Override
    public List<HistoryStationServiceModelReduced> getAllHistoryStationServiceFromLastestByFuelTypeAndCCAA(Long fuelTypeId, Integer idCCAA) {
        Date lastUpdate = lastUpdateDao.findById(1L).get().getDatetime();
        List<HistoryStationServiceEntity> historyStationServiceToday =
                historyStationServiceDao.findAllByDateEqualsAndFuelTypeIdAndStationServiceIdCCAA(lastUpdate, fuelTypeId, idCCAA);

        return ModelMapperExtended.mapList(historyStationServiceToday, HistoryStationServiceModelReduced.class, modelMapper);
    }

    @Override
    public HistoryStationServiceModel getHistoryStationServiceFromLastestById(Long idStationService, Long fuelTypeId) {
        HistoryStationServiceEntity byId = historyStationServiceDao
                .findAllByFuelTypeIdAndStationServiceIdAndDate(fuelTypeId, idStationService, new Date());
        if (byId != null)
            return modelMapper.map(byId, HistoryStationServiceModel.class);
        return null;
    }

    @Override
    public List<HistoryStationServiceModelReduced> getHistoryStationServiceHistoryByIdAndFuelTypeIdLast30Days
            (Long stationServiceId, Long fuelTypeId) {
        Date lastUpdate = lastUpdateDao.findById(1L).get().getDatetime();
        List<HistoryStationServiceEntity> historyStationService =
                historyStationServiceDao.findAllByFuelTypeIdAndStationServiceIdAndDateAfter(
                        fuelTypeId, stationServiceId, DateUtils.addRestDaysToDate(lastUpdate, 30));

        if (historyStationService.isEmpty())
            throw new RestException(HttpStatus.BAD_REQUEST, ErrorMessage.NOT_RESULT_FOUND);

        return ModelMapperExtended.mapList(historyStationService, HistoryStationServiceModelReduced.class, modelMapper);
    }

    @Override
    public void updateCache() {
        logger.debug("Updating cache...");
        //Actualizamos cache de historico de estaciones para todos los combustibles
        Date lastUpdate = lastUpdateDao.findById(1L).get().getDatetime();
        fuelTypeService.getAll().stream().parallel().forEach(fuelType -> {
            cacheHistory.put(fuelType.getId(),
                    ModelMapperExtended.mapList(historyStationServiceDao
                    .findAllByDateEqualsAndFuelTypeId(lastUpdate, fuelType.getId()),
                            HistoryStationServiceModelReduced.class, modelMapper));
        });

        //Actualizamos la cache para las estaciones de servicio
        List<StationServiceEntity> allStationService = new ArrayList<>();
        stationServiceDao.findAll().forEach(allStationService::add);
        cacheStationService.clear();
        cacheStationService
                .addAll(ModelMapperExtended.mapList(allStationService, StationServiceModel.class, modelMapper));
        logger.debug("Cache has been updated.");
    }
}
