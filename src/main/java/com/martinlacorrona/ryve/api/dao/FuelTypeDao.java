package com.martinlacorrona.ryve.api.dao;

import com.martinlacorrona.ryve.api.entities.FuelTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuelTypeDao extends CrudRepository<FuelTypeEntity, Long>, JpaSpecificationExecutor<FuelTypeEntity> {
    List<FuelTypeEntity> findAllByEnabledEquals(boolean equals);
}
