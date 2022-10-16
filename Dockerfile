#### Stage 1: Build the application
FROM openjdk:11  as builder

# Set the current working directory inside the image
WORKDIR /app

# Copy maven executable to the image
COPY . .
# Build all the dependencies in preparation to go offline.
# This is a separate step so the dependencies will be cached unless
# the pom.xml file has changed.

RUN ./gradlew clean test

RUN ./gradlew bootJar -i --stacktrace

#### Stage 2: A minimal docker image with command to run the app
FROM openjdk:11

ARG DEPENDENCY=/app/target/dependency
COPY --from=builder /app/build/libs/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]