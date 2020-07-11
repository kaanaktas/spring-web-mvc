#!/bin/bash

set -e

DOCKER_IMAGE=$1
CONTAINER_NAME=$2

# Check for arguments
if [[ $# -lt 1 ]] ; then
    echo '[ERROR] You must supply a Docker Image to pull'
    exit 1
fi

echo "Deploying $CONTAINER_NAME to Docker Container"

#Check for running container & stop it before starting a new one
if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
    echo "Stopping docker container"
    docker stop $CONTAINER_NAME

    echo "Removing existing docker container"
    docker rm -f $CONTAINER_NAME

    echo "Removing existing docker image"
    docker rmi $DOCKER_IMAGE
fi

echo "Starting $CONTAINER_NAME using Docker Image name: $DOCKER_IMAGE"

docker run -d --network="host" --name $CONTAINER_NAME $DOCKER_IMAGE

docker images
docker ps -a