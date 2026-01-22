package com.fligthontime.flightpredictor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PredictionRequest(

        // Validar DTO
        @JsonProperty("CARRIER_NAME")
        String carrierName,

        @JsonProperty("DEPARTING_AIRPORT")
        String departingAirport,

        @JsonProperty("DATE")
        String date,

        @JsonProperty("TIME")
        String time
) {}
