package com.fligthontime.flightpredictor.service;

import com.fligthontime.flightpredictor.dto.EnrichedData;
import com.fligthontime.flightpredictor.dto.PredictionRequest;
import com.fligthontime.flightpredictor.dto.PredictionResponse;
import com.fligthontime.flightpredictor.entity.PredictionCache;
import com.fligthontime.flightpredictor.repository.PredictionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PredictionService {
    
    private static final Logger logger = LoggerFactory.getLogger(PredictionService.class);

    private final ExternalPredictionService externalService;
    private final LocalModelService localModelService;
    private final PredictionRepository repository;

    // Sobrecarga para mantener compatibilidad si alguien llama sin modo
    public PredictionResponse predict(PredictionRequest request) {
        return predict(request, "remote");
    }

    public PredictionResponse predict(PredictionRequest request, String mode) {
        // 1. Buscar en Caché (Base de Datos) - SIEMPRE PRIMERO
        // CAMBIO: Usamos findFirstBy para evitar error NonUniqueResultException
        Optional<PredictionCache> cached = repository.findFirstByCarrierNameAndDepartingAirportAndFlightDateAndFlightTime(
                request.carrierName(),
                request.departingAirport(),
                request.date(),
                request.time()
        );

        if (cached.isPresent()) {
            logger.info("✅ Predicción encontrada en caché DB para: {} - {}", request.carrierName(), request.date());
            PredictionCache p = cached.get();
            
            // FIX: Devolver un objeto EnrichedData vacío en lugar de null para evitar errores en el Frontend
            EnrichedData emptyEnrichedData = new EnrichedData(0.0, 0.0, 0.0, 0.0, 0.0);

            return new PredictionResponse(
                    p.getPredictionResult(),
                    p.getProbability(),
                    "Recuperado de Base de Datos (Caché)",
                    emptyEnrichedData 
            );
        }

        // 2. Si no está en caché, decidir qué modelo usar
        PredictionResponse response;
        
        if ("local".equalsIgnoreCase(mode)) {
            logger.info("💻 Ejecutando Modelo LOCAL (ONNX) para: {} - {}", request.carrierName(), request.date());
            response = localModelService.predictLocal(request);
        } else {
            logger.info("🌍 Consultando servicio EXTERNO para: {} - {}", request.carrierName(), request.date());
            try {
                response = externalService.predict(request);
            } catch (Exception e) {
                logger.error("❌ Falló servicio externo, activando FALLBACK a local: {}", e.getMessage());
                response = localModelService.predictLocal(request);
            }
        }

        // 3. Guardar el resultado en DB (Caché para la próxima)
        // Solo guardamos si la respuesta fue exitosa (probabilidad > 0)
        if (response != null && response.probability() > 0) {
            try {
                // Verificación extra: ¿Ya existe? (Aunque findFirstBy nos protege al leer, evitemos escribir basura)
                boolean exists = repository.findFirstByCarrierNameAndDepartingAirportAndFlightDateAndFlightTime(
                        request.carrierName(),
                        request.departingAirport(),
                        request.date(),
                        request.time()
                ).isPresent();

                if (!exists) {
                    PredictionCache newCache = PredictionCache.builder()
                            .carrierName(request.carrierName())
                            .departingAirport(request.departingAirport())
                            .flightDate(request.date())
                            .flightTime(request.time())
                            .predictionResult(response.prediction())
                            .probability(response.probability())
                            .queryTimestamp(LocalDateTime.now())
                            .build();
                    
                    repository.save(newCache);
                    logger.info("💾 Predicción guardada en DB");
                } else {
                    logger.info("⚠️ Predicción ya existía en DB, saltando guardado.");
                }
                
            } catch (Exception e) {
                logger.error("⚠️ Error guardando en caché DB: {}", e.getMessage());
            }
        }

        return response;
    }
}
