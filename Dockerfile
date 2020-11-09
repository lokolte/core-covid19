ARG BASE_IMAGE=openjdk:13.0.2-jdk-oraclelinux7
FROM ${BASE_IMAGE}
MAINTAINER Jesus Aguilar jaaguilarmeza@gmail.com
EXPOSE 8080
ENV APP_BASE=/core
ENV VERSION=0.0.1-SNAPSHOT
COPY target/core-covid19-${VERSION}.jar ${APP_BASE}/app.jar
COPY entrypoint.sh ${APP_BASE}/
ENTRYPOINT bash ${APP_BASE}/entrypoint.sh