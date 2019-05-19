#!/bin/bash

# This script allows us to make a demonstration:
# after running a Docker container of the server (on another terminal),
# it builds client (with integration tests) on this terminal...


sudo chmod 755 ../takenoko-server/mvnw ../takenoko-client/mvnw;


gnome-terminal -- ./docker_run_server.sh

sleep 10;

cd ../takenoko-client;
./mvnw clean package
