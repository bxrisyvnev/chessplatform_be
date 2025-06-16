FROM gradle:8.7-jdk17-alpine AS build

ARG JWT_SECRET
ARG DATASOURCE_PASS

ENV SECURITY_JWT_SECRET=${JWT_SECRET}
ENV SPRING_DATASOURCE_PASSWORD=${DATASOURCE_PASS}

RUN mkdir -p /workspace
WORKDIR /workspace
COPY build.gradle /workspace
COPY settings.gradle /workspace
COPY src /workspace/src
RUN gradle build --no-daemon

FROM openjdk:21

COPY --from=build /workspace/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]