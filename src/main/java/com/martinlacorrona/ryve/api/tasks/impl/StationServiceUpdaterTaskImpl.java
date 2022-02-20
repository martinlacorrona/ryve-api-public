package com.martinlacorrona.ryve.api.tasks.impl;


import com.martinlacorrona.ryve.api.dao.LastUpdateDao;
import com.martinlacorrona.ryve.api.entities.LastUpdateEntity;
import com.martinlacorrona.ryve.api.tasks.StationServiceUpdaterTask;
import com.martinlacorrona.ryve.api.service.stationserviceupdater.StationServiceUpdateFuel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@EnableAsync
@Component
public class StationServiceUpdaterTaskImpl implements StationServiceUpdaterTask {

    private static final Logger logger = LoggerFactory.getLogger(StationServiceUpdaterTaskImpl.class);

    @Autowired
    private StationServiceUpdateFuel stationServiceUpdateFuel;

    @Autowired
    private LastUpdateDao lastUpdateDao;

    /**
     * Actualizamos todos los station service definidos.
     * Fixed delay indica despues de cuanto tiempo despues de arrancar nuestra app en ms y cada cuanto se repite
     * 3600000ms = 1 hora
     */
    @Override
    @Async
    @Transactional
    @Scheduled(fixedDelay = 3600000)
    public void update() {
        logger.debug("[TASK] [StationServiceUpdaterTask] Ejecutando el task de actualizar los precios de station service cada 1 hora.");
        stationServiceUpdateFuel.update();
        saveLastUpdateInDB();
    }

    /**
     * Guardamos la ultima fecha de actualizacion en la base de datos.
     */
    private void saveLastUpdateInDB() {
        Date now = new Date();
        lastUpdateDao.save(LastUpdateEntity.builder()
                .id(1L)
                .datetime(now)
                .build());
    }
}
