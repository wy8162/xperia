#!/usr/bin/env bash
docker run \
-v webapp-1.0:/usr/local/tomcat/webapps/webapp \
-it -p 8080:8080 \
tomcat

