# Googanime Backend
[![Circle CI](https://circleci.com/gh/v4lproik/googanime-backend/tree/master.svg?style=shield)](https://circleci.com/gh/v4lproik/googanime-backend/tree/master)

This project aims to provide a clear and nice interface to whoever is seeking for entry/manga information.

## Features implemented

This is a the main core of the project. It provides a powerful JEE RESTful API that returns different types of information about animes, mangas, podcasts and so on.

## Pre-requisites

- Install Maven 
- Install docker or boot2docker (MacOSX user only)
- JDK 1.8
- Maestro & Python

## Installation

1. Clone the repository
2. `cd` to the created directory.
3. `<ELASTICSEARCH_NODE_IP> es.googlanime` to add the dns name to your /etc/hosts file 
4. `cd` to backend/
5. `mvn clean compile jetty:run -Dspring.profiles.active="test"` to launch the server (Reachable at http://localhost:8080/ by default)
6. `docker-compose -d up` to start docker containers
6. `sh scripts/fill-elasticsearch.sh` to fill elasticsearch with some data
7. `http://localhost:8080/animes/?query=geass&fields=title&type=entry&render=mal` to browse to a test page