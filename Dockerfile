FROM java:8
VOLUME /tmp
ADD miaosha-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8760
#ENV JAVA_OPTS="-Xms512M -Xmx512M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/zhiminghan/Desktop/HeapDump"

ENTRYPOINT [ "java", "-jar", "/app.jar" ]