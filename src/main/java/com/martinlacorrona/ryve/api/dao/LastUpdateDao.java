package com.martinlacorrona.ryve.api.dao;

import com.martinlacorrona.ryve.api.entities.LastUpdateEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastUpdateDao extends CrudRepository<LastUpdateEntity, Long>, JpaSpecificationExecutor<LastUpdateEntity> {

}
