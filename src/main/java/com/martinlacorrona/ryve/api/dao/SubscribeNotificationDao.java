package com.martinlacorrona.ryve.api.dao;

import com.martinlacorrona.ryve.api.entities.SubscribeNotificationEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SubscribeNotificationDao extends CrudRepository<SubscribeNotificationEntity, Long>, JpaSpecificationExecutor<SubscribeNotificationEntity> {

    List<SubscribeNotificationEntity> findAllByUserId(Long userId);
    SubscribeNotificationEntity findByUserIdAndStationServiceIdAndPeriodInDays(Long userId, Long stationServiceId, Integer periodInDays);
    List<SubscribeNotificationEntity> findAllByNextNotifyLessThanEqual(Date nextNotify);

}
