#!/bin/bash

# This script allows to build the project (server and client).

cd takenoko-$1
mvn clean package

cd ..
