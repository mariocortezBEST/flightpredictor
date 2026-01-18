package com.fligthontime.flightpredictor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherUsed(
        @JsonProperty("rain") double rain,
        @JsonProperty("wind") double wind,
        @JsonProperty("real_prob") double realProb
) {}
