FROM java:8
VOLUME /tmp
ADD docker-spring-boot-demo-0.0.1-SNAPSHOT.jar souyunku-app.jar
RUN bash -c 'touch /souyunku-app.jar'
EXPOSE 8181
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/souyunku-app.jar"]