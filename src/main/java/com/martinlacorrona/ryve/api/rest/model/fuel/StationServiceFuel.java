package com.martinlacorrona.ryve.api.rest.model.fuel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data public class StationServiceFuel {
    @JsonProperty("C.P.")
    public String cP;
    @JsonProperty("Dirección")
    public String direccion;
    @JsonProperty("Horario")
    public String horario;
    @JsonProperty("Latitud")
    public String latitud;
    @JsonProperty("Localidad")
    public String localidad;
    @JsonProperty("Longitud (WGS84)")
    public String longitudWGS84;
    @JsonProperty("Margen")
    public String margen;
    @JsonProperty("Municipio")
    public String municipio;
    @JsonProperty("Precio Biodiesel")
    public String precioBiodiesel;
    @JsonProperty("Precio Bioetanol")
    public String precioBioetanol;
    @JsonProperty("Precio Gas Natural Comprimido")
    public String precioGasNaturalComprimido;
    @JsonProperty("Precio Gas Natural Licuado")
    public String precioGasNaturalLicuado;
    @JsonProperty("Precio Gases licuados del petróleo")
    public String precioGaseslicuadosdelpetroleo;
    @JsonProperty("Precio Gasoleo A")
    public String precioGasoleoA;
    @JsonProperty("Precio Gasoleo B")
    public String precioGasoleoB;
    @JsonProperty("Precio Gasoleo Premium")
    public String precioGasoleoPremium;
    @JsonProperty("Precio Gasolina 95 E10")
    public String precioGasolina95E10;
    @JsonProperty("Precio Gasolina 95 E5")
    public String precioGasolina95E5;
    @JsonProperty("Precio Gasolina 95 E5 Premium")
    public String precioGasolina95E5Premium;
    @JsonProperty("Precio Gasolina 98 E10")
    public String precioGasolina98E10;
    @JsonProperty("Precio Gasolina 98 E5")
    public String precioGasolina98E5;
    @JsonProperty("Precio Hidrogeno")
    public String precioHidrogeno;
    @JsonProperty("Provincia")
    public String provincia;
    @JsonProperty("Remisión")
    public String remision;
    @JsonProperty("Rótulo")
    public String rotulo;
    @JsonProperty("Tipo Venta")
    public String tipoVenta;
    @JsonProperty("% BioEtanol")
    public String bioEtanol;
    @JsonProperty("% Éster metílico")
    public String estermetílico;
    @JsonProperty("IDEESS")
    public String iDEESS;
    @JsonProperty("IDMunicipio")
    public String iDMunicipio;
    @JsonProperty("IDProvincia")
    public String iDProvincia;
    @JsonProperty("IDCCAA")
    public String iDCCAA;
}
