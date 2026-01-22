package com.fligthontime.flightpredictor.dto;

package com.fligthontime.flightpredictor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PredictionRequest(

        @NotBlank(message = "Carrier name is required")
        @JsonProperty("CARRIER_NAME")
        String carrierName,

        @NotBlank(message = "Departing airport is required")
        @JsonProperty("DEPARTING_AIRPORT")
        String departingAirport,

        @NotBlank(message = "Date is required")
        @Pattern(
                regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$",
                message = "Date must have format yyyy-MM-dd"
        )
        @JsonProperty("DATE")
        String date,

        @NotBlank(message = "Time is required")
        @Pattern(
                regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$",
                message = "Time must have format HH:mm"
        )
        @JsonProperty("TIME")
        String time
) {}

//{
//  "carrierName": "Delta",
//  "departingAirport": "JFK",
//  "fecha": "2026-01-15",
//  "hora": "14:30"
//}