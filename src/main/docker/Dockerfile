FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER Vincent Dubois <dubois.vct@free.fr>

VOLUME /tmp
ADD spring-cloud-sample-amazin-client-0.0.1-SNAPSHOT.jar app.jar
COPY waitforit-linux_amd64 /usr/local/bin/waitforit
RUN chmod +x /usr/local/bin/waitforit
RUN sh -c 'touch /app.jar'
CMD waitforit -host=config -port=8888 -timeout=20 ; \
    java -Djava.security.egd=file:/dev/./urandom -jar /app.jar