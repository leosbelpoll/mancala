# Leito Mancala API

[SWAGGER Documentation](https://leito-mancala-api.herokuapp.com/swagger-ui.html)

## Features

This API contains the following features:

- Start a new Game
- Make a play
- Get default configurations

## Technologies

- Java
- Spring boot
- H2 DB
- Swagger

## Usage

- To run the app set in the `application.yml` file the initial configuration

```yml
app:
  configs:
    pitsNumber: 6
    ballsNumber: 4
    maxPitsNumber: 10
    maxBallsNumber: 10
```

- Or set these ENV variables

```bash
APP_CONFIGS_PITSNUMBER=6
APP_CONFIGS_BALLSNUMBER=4
APP_CONFIGS_MAXPITSNUMBER=10
APP_CONFIGS_MAXBALLSNUMBER=10
```

- Run the Spring-boot application in `http://localhost:8080`

```bash
mvn spring-boot:run
```

## Tests

- Run tests

```bash
mvn clean test
```
