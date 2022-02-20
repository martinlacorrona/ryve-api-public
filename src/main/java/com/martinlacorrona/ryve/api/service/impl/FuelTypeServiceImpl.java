package com.martinlacorrona.ryve.api.service.impl;

import com.martinlacorrona.ryve.api.dao.FuelTypeDao;
import com.martinlacorrona.ryve.api.entities.FuelTypeEntity;
import com.martinlacorrona.ryve.api.model.FuelTypeModel;
import com.martinlacorrona.ryve.api.service.FuelTypeService;
import com.martinlacorrona.ryve.api.utils.ModelMapperExtended;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FuelTypeServiceImpl implements FuelTypeService {

    @Autowired
    private FuelTypeDao fuelTypeDao;

    //Mapper
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<FuelTypeModel> getAll() {
        List<FuelTypeEntity> allFuelsType = new ArrayList<>();
        fuelTypeDao.findAll().forEach(allFuelsType::add);
        return ModelMapperExtended.mapList(allFuelsType, FuelTypeModel.class, modelMapper);
    }

    @Override
    public List<FuelTypeModel> getEnabledFuelTypes() {
        List<FuelTypeEntity> allFuelsType = new ArrayList<>();
        fuelTypeDao.findAllByEnabledEquals(true).forEach(allFuelsType::add);
        return ModelMapperExtended.mapList(allFuelsType, FuelTypeModel.class, modelMapper);
    }
}
