# Paper-Rock-Scissors Game

## Background
Paper-Rock-Scissors is a game for two players. Each player simultaneously opens his/her hand to display
a symbol:
- Fist equals rock
- Open hand equals paper
- Showing the index and middle finger equals scissors.

The winner is determined by the following schema:
- Paper beats (wraps) rock
- Rock beats (blunts) scissors
- Scissors beats (cuts) paper.

This project is a single player Paper-Rock-Scissors in with a real player can play it with the computer.

## Technical stack
This game has been developed on Quarkus, which is a Supersonic Subatomic Java Framework.
If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

Besides, Paper-Rock-Scissors comes with a simple user interface which is developed by angularjs 8.0 (https://angularjs.org/) and bootstrap 4.0 (https://getbootstrap.com/docs/4.0/getting-started/introduction/)

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
mvn compile quarkus:dev
```

## Packaging and running the application

The application can be packaged using:
```shell script
mvn package
```
It produces the `paper-rock-scissors-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.

The application is now runnable using `java -jar target/paper-rock-scissors-1.0.0-SNAPSHOT-runner.jar`.

Now you can open http://localhost:8080/index.html on your browser to see the Paper-Rock-Scissors web interface.

# Paper-Rock-Scissors REST API

There are some REST api in this project to manage the game. You can find out the API specification from the
`src/main/resources/META-INF/openapi.yml`

Besides, once your application is started, you can make a request to the default `/q/openapi` endpoint to download the API specification
```
curl http://localhost:8080/q/openapi
```

## View REST API in Swagger UI
You can access to the swaggerui when the project is started in dev mode.
```shell script
mvn compile quarkus:dev
```
Once your application is started, you can go to [http://localhost:8080/q/swagger-ui]() and play with your API.

## Game SDK
In this project a game sdk has been developed that is responsible for preparation and rule controlling of the game.
Since it has been developed based on a pure java, you can take advantage of it to use in any other Java framework such as Spring Boot.

The SDK can be found under the `src/main/java/com/miladjafari/prs/sdk` package

## Code Coverage
In this project JaCoCo Maven plugin is used to generate a code coverage report. You can generate report by using:
```shell script
mvn clean test
```
The code coverage report will be generated at `target/site/jacoco/*`. Open `target/site/jacoco/index.html` file, review the code coverage report
