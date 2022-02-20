package com.martinlacorrona.ryve.api.dao;

import com.martinlacorrona.ryve.api.entities.StationServiceTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationServiceTypeDao extends CrudRepository<StationServiceTypeEntity, Long>, JpaSpecificationExecutor<StationServiceTypeEntity> {

}
