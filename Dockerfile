FROM gradle:8.7-jdk17-alpine AS build

ARG JWT_SECRET
ARG DATASOURCE_PASS

ENV SPRING_DATASOURCE_PASSWORD=${DATASOURCE_PASS}
ENV JWT_SECRET=${JWT_SECRET}

WORKDIR /workspace
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle bootJar -x test

FROM eclipse-temurin:21-jdk

WORKDIR /app

# copy ONLY the boot jar (exclude -plain.jar)
COPY --from=build /workspace/build/libs/*[^plain].jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

