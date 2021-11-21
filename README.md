# Maritime Operating Center (MOC) environment

The repository contains code for the shore-side of the [VRGP
specification](https://github.com/aboamare/vrgp-specifications/).
It implements both a backend (to communicate with the vessel-side) and a
frontend (for easy visualization of the data received from the boat).

## Minimal application

The current minimal Spring Boot application was created following this
[tutorial for a RESTful web service](https://spring.io/guides/gs/rest-service/).
For now, the application only sends a JSON containing an ID and a greeting upon
HTTP GET requests to the path `/greeting`.

## Setup

### Requirements

First, you need to set up the following tools:

* Install a Java Development Kit (JDK) for Java 11 such as
  [AdoptOpenJDK](https://adoptopenjdk.net/).
* [Download](https://maven.apache.org/download.cgi) and
  [install](https://maven.apache.org/install.html) Maven 3.2+, preferably
  version 3.8.3.
* Preferably install a Java IDE such as
  [IntelliJ IDEA](https://www.jetbrains.com/idea/).

### Run Spring Boot application

To run the application from the command line, first run `mvn install` and then
`mvn spring-boot:run`. You can test the application at
[localhost:8080/greeting](http://localhost:8080/greeting).

### Run app with Docker

To run the app with Docker follow these standard steps:

1. `docker build -t moc-server .`
2. `docker run -p 8080:8080 moc-server`

## Spring WebSockets

Here are some useful tutorials for WebSockets in Spring:

* [SpringHow: Introduction to WebSocket with Spring Boot](https://springhow.com/spring-boot-websocket/)
* [Java in use: Spring Boot + WebSocket Simple Example](https://www.javainuse.com/spring/boot-websocket)