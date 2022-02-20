package com.martinlacorrona.ryve.api.dao;

import com.martinlacorrona.ryve.api.entities.NotificationTokenEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface NotificationTokenDao extends CrudRepository<NotificationTokenEntity, Long>, JpaSpecificationExecutor<NotificationTokenEntity> {
    NotificationTokenEntity findNotificationTokenEntityByToken(String token);
    @Transactional
    Long deleteByLastuseBefore(Date dateBefore);
    List<NotificationTokenEntity> findAllByUserId(Long userId);
}
