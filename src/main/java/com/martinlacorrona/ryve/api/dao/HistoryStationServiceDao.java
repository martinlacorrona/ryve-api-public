package com.martinlacorrona.ryve.api.dao;

import com.martinlacorrona.ryve.api.entities.FuelTypeEntity;
import com.martinlacorrona.ryve.api.entities.HistoryStationServiceEntity;
import com.martinlacorrona.ryve.api.entities.StationServiceEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository@Transactional
public interface HistoryStationServiceDao extends CrudRepository<HistoryStationServiceEntity, Long>, JpaSpecificationExecutor<HistoryStationServiceEntity> {
    void deleteByStationServiceAndDateAndFuelType(StationServiceEntity stationService, Date date, FuelTypeEntity fuelType);
    Long deleteByDateBefore(Date dateBefore);
    List<HistoryStationServiceEntity> findAllByDateEqualsAndFuelTypeId(Date date, Long fuelTypeId);
    List<HistoryStationServiceEntity> findAllByDateEqualsAndFuelTypeIdAndStationServiceIdCCAA(Date date, Long fuelTypeId,
                                                                                              Integer idCCAA);
    List<HistoryStationServiceEntity> findAllByFuelTypeIdAndStationServiceIdAndDateAfter(Long fuelTypeId,
                                                                                         Long stationServiceId,
                                                                                         Date dateBefore);
    HistoryStationServiceEntity findAllByFuelTypeIdAndStationServiceIdAndDate(Long fuelTypeId,
                                                                                         Long stationServiceId,
                                                                                         Date date);
}
