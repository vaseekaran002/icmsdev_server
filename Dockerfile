FROM openjdk:8-jdk-alpine

WORKDIR /app
COPY *.jar ./

RUN chmod g+rwx -R .

ENTRYPOINT ["java","-jar","icms-service.jar"]