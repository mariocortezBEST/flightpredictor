package com.fligthontime.flightpredictor.dto;

import com.fligthontime.flightpredictor.entity.PredictionCache;

import java.time.LocalDateTime;

public record DetailsPredictionResponse(
        Long id,
        String carrierName,
        String departingAirport,
        String date,
        String time,
        String predictionResult,
        Double probability,
        LocalDateTime queryTimestamp
) {
    public DetailsPredictionResponse(PredictionCache prediction) {
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
