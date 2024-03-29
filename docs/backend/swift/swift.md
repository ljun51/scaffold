# swift

## Using Docker Images
Pull the Docker image from Docker Hub:

> docker pull swift

Create a container using tag latest and attach it to the container:

> docker run --privileged --interactive --tty \
  --name swift-latest swift:latest /bin/bash

Start container swift-latest:

> docker start swift-latest

Attach to swift-latest container:

> docker attach swift-latest


