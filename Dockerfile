FROM amazoncorretto:11-alpine-jdk
EXPOSE 8080
RUN addgroup -S hn && adduser -S hn -G hn
USER hn:hn
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]