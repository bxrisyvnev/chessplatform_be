# ---- build stage ----
FROM gradle:8.7-jdk17-alpine AS build

WORKDIR /workspace

# copy only gradle files first for caching
COPY build.gradle settings.gradle ./
COPY src ./src

# build boot jar
RUN gradle bootJar -x test

# ---- runtime stage ----
FROM eclipse-temurin:17-jre

WORKDIR /app

# copy the bootJar (exclude *plain.jar)
COPY --from=build /workspace/build/libs/*[^plain].jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
