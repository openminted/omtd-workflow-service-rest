#!/bin/bash

# Set parameters.
confFile="./application.properties"
jarExec="../omtd-workflow-service-rest-server/target/omtd-workflow-service-rest-server-0.0.1-SNAPSHOT.jar"

# Print config file. 
clear;
echo $confFile
echo "============="
cat $confFile

java -jar $jarExec --spring.config.location=$confFile


