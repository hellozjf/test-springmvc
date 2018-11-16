FROM java:openjdk-8-jre-alpine

MAINTAINER hellozjf 908686171@qq.com

ARG JAR_FILE
ADD target/${JAR_FILE} app.jar

EXPOSE 8080

VOLUME /output

ENTRYPOINT ["java", "-jar", "app.jar"]