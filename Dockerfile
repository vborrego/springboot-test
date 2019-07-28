FROM openjdk:8-alpine
RUN apk add maven
RUN mkdir -p /usr/src/springboot-test
COPY . /usr/src/springboot-test
WORKDIR /usr/src/springboot-test
RUN mvn clean install
CMD ["java","-Dfilelog=/tmp/out.log","-jar","target/test-spring-boot-0.1.0.jar"]