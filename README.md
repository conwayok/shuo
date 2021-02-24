# shuo3

This project is an example Forum app created with Kotlin + Spring Boot

# Setup

1. Clone this repository

```bash
  git clone https://gitlab.com/conwayok/shuo3.git
```

2. Edit
   the [application.properties file](https://gitlab.com/conwayok/shuo3/-/blob/master/src/main/resources/application.properties)
   and replace the database url. Alternatively, create a new configuration.
   
3. Build project
```bash
  cd shuo3
  ./gradlew build
```

4. Run
```bash
  java -jar build/libs/shuo3-0.0.1.jar
```

# Usage

Once shuo3 is up and running, visit http://localhost:8081/swagger-ui/index.html for api docs.

Frontend is available at http://localhost:8081/index.html.
