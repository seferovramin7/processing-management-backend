FROM openjdk:8-jre-alpine
EXPOSE	8800
ADD /build/libs/jwt-auth-0.0.1-SNAPSHOT.jar jwt-auth-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","jwt-auth-0.0.1-SNAPSHOT.jar"]