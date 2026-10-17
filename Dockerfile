# syntax=docker/dockerfile:1

FROM amazoncorretto:21 AS build
WORKDIR /workspace

RUN dnf install -y tar gzip findutils \
    && dnf clean all

COPY mvnw mvnw
COPY .mvn .mvn
COPY pom.xml pom.xml

RUN chmod +x mvnw \
    && ./mvnw -B -DskipTests dependency:go-offline

COPY src src
RUN ./mvnw -B -DskipTests package \
    && JAR="$(find target -maxdepth 1 -type f -name '*.jar' ! -name '*sources*' ! -name '*javadoc*' | head -n 1)" \
    && cp "$JAR" /workspace/app.jar

FROM amazoncorretto:21-headless AS runtime
WORKDIR /app

RUN dnf install -y shadow-utils \
    && dnf clean all \
    && groupadd --system spring \
    && useradd --system --gid spring --no-create-home spring

COPY --from=build /workspace/app.jar /app/app.jar

RUN mkdir -p /app/data \
    && chown spring:spring /app/data

USER spring:spring
EXPOSE 8081

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0"

HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -fsS http://127.0.0.1:8081/actuator/health | grep -q '"status":"UP"'

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]