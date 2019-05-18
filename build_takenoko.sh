#!/bin/bash

# This script allows to build the project (server, on the one hand, and client, on the other hand).

cd takenoko-$1;
mvn clean package

cd ..;
