FROM williamyeh/java8:latest
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/app.jar"]