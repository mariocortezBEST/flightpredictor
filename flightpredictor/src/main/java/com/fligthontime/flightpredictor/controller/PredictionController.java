package com.fligthontime.flightpredictor.controller;

import com.fligthontime.flightpredictor.dto.PredictionRequest;
import com.fligthontime.flightpredictor.dto.PredictionResponse;
import com.fligthontime.flightpredictor.service.PredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/prediction")
@RequiredArgsConstructor
public class PredictionController {
    private final PredictionService predictionService;

    @PostMapping
    // Falta convertir la respuesta y validar los campos que se agregan.
    // Controlador echo por defecto para probar el microservicio.
    // Editar si es necesario.
    // Tuve que hacer los dto obligatoriamente para probar.
    public PredictionResponse predict(@RequestBody PredictionRequest request) {
        return predictionService.predict(request);
    }
}
