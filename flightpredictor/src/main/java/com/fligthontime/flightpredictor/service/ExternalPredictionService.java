package com.fligthontime.flightpredictor.service;

import com.fligthontime.flightpredictor.dto.PredictionRequest;
import com.fligthontime.flightpredictor.dto.PredictionResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Service
public class ExternalPredictionService {

    private final WebClient webClient;

    public ExternalPredictionService(
            WebClient.Builder webClientBuilder,
            @Value("${fastapi.base-url}") String baseUrl
    ) {
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    @CircuitBreaker(name = "externalService")
    @TimeLimiter(name = "externalService")
    public CompletableFuture<PredictionResponse> predictAsync(PredictionRequest request) {
        // Resilience4j TimeLimiter requiere retornar un CompletableFuture para manejar timeouts
        return CompletableFuture.supplyAsync(() -> 
            webClient.post()
                .uri("/predict")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PredictionResponse.class)
                .block()
        );
    }

    // Mantenemos el método síncrono original para compatibilidad, pero delegamos
    public PredictionResponse predict(PredictionRequest request) {
        try {
            return predictAsync(request).join();
        } catch (Exception e) {
            // Desempaquetar la excepción del CompletableFuture
            throw new RuntimeException(e.getCause() != null ? e.getCause() : e);
        }
    }
}
