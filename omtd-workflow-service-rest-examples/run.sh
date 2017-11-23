#!/bin/bash

execJar="/home/ilsp/Desktop/DG/OMTD/omtd-workflow-service-rest/omtd-workflow-service-rest-examples/target/omtd-workflow-service-rest-examples-0.0.1-SNAPSHOT-exec.jar"

# params 
# storeEndpoint workflowEndpoint inFolder inArchive workflowID downloadPath
java -jar $execJar $@
