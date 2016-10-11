#!/bin/sh

#Gait
export APPNAME=gait-server
export LANG=en_US.utf8
export GaitAuth=off

#Options
export CATALINA_OPTS="-Xmx1200m -XX:MaxPermSize=256m -DAppName=$APPNAME"
export ANT_OPTS="-Xmx1200m $ANT_OPTS"
