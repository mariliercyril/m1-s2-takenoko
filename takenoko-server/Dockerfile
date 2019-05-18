#FROM projets2192co/takenoko:server
#VOLUME /tmp
#ADD JAR_FILE=target/takenoko-server-1.0-CLIENT-SERVER.jar
#CMD ["java","-jar","takenoko-server.jar"]
#ENTRYPOINT ["java","-cp","app:app/lib/*","com.cco.takenoko.server.TakenokoServer"]

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.cco.takenoko.server.TakenokoServer"]
