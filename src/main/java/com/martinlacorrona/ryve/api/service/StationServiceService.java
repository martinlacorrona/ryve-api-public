package com.martinlacorrona.ryve.api.service;

import com.martinlacorrona.ryve.api.model.HistoryStationServiceModel;
import com.martinlacorrona.ryve.api.model.StationServiceModel;
import com.martinlacorrona.ryve.api.model.reduced.HistoryStationServiceModelReduced;

import java.util.List;

public interface StationServiceService {
    /**
     * Todas las estaciones
     * @return todas las estacione de servicio
     */
    List<StationServiceModel> getAllStationService();

    /**
     * Devuelve los historicos por tipo de fuel
     * @param fuelTypeId Tipo de fuel
     * @return lista de Histiricos de estaciones de modelo reducido para ir mas rápido
     */
    List<HistoryStationServiceModelReduced> getAllHistoryStationServiceLastestByFuelType(Long fuelTypeId);

    /**
     * Devuelve los historicos por tipo de fuel y comunidad autonoma
     * @param fuelTypeId tipo de fuel
     * @param idCCAA comunida autonoma, los ids estan en la bbdd
     * @return lista de Histiricos de estaciones de modelo reducido para ir mas rápido
     */
    List<HistoryStationServiceModelReduced> getAllHistoryStationServiceFromLastestByFuelTypeAndCCAA(Long fuelTypeId, Integer idCCAA);

    /**
     * Devuelve todos los hisoricos de una estacion en los ultimos 30 dias
     * @param stationServiceId id estacion de servicio
     * @param fuelTypeId tipo de fuel
     * @return los historicos de modelo reducido
     */
    List<HistoryStationServiceModelReduced> getHistoryStationServiceHistoryByIdAndFuelTypeIdLast30Days
            (Long stationServiceId, Long fuelTypeId);

    /**
     * Devuelve la ultima station service por id y fueltype
     * @param id id de estacion
     * @param fuelTypeId fuelType el id
     * @return la que encuentra haciendo la consulta
     */
    HistoryStationServiceModel getHistoryStationServiceFromLastestById(Long id, Long fuelTypeId);

    /**
     * Actualiza la cache, este metodo es ejecutado por la task cuando tiene descargadas las nuevas
     */
    void updateCache();
}
