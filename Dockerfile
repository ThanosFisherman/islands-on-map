FROM bellsoft/liberica-openjdk-debian:23
LABEL authors="Thanos Psaridis"
# Set the working directory inside the container
WORKDIR /app
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
# Expose the port that the Spring Boot application will run on
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
