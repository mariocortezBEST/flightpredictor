package com.fligthontime.flightpredictor.repository;

import com.fligthontime.flightpredictor.entity.PredictionCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PredictionRepository extends JpaRepository<PredictionCache, Long> {

    // CAMBIO: Usamos 'findFirstBy' para asegurar que solo devuelva 1 resultado,
    // incluso si hay duplicados en la base de datos.
    Optional<PredictionCache> findFirstByCarrierNameAndDepartingAirportAndFlightDateAndFlightTime(
            String carrierName,
            String departingAirport,
            String flightDate,
            String flightTime
    );
}
