FROM maven:3-jdk-12 AS DEPS_IMAGE
ENV APP_HOME=/root/dev/
RUN mkdir -p $APP_HOME/deps
ADD pom.xml $APP_HOME
RUN mvn -f $APP_HOME/pom.xml -Dmaven.repo.local=$APP_HOME/deps dependency:go-offline

FROM maven:3-jdk-12 AS BUILD_IMAGE
WORKDIR /root/
COPY --from=DEPS_IMAGE /root/dev/deps/ ./deps/
COPY . .
RUN mvn -Dmaven.repo.local=/root/deps clean install -DskipTests=true

FROM openjdk:12-alpine
WORKDIR /root/
COPY --from=BUILD_IMAGE /root/target/cron-downloader-0.0.1-SNAPSHOT.jar  .
CMD ["java","-jar","cron-downloader-0.0.1-SNAPSHOT.jar"]