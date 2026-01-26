# Etapa 1: Construcción (Build)
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiar el archivo de configuración de Maven
COPY pom.xml ./

# Descargar dependencias
RUN mvn dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Compilar y empaquetar
RUN mvn package -DskipTests

# Etapa 2: Ejecución (Runtime)
# CAMBIO IMPORTANTE: Usamos la versión estándar (Jammy/Ubuntu) en lugar de Alpine
# Esto es necesario para que las librerías nativas de ONNX Runtime funcionen correctamente.
FROM eclipse-temurin:17-jre
WORKDIR /app

# Crear usuario no-root por seguridad
RUN groupadd -r mariodev_28 && useradd -r -g mariodev_28 mariodev_28
USER mariodev_28

# Copiar el JAR generado
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Variables de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=prod

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
