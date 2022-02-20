package com.martinlacorrona.ryve.api.service.stationserviceupdater;

import java.util.Date;

public interface StationServiceUpdate {
    /**
     * Funcion que actualiza la base de datos del servicio.
     * @return si se actualizo correctamente
     */
    boolean update();

    Long deleteDateBefore(Date dateBefore);
}
