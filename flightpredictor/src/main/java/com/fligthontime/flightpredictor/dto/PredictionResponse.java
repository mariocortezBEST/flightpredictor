package com.fligthontime.flightpredictor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PredictionResponse(

        @JsonProperty("prediction")
        String prediction,

        @JsonProperty("probability")
        double probability,

        @JsonProperty("details")
        String details,

        @JsonProperty("weather_used")
        WeatherUsed weatherUsed
) {}
