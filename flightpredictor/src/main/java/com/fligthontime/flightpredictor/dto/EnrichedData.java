package com.fligthontime.flightpredictor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EnrichedData(

        @JsonProperty("PRCP")
        double prcp,

        @JsonProperty("TMAX")
        double tmax,

        @JsonProperty("AWND")
        double awnd,

        @JsonProperty("SNOW")
        double snow,

        @JsonProperty("CONCURRENT_FLIGHTS")
        double concurrentFlights
) {}
