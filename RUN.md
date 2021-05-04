# ALNS-MediaService-Reactive-MS

### Pre-Requirites:

In order to run this simple application make sure you meet these pre-requisites:

- Git 2.25+
- Java JDK 11 (recommended AdoptOpenJDK)
- Maven 3.6.3+
- Some Java IDE (Optional);
- Postman (Optional).

For the simplicity sake, this application runs against and H2 File.

### How to Run:

- Clone the project on your local machine:
    - `> git clone https://github.com/scout23DF/alns-media-service-reactive-ms.git`
- Move to the directory wher the project was cloned:
    - `> cd alns-media-service-reactive-ms`
- Build and Package the Application using Maven 3.6.3+:
    - `> clear && mvn clean package`
    - Be patient and grab a Coffee whilst all dependencies and tests run :-)
- Run Build and Package the Application using Maven 3.6.3+:
    - `> clear && java -jar ./target/alns-media-service-reactive-ms-1.0.0-SNAPSHOT.jar`
- [Optional] Alternatively, you also may import an run this project in your preferred IDE that supports Maven projects (IntelliJ Idea / Eclipse (STS) / NetBeans / VS Code + Java Extensions);

### Inspecting the REST API's Endpoints:

You can inspect all the available REST API's navigating to Swagger-UI for Spring WebFlux at: 

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config)


