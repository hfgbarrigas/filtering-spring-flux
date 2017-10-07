#!/bin/sh

#we can use environment variables derived from several places, e.g: other scripts that run before this one and
if [ -z "$ENVIRONMENT" ]; then
    echo "Missing ENVIRONMENT - using default"
    export ENVIRONMENT=default
    echo "Using $ENVIRONMENT as environment"
fi

java \
-Xmx500M -Xms200M \
-jar \
-Dnetworkaddress.cache.ttl=60 \
-Dspring.profiles.active=$ENVIRONMENT \
/usr/opt/service/filtering.jar
