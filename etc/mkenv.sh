#!/bin/bash

TomcatVersion=7.0.39
Tomcat=apache-tomcat-${TomcatVersion}

if [[ $@ =~ (^| )"--help"($| ) ]] ; then
	echo "Usage: $0: [--get] [--conf]"
	echo "    --tomcat    Baja y expande Tomcat en .env/. ($Tomcat)"
	echo "    --conf      Configura Tomcat. Copia tomcat/* al .env/$Tomcat"
fi

mkdir -p .env
cd .env

if [[ $@ =~ (^| )"--tomcat"($| ) ]]; then
	if [[ ! -f ${Tomcat}.tar.gz ]] ; then
		curl -O http://archive.apache.org/dist/tomcat/tomcat-7/v${TomcatVersion}/bin/${Tomcat}.tar.gz
	fi
	tar zxf ${Tomcat}.tar.gz
	mv $Tomcat tomcat
	echo "Installed $Tomcat on $(pwd)/tomcat"
fi

if [[ $@ =~ (^| )"--conf"($| ) ]] ; then
	cp -r ../etc/tomcat/* tomcat
	echo "Configured $(pwd)/tomcat"
fi
