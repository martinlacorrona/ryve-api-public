package com.martinlacorrona.ryve.api.dao;

import com.martinlacorrona.ryve.api.entities.StationServiceEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationServiceDao extends CrudRepository<StationServiceEntity, Long>, JpaSpecificationExecutor<StationServiceEntity> {

    StationServiceEntity findByIdStationApi(Long id);

}
