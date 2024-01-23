FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

RUN sed -i 's/\r$//' gradlew

RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

ENV JAR_PATH=/app/build/libs
RUN mv ${JAR_PATH}/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]