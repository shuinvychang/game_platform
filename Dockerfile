# -- build stage --
# find a properly image base
FROM maven:3.9.16-eclipse-temurin-17-noble AS build

# If there is no app directory,
# Docker will create it and cd to it
WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

# RUN is for build image stage
# clean first, then package
# skip unit test
RUN mvn clean package -Dmaven.test.skip=true

# -- package stage --
# We can just use jre instead of jdk
# alpine will smaller the image size
FROM eclipse-temurin:17-jre-alpine

LABEL authors="Shuinvy"

# Because each FROM will create a new image stage
# We should change directory again
WORKDIR /app

# copy files from the stage "build" (we named)
COPY --from=build /app/target/*.jar app.jar

# Our Spring Boot used 8080 port
# so the container port should be 8080
EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]