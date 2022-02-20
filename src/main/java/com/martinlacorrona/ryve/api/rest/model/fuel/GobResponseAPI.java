package com.martinlacorrona.ryve.api.rest.model.fuel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GobResponseAPI {

    @JsonProperty("Fecha")
    public String fecha;
    @JsonProperty("ListaEESSPrecio")
    public List<StationServiceFuel> listaEESSPrecio;
    @JsonProperty("Nota")
    public String nota;
    @JsonProperty("ResultadoConsulta")
    public String resultadoConsulta;

}
