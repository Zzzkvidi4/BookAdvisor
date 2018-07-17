FROM openjdk:8-jdk-alpine
COPY /target/BookAdvisor-1.0-SNAPSHOT.jar /home/zzzkvidi4/images/book-advisor/
WORKDIR /home/zzzkvidi4/images/book-advisor
EXPOSE 8080
ENTRYPOINT ["java","-jar","BookAdvisor-1.0-SNAPSHOT.jar"]
