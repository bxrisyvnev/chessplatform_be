FROM gradle:8.7-jdk17-alpine AS build

ARG JWT_SECRET
ARG DATASOURCE_PASS

ENV SPRING_DATASOURCE_PASSWORD=${DATASOURCE_PASS}
ENV JWT_SECRET=${JWT_SECRET}

RUN mkdir -p /workspace
WORKDIR /workspace
COPY build.gradle /workspace
COPY settings.gradle /workspace
COPY src /workspace/src
RUN gradle build -x test

FROM eclipse-temurin:21-jdk

COPY --from=build /workspace/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
