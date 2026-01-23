# ✈️ FlightPredictor Microservice

> **Predicción de retrasos de vuelos con Alta Disponibilidad y Arquitectura Híbrida.**

FlightPredictor no es solo una API. Es un sistema resiliente diseñado para ofrecer predicciones en milisegundos, combinando la potencia de la nube con la velocidad del borde (Edge AI).

---

## 🚀 ¿Por qué es diferente?

Este microservicio implementa una **Arquitectura de 3 Capas** para garantizar que nunca falle:

1.  **⚡ Caché Inteligente (L1)**: Si el vuelo ya fue consultado, responde en **<10ms** desde la base de datos.
2.  **☁️ Cloud AI (L2)**: Conecta con una API Python (FastAPI) para predicciones precisas usando clima en tiempo real.
3.  **🛡️ Fallback Local (L3)**: ¿Se cayó la nube? ¿No hay internet? **No hay problema.** El sistema activa automáticamente un modelo **ONNX embebido** en Java para seguir operando sin interrupciones.

---

## 🛠️ Tech Stack

*   **Core**: Java 17, Spring Boot 3.5
*   **IA / ML**: ONNX Runtime (Inferencia local), Scikit-Learn (Entrenamiento).
*   **Resiliencia**: Resilience4j (Circuit Breaker & Timeouts).
*   **Datos**: MySQL (Prod) / H2 (Dev).
*   **Contenedores**: Docker.

---

## 🔌 API Endpoints

### 1. Predecir Vuelo
`POST /api/prediction`

**Body:**
```json
{
    "CARRIER_NAME": "Delta Air Lines",
    "DEPARTING_AIRPORT": "JFK",
    "DATE": "2024-12-25",
    "TIME": "18:30"
}
```

### 2. Forzar Modo Local (Demo)
Puedes simular un fallo de red forzando el uso del modelo ONNX:
`POST /api/prediction?mode=local`

---

## 🏃‍♂️ Cómo correrlo

### Prerrequisitos
*   Java 17
*   Maven

### Desarrollo (Local)
```bash
# Ejecutar con base de datos en memoria (H2)
./mvnw spring-boot:run
```

### Producción (Docker)
```bash
docker build -t flightpredictor .
docker run -p 8080:8080 -e MYSQL_HOST=mi-db-host flightpredictor
```

---

## 🧠 Arquitectura Lógica

```mermaid
graph TD
    Client -->|Request| Controller
    Controller --> Service
    Service -->|1. Check| Cache[(DB)]
    Service -->|2. Try| CloudAPI[☁️ Python API]
    Service -.->|3. Fallback (Error/Timeout)| LocalModel[💻 ONNX Model]
```

---

**Hecho con ❤️ para el Hackathon.**
