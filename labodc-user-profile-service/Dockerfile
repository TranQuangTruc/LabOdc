# Su dung JDK 17, phu hop Spring Boot 3
FROM eclipse-temurin:17-jdk

# Thu muc lam viec trong container
WORKDIR /app

# Copy file jar sau khi build
COPY target/*.jar app.jar

# Mo cong 8080 trong container
EXPOSE 8080

# Lenh chay ung dung
ENTRYPOINT ["java", "-jar", "app.jar"]
