# ---- Build-Stage: Maven + JDK 21 ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build

# Zuerst nur die pom.xml kopieren, damit die Dependencies im Docker-Cache bleiben
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B -q package -DskipTests

# ---- Runtime-Stage: nur JRE 21 ----
FROM eclipse-temurin:21-jre
WORKDIR /app

RUN useradd --system --no-create-home appuser
COPY --from=build /build/target/*.jar app.jar
USER appuser

EXPOSE 8080
# JAVA_OPTS z. B. "-Xmx512m" zum Begrenzen des Speichers
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
