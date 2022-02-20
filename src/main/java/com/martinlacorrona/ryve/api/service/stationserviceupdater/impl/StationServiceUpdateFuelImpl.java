package com.martinlacorrona.ryve.api.service.stationserviceupdater.impl;

import com.martinlacorrona.ryve.api.dao.HistoryStationServiceDao;
import com.martinlacorrona.ryve.api.dao.StationServiceDao;
import com.martinlacorrona.ryve.api.dao.StationServiceTypeDao;
import com.martinlacorrona.ryve.api.entities.FuelTypeEntity;
import com.martinlacorrona.ryve.api.entities.HistoryStationServiceEntity;
import com.martinlacorrona.ryve.api.entities.StationServiceEntity;
import com.martinlacorrona.ryve.api.entities.StationServiceTypeEntity;
import com.martinlacorrona.ryve.api.rest.RetrofitClientGobStationService;
import com.martinlacorrona.ryve.api.rest.StationServiceFuelAPI;
import com.martinlacorrona.ryve.api.rest.model.fuel.GobResponseAPI;
import com.martinlacorrona.ryve.api.rest.model.fuel.StationServiceFuel;
import com.martinlacorrona.ryve.api.service.StationServiceService;
import com.martinlacorrona.ryve.api.service.stationserviceupdater.FuelTypeEnum;
import com.martinlacorrona.ryve.api.service.stationserviceupdater.StationServiceUpdateFuel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StationServiceUpdateFuelImpl implements StationServiceUpdateFuel {

    private static final Logger logger = LoggerFactory.getLogger(StationServiceUpdateFuelImpl.class);

    private StationServiceFuelAPI clienteReqResAPI = RetrofitClientGobStationService.getClient(StationServiceFuelAPI.BASE_URL)
            .create(StationServiceFuelAPI.class);

    @Autowired
    private StationServiceDao stationServiceDao;
    @Autowired
    private StationServiceTypeDao stationServiceTypeDao;
    @Autowired
    private HistoryStationServiceDao historyStationServiceDao;
    @Autowired
    private StationServiceService stationServiceService;

    @Override
    public boolean update() {
        Call<GobResponseAPI> call = clienteReqResAPI.getEstaciones();
        call.enqueue(new Callback<GobResponseAPI>() {
            @Override
            public void onResponse(Call<GobResponseAPI> call, Response<GobResponseAPI> response) {
                logger.info("[STATIONSERVICE] Actualizando nuevas estaciones de servicio...");
                if (response.code() == 200) {
                    long start = System.currentTimeMillis();
                    GobResponseAPI data = response.body();
                    List<StationServiceFuel> estaciones = data.getListaEESSPrecio();
                    StationServiceTypeEntity typeEntity = stationServiceTypeDao.findById(1L).get(); //1 combustible
                    List<HistoryStationServiceEntity> listHistoryNews = new ArrayList<>();
                    List<StationServiceEntity> listStationNews = new ArrayList<>();
                    estaciones.stream().parallel().forEach(ss -> {
                        //Chequeamos que no existe
                        StationServiceEntity stationServiceEntity =
                                stationServiceDao.findByIdStationApi(Long.parseLong(ss.iDEESS));
                        if(stationServiceEntity == null) {
                            //Primero convertirla a Estacion, si no existe lo creamos
                            stationServiceEntity = stationServiceDao.save(convertStationServiceFuelToEntity(ss, typeEntity));
                            logger.debug(
                                    "[STATIONSERVICE] Estacion {} detectada como nueva y guardada en la base de datos.",
                                    ss.rotulo);
                        }

                        //Segundo, guardar los precios
                        insertOrUpdateHistoryStationServicePrice(ss, stationServiceEntity);
                    });
                    long end = System.currentTimeMillis();
                    float sec = (end - start) / 1000F;
                    stationServiceService.updateCache();
                    logger.info("[STATIONSERVICE] Acabada agregar nuevas de estaciones de servicio y sus precio de hoy." +
                            "(Duracion: {} segundos)", sec);
                } else {
                    logger.info("[STATIONSERVICE] No ha habido una respuesta 200 de la API del gobierno de gasolineras.");
                    call.cancel();
                }
            }

            @Override
            public void onFailure(Call<GobResponseAPI> call, Throwable t) {
                logger.error("[STATIONSERVICE] Ha fallado la API del gobierno. ERROR: {}", t.toString());
            }
        });
        return false;
    }

    @Override
    public Long deleteDateBefore(Date dateBefore) {
        return historyStationServiceDao.deleteByDateBefore(dateBefore);
    }

    private void insertOrUpdateHistoryStationServicePrice(StationServiceFuel ss, StationServiceEntity stationServiceEntity) {
        Date now = new Date();
        List<HistoryStationServiceEntity> list = getAllHistoryStationServiceEntity(ss, stationServiceEntity, now);
        list.stream().parallel().forEach(
                historyStationServiceEntity -> {
                    historyStationServiceDao.deleteByStationServiceAndDateAndFuelType(
                            historyStationServiceEntity.getStationService(),
                            now, historyStationServiceEntity.getFuelType());
                }
        );
        historyStationServiceDao.saveAll(list);
    }

    private List<HistoryStationServiceEntity> getAllHistoryStationServiceEntity
            (StationServiceFuel stationServiceFuel, StationServiceEntity stationServiceEntity, Date now) {
        List<HistoryStationServiceEntity> historyStationServiceEntityList = new ArrayList<>();
        //Add all type of fuels
        addToListIfMeetRequirements(stationServiceFuel.getPrecioBiodiesel(), FuelTypeEnum.BIODIESEL,
                historyStationServiceEntityList, now, stationServiceEntity);
        addToListIfMeetRequirements(stationServiceFuel.getPrecioBioetanol(), FuelTypeEnum.BIOETANOL,
                historyStationServiceEntityList, now, stationServiceEntity);
        addToListIfMeetRequirements(stationServiceFuel.getPrecioGasNaturalComprimido(), FuelTypeEnum.GAS_NATURAL_COMPRIMIDO,
                historyStationServiceEntityList, now, stationServiceEntity);
        addToListIfMeetRequirements(stationServiceFuel.getPrecioGasNaturalLicuado(), FuelTypeEnum.GAS_NATURAL_LICUADO,
                historyStationServiceEntityList, now, stationServiceEntity);
        addToListIfMeetRequirements(stationServiceFuel.getPrecioGaseslicuadosdelpetroleo(), FuelTypeEnum.GASES_LICUADOS_DEL_PETROLEO,
                historyStationServiceEntityList, now, stationServiceEntity);
        addToListIfMeetRequirements(stationServiceFuel.getPrecioGasoleoA(), FuelTypeEnum.GASOLEO_A,
                historyStationServiceEntityList, now, stationServiceEntity);
        addToListIfMeetRequirements(stationServiceFuel.getPrecioGasoleoB(), FuelTypeEnum.GASOLEO_B,
                historyStationServiceEntityList, now, stationServiceEntity);
        addToListIfMeetRequirements(stationServiceFuel.getPrecioGasoleoPremium(), FuelTypeEnum.GASOLEO_PREMIUM,
                historyStationServiceEntityList, now, stationServiceEntity);
        addToListIfMeetRequirements(stationServiceFuel.getPrecioGasolina95E10(), FuelTypeEnum.GASOLINA_95_E10,
                historyStationServiceEntityList, now, stationServiceEntity);
        addToListIfMeetRequirements(stationServiceFuel.getPrecioGasolina95E5(), FuelTypeEnum.GASOLINA_95_E5,
                historyStationServiceEntityList, now, stationServiceEntity);
        addToListIfMeetRequirements(stationServiceFuel.getPrecioGasolina95E5Premium(), FuelTypeEnum.GASOLINA_95_E5_PREMIUM,
                historyStationServiceEntityList, now, stationServiceEntity);
        addToListIfMeetRequirements(stationServiceFuel.getPrecioGasolina98E10(), FuelTypeEnum.GASOLINA_98_E10,
                historyStationServiceEntityList, now, stationServiceEntity);
        addToListIfMeetRequirements(stationServiceFuel.getPrecioGasolina98E5(), FuelTypeEnum.GASOLINA_98_E5,
                historyStationServiceEntityList, now, stationServiceEntity);
        addToListIfMeetRequirements(stationServiceFuel.getPrecioHidrogeno(), FuelTypeEnum.HIDROGENO,
                historyStationServiceEntityList, now, stationServiceEntity);

        return historyStationServiceEntityList;
    }

    private void addToListIfMeetRequirements(String price, FuelTypeEnum fuelTypeEnum,
                                             List<HistoryStationServiceEntity> historyStationServiceEntityList,
                                             Date now, StationServiceEntity stationServiceEntity) {
        if(isNotNullOrVoid(price))
            historyStationServiceEntityList.add(getHistoryEntityFromPrice(stationServiceEntity,
                    convertNumberWrittedInStringWithCommansToDouble(price),
                    fuelTypeEnum, now));
    }

    private HistoryStationServiceEntity getHistoryEntityFromPrice(StationServiceEntity stationServiceEntity,
                                                                  Double price, FuelTypeEnum fuelTypeEnum, Date now) {
        return HistoryStationServiceEntity.builder()
                .stationService(stationServiceEntity)
                .price(price)
                .fuelType(FuelTypeEntity.builder().id(fuelTypeEnum.getValue()).build())
                .date(now)
                .datetime(now)
                .build();
    }

    /**
     * Chequeamos que el string no es ni null ni vacio.
     * Nos aseguramos de esto por que no nos fiamos del back del gobierno.
     * @param stringToCheck
     * @return
     */
    private boolean isNotNullOrVoid(String stringToCheck) {
        return stringToCheck != null && stringToCheck != "";
    }

    /**
     * La API del estado
     * @param stringNumber
     * @return
     */
    private Double convertNumberWrittedInStringWithCommansToDouble(String stringNumber) {
        return Double.parseDouble(stringNumber.replace(",", "."));
    }

    private StationServiceEntity convertStationServiceFuelToEntity(StationServiceFuel ss, StationServiceTypeEntity typeEntity) {
        return StationServiceEntity.builder()
                .name(ss.rotulo)
                .longitude(convertNumberWrittedInStringWithCommansToDouble(ss.longitudWGS84))
                .latitude(convertNumberWrittedInStringWithCommansToDouble(ss.latitud))
                .schedule(ss.horario)
                .postalCode(ss.cP)
                .address(ss.direccion)
                .town(ss.localidad)
                .municipality(ss.municipio)
                .district(ss.provincia)
                .idMunicipality(Integer.parseInt(ss.iDMunicipio))
                .idDistrict(Integer.parseInt(ss.iDProvincia))
                .idCCAA(Integer.parseInt(ss.iDCCAA))
                .idStationApi(Long.parseLong(ss.iDEESS))
                .stationServiceType(typeEntity)
                .build();
    }
}
