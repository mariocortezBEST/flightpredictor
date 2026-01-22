package com.fligthontime.flightpredictor.repository;

import com.fligthontime.flightpredictor.entity.PredictionCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PredictionRepository extends JpaRepository<PredictionCache, Long> {
    // Método para buscar si ya existe una predicción para este vuelo específico
    Optional<PredictionCache> findByCarrierNameAndDepartingAirportAndFlightDateAndFlightTime(
            String carrierName,
            String departingAirport,
            String flightDate,
            String flightTime
    );
}