package com.martinlacorrona.ryve.api.service.impl;

import com.martinlacorrona.ryve.api.dao.UserPreferencesDao;
import com.martinlacorrona.ryve.api.entities.FuelTypeEntity;
import com.martinlacorrona.ryve.api.entities.UserEntity;
import com.martinlacorrona.ryve.api.entities.UserPreferencesEntity;
import com.martinlacorrona.ryve.api.model.UserPreferencesModel;
import com.martinlacorrona.ryve.api.security.DataUserSecurity;
import com.martinlacorrona.ryve.api.service.UserPreferencesService;
import com.martinlacorrona.ryve.api.service.stationserviceupdater.FuelTypeEnum;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserPreferencesServiceImpl implements UserPreferencesService {

    @Autowired
    private UserPreferencesDao userPreferencesDao;

    @Autowired
    private DataUserSecurity dataUserSecurity;

    //Mapper
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserPreferencesModel get() {
        UserEntity userEntity = dataUserSecurity.getUserEntity();

        UserPreferencesEntity userPreferencesEntity = userPreferencesDao.findByUserId(userEntity.getId());
        if(userPreferencesEntity == null) { //Si no esta creado se crea
            userPreferencesEntity = UserPreferencesEntity.builder()
                    .user(userEntity)
                    .favouriteFuel(FuelTypeEntity.builder().id(FuelTypeEnum.GASOLEO_A.getValue()).build())
                    .carcolor("")
                    .carname("")
                    .kmRange(200.0)
                    .build();

            userPreferencesEntity = userPreferencesDao.save(userPreferencesEntity);
        }

        return modelMapper.map(userPreferencesEntity, UserPreferencesModel.class);
    }

    @Override
    public UserPreferencesModel update(UserPreferencesModel userPreferencesModel) {
        UserEntity userEntity = dataUserSecurity.getUserEntity();

        UserPreferencesEntity userPreferencesEntity = userPreferencesDao.findByUserId(userEntity.getId());
        if(userPreferencesEntity == null) { //Si no esta creado se crea con lo que nos pasa
            userPreferencesEntity = UserPreferencesEntity.builder()
                    .user(userEntity)
                    .carname(userPreferencesModel.getCarname())
                    .carcolor(userPreferencesModel.getCarcolor())
                    .kmRange(userPreferencesModel.getKmRange())
                    .favouriteFuel(modelMapper.map(userPreferencesModel.getFavouriteFuel(), FuelTypeEntity.class))
                    .build();
            userPreferencesEntity = userPreferencesDao.save(userPreferencesEntity);
        } else {
            userPreferencesEntity.setCarname(userPreferencesModel.getCarname());
            userPreferencesEntity.setCarcolor(userPreferencesModel.getCarcolor());
            userPreferencesEntity.setKmRange(userPreferencesModel.getKmRange());
            userPreferencesEntity.setFavouriteFuel(modelMapper.map(userPreferencesModel.getFavouriteFuel(), FuelTypeEntity.class));
            userPreferencesEntity = userPreferencesDao.save(userPreferencesEntity);
        }

        return modelMapper.map(userPreferencesEntity, UserPreferencesModel.class);
    }
}
