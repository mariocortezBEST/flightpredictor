package com.fligthontime.flightpredictor.dto;

import com.fligthontime.flightpredictor.entity.PredictionCache;

import java.time.LocalDateTime;

public record PredictionListResponse(
        Long id,
        String carrierName,
        String departingAirport,
        String date,
        String time,
        String predictionResult,
        Double probability,
        LocalDateTime queryTimestamp
) {
    public PredictionListResponse(PredictionCache prediction) {
        this(
                prediction.getId(),
                prediction.getCarrierName(),
                prediction.getDepartingAirport(),
                prediction.getFlightDate(),
                prediction.getFlightTime(),
                prediction.getPredictionResult(),
                prediction.getProbability(),
                prediction.getQueryTimestamp()
        );
    }
}
