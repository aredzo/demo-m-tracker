#!/bin/bash

docker network create mtracker-network

cd app
docker-compose -f app-docker-compose.yml up -d


#export $(grep -v '^#' env | xargs)
#unset $(grep -v '^#' env | sed -E 's/(.*)=.*/\1/' | xargs)
