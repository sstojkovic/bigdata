FROM bde2020/spark-scala-template:3.0.1-hadoop3.2

ENV SPARK_APPLICATION_MAIN_CLASS Application
ENV SPARK_APPLICATION_JAR_NAME extrasensory-scala-1.0-SNAPSHOT

COPY wait-for.sh /
RUN chmod +x /wait-for.sh

ENTRYPOINT ["sh", "-c", "/wait-for.sh ${ES_HEALTHCHECK_ENDPOINT} /template.sh"]