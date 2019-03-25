FROM ubuntu:18.04

MAINTAINER Kamil Chalczynski <kamil.chalczynski@student.uj.edu.pl>

RUN useradd ujot --create-home

RUN apt-get update && apt-get install -y vim unzip curl git

# dodaj konfiguracjê tutaj
RUN apt-get update && apt-get -y install default-jdk scala

#instalacja sbt
RUN apt-get update && apt-get install -y gnupg2


RUN echo "deb https://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list && \
	apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823 && \
	apt-get update && \
	apt-get -y install sbt
#

USER ujot

RUN java -version
RUN scala -version

CMD /bin/bash