import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.4.2"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  kotlin("jvm") version "1.4.21"
  kotlin("plugin.spring") version "1.4.21"
  kotlin("plugin.jpa") version "1.4.21"
}

group = "com.conway"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
  mavenCentral()
}

configurations {
  all {
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    exclude(group = "ch.qos.logback", module = "logback-classic")
  }
}

dependencies {
  // spring
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
  implementation("org.springframework.boot:spring-boot-starter-log4j2")

  // swagger
//  implementation("org.springdoc:springdoc-openapi-ui:1.3.4")
  implementation("io.springfox:springfox-boot-starter:3.0.0")
  implementation("io.springfox:springfox-swagger-ui:3.0.0")

  // jackson
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  // jwt
  implementation("io.jsonwebtoken:jjwt-api:0.11.2")
  runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")

  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  runtimeOnly("mysql:mysql-connector-java")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "1.8"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
