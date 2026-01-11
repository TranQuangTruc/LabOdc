# 1️⃣ Base image: JDK 17
FROM eclipse-temurin:17-jdk-alpine

# 2️⃣ Set working directory
WORKDIR /app

# 3️⃣ Copy jar build từ target
COPY target/admin-service-0.0.1-SNAPSHOT.jar app.jar

# 4️⃣ Expose port Spring Boot
EXPOSE 8082

# 5️⃣ Run Spring Boot app trên port 8082
ENTRYPOINT ["java","-jar","app.jar","--server.port=8082"]
