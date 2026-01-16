package com.fligthontime.flightpredictor.controller;
import com.fligthontime.flightpredictor.dto.PredictionRequest;
import com.fligthontime.flightpredictor.service.PredictionService;
import com.fligthontime.flightpredictor.dto.PredictionResponse;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @PostMapping("/predict")
    public PredictionResponse predict(@Valid @RequestBody PredictionResponse request) {
        return predictionService.processPrediction(request);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Campo inválido");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}