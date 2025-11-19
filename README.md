# Device API Service

## Description
Device API Service is an application used to manage devices.

## Prerequisites
- Java 21
- Maven
- Docker 

## How to deploy in docker
1. Start application:
    ```sh
    ./start.sh
    ```
2. Use swagger ui to access endpoints `http://localhost:8080/swagger-ui/index.html#`.

3. Stop application:
    ```sh
    ./stop.sh
    ```


## Running locally
1. Create database dependency:
    ```sh
    ./start-dev-dependencies.sh
    ```
2. Start the `DevicesApiApplication#main` from your IDE.

3. Use swagger ui to access endpoints `http://localhost:8080/swagger-ui/index.html#`.

4. Stop the database dependency:
    ```sh
    ./stop-dev-dependencies.sh
    ```

## Running Tests
1. Run unit tests:
    ```sh
    mvn test
    ```

## How to Build the application
1. Clean and build the application:
    ```sh
    mvn clean install
    ```

## Known Issues

1. End points are not secured