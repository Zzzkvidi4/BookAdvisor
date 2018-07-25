FROM maven:3.5.2-jdk-8-alpine AS maven_tool_chain
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package && ls target

FROM openjdk:8-jdk-alpine
COPY --from=maven_tool_chain /tmp/target/BookAdvisor*.jar /jars/book-advisor/BookAdvisor.jar
WORKDIR /jars/book-advisor
EXPOSE 8080
ENTRYPOINT ["java","-jar","BookAdvisor.jar"]
