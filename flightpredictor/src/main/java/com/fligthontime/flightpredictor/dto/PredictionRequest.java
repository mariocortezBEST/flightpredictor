package com.fligthontime.flightpredictor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PredictionRequest(

        @JsonProperty("CARRIER_NAME")
        String carrierName,

        @JsonProperty("DEPARTING_AIRPORT")
        String departingAirport,

        @JsonProperty("FECHA")
        String fecha,

        @JsonProperty("HORA")
        String hora
) {}
