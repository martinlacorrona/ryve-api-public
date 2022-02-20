package com.martinlacorrona.ryve.api.dao;

import com.martinlacorrona.ryve.api.entities.UserFavouriteStationServiceEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFavouriteStationServiceDao extends CrudRepository<UserFavouriteStationServiceEntity, Long>, JpaSpecificationExecutor<UserFavouriteStationServiceEntity> {
    List<UserFavouriteStationServiceEntity> findByUserId(Long userId);
    UserFavouriteStationServiceEntity findByUserIdAndStationServiceId(Long userId, Long stationServiceId);
    void deleteByUserIdAndStationServiceId(Long userId, Long stationServiceId);
}
