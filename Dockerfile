FROM eclipse-temurin:17-jdk-alpine
LABEL maintainer="arulsemmalai@gmail.com"

ARG VERSION=1.0.0
LABEL version="$VERSION"

WORKDIR /app
COPY target/tracking-number-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
