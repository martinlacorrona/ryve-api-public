package com.martinlacorrona.ryve.api.service;

import com.martinlacorrona.ryve.api.model.SubscribeNotificationModel;

import java.util.List;

public interface SubscribeNotificationService {

    /**
     * Devuelve la lista a la cual esta suscrito el usuario
     * @return Lista de suscripciones
     */
    List<SubscribeNotificationModel> get();

    /**
     * Suscribirse a una estacion de servicio.
     * @param stationServiceId id de la estacion
     * @param fuelTypeId fuel al que nos suscribimos
     * @param periodInDays cada cuantos dias queremos que nos notifique
     * @return la lista de las suscripciones actuales
     */
    List<SubscribeNotificationModel> suscribeStationServiceByIdAndFuelIdAndPeriod(Long stationServiceId, Long fuelTypeId,
                                                                                  int periodInDays);

    /**
     * Usuario de desubscribe de una de sus subscripciones
     * @param subscribeNotificationId id de las subscriocion que quiere dejar
     * @return la lista de las suscripciones actuales
     */
    List<SubscribeNotificationModel> unsuscribeBySubscribeNotificationId(Long subscribeNotificationId);
}
