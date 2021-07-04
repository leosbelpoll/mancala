# stage 1 - build
FROM maven:3.8.1-jdk-8-slim AS build

WORKDIR /build
COPY . ./
RUN mvn clean package -X

# stage 2 — build production image
FROM openjdk:8-jdk-alpine

ENV MANCALA_HOME ./
WORKDIR $MANCALA_HOME
COPY --from=build /build/target/mancala-0.0.1-SNAPSHOT.jar $MANCALA_HOME/app.jar
ENTRYPOINT java -jar $MANCALA_HOME/app.jar