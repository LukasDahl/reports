FROM adoptopenjdk:11-jre-hotspot

COPY target/reports-1.0.0-SNAPSHOT-runner.jar /usr/src

WORKDIR /usr/src

CMD java -Xmx64m \
    -jar reports-1.0.0-SNAPSHOT-runner.jar