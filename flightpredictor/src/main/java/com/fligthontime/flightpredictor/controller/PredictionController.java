package com.fligthontime.flightpredictor.controller;

import com.fligthontime.flightpredictor.dto.PredictionRequest;
import com.fligthontime.flightpredictor.dto.PredictionResponse;
import com.fligthontime.flightpredictor.service.PredictionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/predictions")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;

    @PostMapping
    public ResponseEntity<PredictionResponse> predict(
            @Valid @RequestBody PredictionRequest request) {

        PredictionResponse response = predictionService.predict(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{flightNumber}")
    public ResponseEntity<PredictionResponse> getCachedPrediction(
            @PathVariable String flightNumber) {

        PredictionResponse response = predictionService.getCachedPrediction(flightNumber);
        return ResponseEntity.ok(response);
    }
}