#!/bin/bash

# This cript allows to build the project with testing the integration between client and server.

# Launches the server...
cd takenoko-server;
mvn exec:java -Dexec.args=4 &

# Builds the project
cd ..;
mvn clean package

# Stops Tomcat of the server...
#curl -v -X POST http://localhost:8080/shutdown
