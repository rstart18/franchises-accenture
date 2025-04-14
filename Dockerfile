FROM eclipse-temurin:21-jdk
WORKDIR /app

RUN apt update && apt install -y netcat-openbsd iputils-ping curl

COPY applications/app-service/build/libs/FranchisesAccenture.jar FranchisesAccenture.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "FranchisesAccenture.jar"]