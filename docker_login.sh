#!/bin/bash

# This script allows Travis CI to log in our Docker repository.

echo $DOCKER_PASSWORD | docker login --username projets2192co --password-stdin
