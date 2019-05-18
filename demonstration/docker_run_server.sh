#!/bin/bash

cd ../takenoko-server;

docker pull projets2192co/takenoko:server
docker run -p 8080:8080 -t projets2192co/takenoko:server
