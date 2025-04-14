# 🏪 Franchises Accenture - API Backend

Microservicio desarrollado en **Java 17** con **Spring Boot** y arquitectura **Clean Architecture**, que permite la
gestión de franquicias, sucursales y productos. El proyecto está dockerizado y usa **PostgreSQL** como base de datos.

---

## ✅ Requisitos Previos

- Docker
- Docker Compose

---

## 🚀 Instrucciones para correr el proyecto

1. Clona el repositorio:

   ```bash
   git clone https://github.com/tu-usuario/franchises-accenture.git
   cd franchises-accenture

### 🛠️ Generar el archivo JAR antes de construir la imagen Docker

2. Antes de ejecutar el proyecto con Docker, asegúrate de generar el `.jar` con el siguiente comando:

   ```bash
   ./gradlew clean bootJar
   ```

2. Crea un archivo `.env` en la raíz del proyecto con las siguientes variables:

   ```env
   DB_HOST=postgres
   DB_PORT=5432
   DB_NAME=franchises2
   DB_USER=sebas
   DB_PASSWORD=12345

3. Ejecuta los contenedores:

   En primer plano:

   ```bash
   docker-compose up --build
   ```

   En segundo plano:

   ```bash
   docker-compose up --build -d

4. La API estará disponible en:

   ```bash
   http://localhost:8080

Puedes probar todos los endpoints usando la colección de Postman incluida en la raíz del proyecto:

**Archivo:** `Franchises.postman_collection.json`



