package com.fligthontime.flightpredictor.service;

import com.fligthontime.flightpredictor.dto.PredictionRequest;
import com.fligthontime.flightpredictor.dto.PredictionResponse;
import org.springframework.stereotype.Service;

@Service
public class PredictionService {
    private final ExternalPredictionService externalService;

    public PredictionService(ExternalPredictionService externalService) {
        this.externalService = externalService;
    }

    public PredictionResponse predict(PredictionRequest request) {
        return externalService.predict(request);
    }
}
