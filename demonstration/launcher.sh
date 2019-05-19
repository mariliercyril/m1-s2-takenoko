#!/bin/bash

# This script allows us to make a demonstration:
# after running a Docker container of the server (on another terminal),
# it builds client (with integration tests) on this terminal...


gnome-terminal -- ./docker_run_server.sh


sleep 10;


cd ../takenoko-client;

sudo chmod 755 mvnw;
./mvnw clean package
