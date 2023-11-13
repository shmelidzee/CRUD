FROM bellsoft/liberica-openjdk-alpine-musl
COPY ./build/libs/CRUD-1.0-SNAPSHOT.jar .
CMD ["java", "-jar", "CRUD-1.0-SNAPSHOT.jar"]