FROM eclipse-temurin:17-jdk
WORKDIR /app

# Instalar herramientas de red necesarias para verificar la conexión
RUN apt update && apt install -y netcat-openbsd iputils-ping curl

COPY *.jar FranchisesAccenture.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]