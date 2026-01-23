# Etapa 1: Construcción (Build)
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Descargar dependencias (Capa cacheada si no cambia el pom.xml)
# Usamos 'go-offline' para mejorar tiempos en builds sucesivos
RUN ./mvnw dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Compilar y empaquetar (Saltando tests para agilizar el build de contenedor)
RUN ./mvnw package -DskipTests

# Etapa 2: Ejecución (Runtime)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Crear usuario no-root por seguridad (Personalizado)
RUN addgroup -S mariodev_28 && adduser -S mariodev_28 -G mariodev_28
USER mariodev_28:mariodev_28

# Copiar el JAR generado desde la etapa de construcción
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Variables de entorno por defecto (pueden sobreescribirse al correr)
ENV SPRING_PROFILES_ACTIVE=prod

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
