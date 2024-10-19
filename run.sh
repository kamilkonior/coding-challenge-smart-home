#!/usr/bin/env sh

# This file will be included as a Docker ENTRYPOINT in our automated testing evironment.
mvn clean package
exec java -jar target/smart-home-1.0-SNAPSHOT.jar
