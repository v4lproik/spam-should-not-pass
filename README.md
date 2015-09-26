# Googanime Backend
[![Circle CI](https://circleci.com/gh/v4lproik/googanime-backend/tree/master.svg?style=shield)](https://circleci.com/gh/v4lproik/googanime-backend/tree/master)

Googanime-backend is the main core of a bigger project called Googanime. It is a platform between that is used by different other project such as [googanime-frontend](https://github.com/googanime/googanime-frontend/tree/master).

## Features implemented

It provides a powerful JEE platform that returns different types of information about animes, mangas, podcasts and so on.

## Backends (Available through docker containers)

- Mysql 5.7
- Redis 2.8.21
- Elasticsearch 1.5

## Pre-requisites

- Install Maven 
- Install docker-machine and docker-compose (cf. docker official website for FAQ)
- JDK 1.8
- Maestro & Python

## Installation

1. Clone the repository
2. `cd` to the created directory.
3. `export DOCKER_IP=$(docker-machine ip <YOUR-DOCKER-VM> 2>/dev/null)` to set your docker ip for the tests
4. `docker-compose -d up` to start docker containers
5. `mvn clean compile jetty:run -Dspring.profiles.active="test"` to launch the server (Reachable at http://localhost:8080/ by default)
6. `sh scripts/fill-elasticsearch.sh` to fill elasticsearch with some data
7. `http://localhost:8080/animes/?query=geass&fields=title&type=entry&render=mal` to browse to a test page
