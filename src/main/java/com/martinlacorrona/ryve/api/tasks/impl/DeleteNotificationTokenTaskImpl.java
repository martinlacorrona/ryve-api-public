package com.martinlacorrona.ryve.api.tasks.impl;

import com.martinlacorrona.ryve.api.dao.NotificationTokenDao;
import com.martinlacorrona.ryve.api.service.stationserviceupdater.StationServiceUpdateFuel;
import com.martinlacorrona.ryve.api.tasks.DeleteOldHistoryPriceTask;
import com.martinlacorrona.ryve.api.tasks.DeleteOldNotificationTokenTask;
import com.martinlacorrona.ryve.api.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@EnableAsync
@Component
public class DeleteNotificationTokenTaskImpl implements DeleteOldNotificationTokenTask {

    private static final Logger logger = LoggerFactory.getLogger(DeleteNotificationTokenTaskImpl.class);

    @Autowired
    private NotificationTokenDao notificationTokenDao;

    private final Integer DAYS_BEFORE_TO_DELETE = 30;

    /**
     * Borramos los viejos de mas de 30 días.
     * Fixed delay indica despues de cuanto tiempo despues de arrancar nuestra app en ms y cada cuanto se repite
     * 86400000ms = 24 horas
     */
    @Override
    @Async
    @Scheduled(fixedDelay = 86400000)
    public void deleteOldHistoryStationService() {
        logger.debug("[TASK] [DeleteOldNotificationTokenTask] Ejecutando el task de borrar token de notificacion de hace mas de {} días.", DAYS_BEFORE_TO_DELETE);
        Long total = notificationTokenDao.deleteByLastuseBefore(DateUtils.addRestDaysToDate(new Date(), DAYS_BEFORE_TO_DELETE));
        logger.info("[TASK] [DeleteOldNotificationTokenTask] Borrados un total de {} tokens de notificacion de antes de la fecha {}.",
                total, DateUtils.addRestDaysToDate(new Date(), DAYS_BEFORE_TO_DELETE));
    }
}
