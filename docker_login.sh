#!/bin/bash

# This script allows Travis CI to log in our Docker repository.

docker login --username projets2192co --password-stdin $PASSWORD
