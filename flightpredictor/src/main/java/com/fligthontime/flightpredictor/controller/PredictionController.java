package com.fligthontime.flightpredictor.controller;

import com.fligthontime.flightpredictor.dto.PredictionRequest;
import com.fligthontime.flightpredictor.dto.PredictionResponse;
import com.fligthontime.flightpredictor.service.LocalModelService;
import com.fligthontime.flightpredictor.service.PredictionService;
import lombok.RequiredArgsConstructor;
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
            @RequestBody PredictionRequest request,
            @RequestParam(required = false, defaultValue = "remote") String mode
    ) {
        if ("local".equalsIgnoreCase(mode)) {
            return localModelService.predictLocal(request);
        }
        return predictionService.predict(request);
    }

    @GetMapping("/debug-model")
    public Map<String, String> debugModel() {
        return localModelService.getModelInputs();
    }
}
