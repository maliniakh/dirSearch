#!/bin/bash
if [ ! -f target/dirSearch-1.0-SNAPSHOT.jar ]; then
    mvn install
fi
java -jar target/dirSearch-1.0-SNAPSHOT.jar $@
