FROM openjdk:17-jdk-slim AS builder
ENV HOME=/home/app

RUN apt-get update && apt-get install -y maven
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME

RUN mvn clean -e install
RUN mvn clean package

FROM openjdk:17-jdk-slim
ENV HOME=/home/app
ARG JAR_FILE=$HOME/target/*.jar
COPY --from=builder $JAR_FILE $HOME/runner.jar

EXPOSE 8080
CMD ["java", "-jar", "/home/app/runner.jar"]
