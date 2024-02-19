#!/bin/bash

ROOT_PATH="/home/ubuntu/spring-app"

# Pull down the latest version of the Docker image from Docker Hub
docker pull "$DOCKER_IMAGE"

# Stop the Docker container if it's already running
docker stop "$CONTAINER_NAME" || true

# Remove the container if it exists
docker rm "$CONTAINER_NAME" || true

# Start the Docker container using the pulled image
docker run -d --env-file env.list --name "$CONTAINER_NAME" "$DOCKER_IMAGE"