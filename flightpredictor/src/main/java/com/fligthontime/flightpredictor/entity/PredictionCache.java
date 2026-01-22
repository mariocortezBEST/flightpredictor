package com.fligthontime.flightpredictor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredictionCache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String carrierName;
    private String departingAirport;
    private String flightDate; // Formato YYYY-MM-DD
    private String flightTime; // Formato HH:MM

    private String predictionResult; // "PUNTUAL" o "RETRASADO"
    private Double probability;

    private LocalDateTime queryTimestamp;
}