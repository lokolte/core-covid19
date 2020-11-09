#!/bin/bash

echo "Downloading dependencies..."

sudo mvn clean package spring-boot:repackage

echo "###########################################################################################################"
echo "############################################## DONE DOWNLOAD ##############################################"
echo "###########################################################################################################"

# echo "Starting core-covid19 by jar..."
# VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
# sudo java -jar target/core-covid19-${VERSION}.jar

echo "Deleting docker image if there exist..."
sudo docker rmi -f core-covid19_covid19.core

echo "Cleaning system..."
sudo docker system prune -f

echo "Starting to build docker image..."
sudo docker-compose build

echo "Starting jar in docker image..."
sudo docker run -d --restart=always --network="host" core-covid19_covid19.core