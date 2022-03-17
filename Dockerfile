FROM eclipse-temurin:11-alpine
WORKDIR /app
COPY /build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8000