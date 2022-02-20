package com.martinlacorrona.ryve.api.service.impl;

import com.martinlacorrona.ryve.api.dao.StationServiceDao;
import com.martinlacorrona.ryve.api.dao.UserFavouriteStationServiceDao;
import com.martinlacorrona.ryve.api.entities.StationServiceEntity;
import com.martinlacorrona.ryve.api.entities.UserEntity;
import com.martinlacorrona.ryve.api.entities.UserFavouriteStationServiceEntity;
import com.martinlacorrona.ryve.api.entities.UserPreferencesEntity;
import com.martinlacorrona.ryve.api.exception.ErrorMessage;
import com.martinlacorrona.ryve.api.exception.RestException;
import com.martinlacorrona.ryve.api.model.FavouriteStationServiceModel;
import com.martinlacorrona.ryve.api.model.HistoryStationServiceModel;
import com.martinlacorrona.ryve.api.model.UserPreferencesModel;
import com.martinlacorrona.ryve.api.security.DataUserSecurity;
import com.martinlacorrona.ryve.api.service.StationServiceService;
import com.martinlacorrona.ryve.api.service.UserFavouriteStationService;
import com.martinlacorrona.ryve.api.service.UserPreferencesService;
import com.martinlacorrona.ryve.api.utils.ModelMapperExtended;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserFavouriteServiceImpl implements UserFavouriteStationService {

    @Autowired
    private DataUserSecurity dataUserSecurity;

    @Autowired
    private StationServiceDao stationServiceDao;

    @Autowired
    private UserFavouriteStationServiceDao userFavouriteStationServiceDao;

    @Autowired
    private UserPreferencesService userPreferencesService;

    @Autowired
    private StationServiceService stationServiceService;

    //Mapper
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<FavouriteStationServiceModel> getFavourites() {
        UserEntity userEntity = dataUserSecurity.getUserEntity();
        List<UserFavouriteStationServiceEntity> favourites =
                userFavouriteStationServiceDao.findByUserId(userEntity.getId());
        List<StationServiceEntity> favouritesStations =
                favourites.stream().parallel()
                    .map(UserFavouriteStationServiceEntity::getStationService)
                    .collect(Collectors.toList());

        List<FavouriteStationServiceModel> favouriteStationServiceModels =
                ModelMapperExtended.mapList(favouritesStations, FavouriteStationServiceModel.class, modelMapper);
        Long favouriteFuelId = userPreferencesService.get().getFavouriteFuel().getId();
        favouriteStationServiceModels.forEach(favouriteStationServiceModel -> {
            HistoryStationServiceModel historyStationServiceFromLastestById = stationServiceService
                    .getHistoryStationServiceFromLastestById(favouriteStationServiceModel.getId(),
                            favouriteFuelId);
            if(historyStationServiceFromLastestById != null) {
                favouriteStationServiceModel.setPrice(historyStationServiceFromLastestById.getPrice());
            }
        });

        return favouriteStationServiceModels;
    }

    @Override
    public List<FavouriteStationServiceModel> addFavourite(Long idStationService) {
        UserEntity userEntity = dataUserSecurity.getUserEntity();
        //Chequeamos que no exista ya y la creamos
        if(userFavouriteStationServiceDao.findByUserIdAndStationServiceId(userEntity.getId(), idStationService) != null)
            throw new RestException(HttpStatus.BAD_REQUEST, ErrorMessage.YOU_ALREADY_HAVE_THIS_FAVOURITE);
        if(!stationServiceDao.findById(idStationService).isPresent())
            throw new RestException(HttpStatus.BAD_REQUEST, ErrorMessage.STATION_SERVICE_DOESNT_EXIST);
        userFavouriteStationServiceDao.save(
                UserFavouriteStationServiceEntity.builder()
                        .user(userEntity)
                        .stationService(stationServiceDao.findById(idStationService).get())
                        .build()
        );
        return getFavourites();
    }

    @Override
    public List<FavouriteStationServiceModel> removeFavourite(Long idStationService) {
        UserEntity userEntity = dataUserSecurity.getUserEntity();
        //Chequeamos que lo tenga como favorito
        if(userFavouriteStationServiceDao.findByUserIdAndStationServiceId(userEntity.getId(), idStationService) == null)
            throw new RestException(HttpStatus.BAD_REQUEST, ErrorMessage.YOU_DONT_HAVE_THIS_FAVOURITE);
        userFavouriteStationServiceDao.deleteByUserIdAndStationServiceId(userEntity.getId(), idStationService);
        return getFavourites();
    }
}
