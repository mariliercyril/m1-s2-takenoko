#!/bin/bash

# This cript allows to stop Tomcat of the server (port = 8080).

kill `lsof -i -n -P | grep TCP | grep 8080 | tr -s " " "\n" | sed -n 2p`
