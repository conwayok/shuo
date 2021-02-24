# shuo

This project is an example Forum app created with Kotlin + Spring Boot

# Setup

1. Clone this repository

```bash
  git clone https://github.com/conwayok/shuo.git
```

2. Edit
   the [application.properties file](https://github.com/conwayok/shuo/blob/master/src/main/resources/application.properties)
   and replace the database url. Alternatively, create a new configuration.
   
3. Build project
```bash
  cd shuo
  ./gradlew build
```

4. Run
```bash
  java -jar build/libs/shuo-0.0.1.jar
```

# Usage

Once shuo is up and running, visit http://localhost:8081/swagger-ui/index.html for api docs.

Frontend is available at http://localhost:8081/index.html.
