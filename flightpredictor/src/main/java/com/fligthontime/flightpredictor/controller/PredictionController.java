package com.fligthontime.flightpredictor.controller;

import com.fligthontime.flightpredictor.dto.PredictionListResponse;
import com.fligthontime.flightpredictor.dto.PredictionRequest;
import com.fligthontime.flightpredictor.dto.PredictionResponse;
import com.fligthontime.flightpredictor.service.LocalModelService;
import com.fligthontime.flightpredictor.service.PredictionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/prediction")
@RequiredArgsConstructor
public class PredictionController {
    private final PredictionService predictionService;
    private final LocalModelService localModelService;

    @PostMapping
    public PredictionResponse predict(
            @Valid @RequestBody PredictionRequest request,
            @RequestParam(required = false, defaultValue = "remote") String mode
    ) {
        // Delegamos TODA la lógica al servicio principal, pasando el modo
        return predictionService.predict(request, mode);
    }

    @GetMapping
    public ResponseEntity<Page<PredictionListResponse>> listPredictions(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(predictionService.listPredictions(page, size));
    }

    @GetMapping("/debug-model")
    public Map<String, String> debugModel() {
        return localModelService.getModelInputs();
    }
}
