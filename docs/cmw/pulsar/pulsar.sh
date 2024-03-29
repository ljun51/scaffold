#!/usr/bin/env bash
docker rm pulsar
docker volume rm pulsardata
docker volume rm pulsarconf
docker run -it -d --name pulsar -p 6650:6650  -p 28080:8080 --mount source=pulsardata,target=/pulsar/data --mount source=pulsarconf,target=/pulsar/conf apachepulsar/pulsar:2.10.4 bin/pulsar standalone