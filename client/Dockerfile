FROM maven:3.8.1-openjdk-17 as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
ENV SPRING_PROFILES_ACTIVE prod
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "/app/app.jar"]
