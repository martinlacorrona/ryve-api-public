package com.martinlacorrona.ryve.api.dao;

import com.martinlacorrona.ryve.api.entities.UserPreferencesEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferencesDao extends CrudRepository<UserPreferencesEntity, Long>, JpaSpecificationExecutor<UserPreferencesEntity> {

    UserPreferencesEntity findByUserId(Long userId);

}
