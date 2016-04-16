# Spam Should Not Pass
[![Circle CI](https://circleci.com/gh/v4lproik/spam-should-not-pass/tree/master.svg?style=svg&circle-token=9ffca1e5226209ebb2be4b03a1218288b9eb7ce1)](https://circleci.com/gh/v4lproik/spam-should-not-pass/tree/master)
## Features implemented

It provides a powerful JEE platform that performs spam checks

## Backends (Available through docker containers)

- Postgresql 9.4
- Redis 2.8.21
- Elasticsearch 1.5
- DynamoDB
- Sqs

## Pre-requisites

- Install Maven 
- Install docker-machine and docker-compose (cf. docker official website for FAQ)
- JDK 1.8

## Installation

1. Clone the repository
2. `cd` to the created directory
3. `export DOCKER_IP=$(docker-machine ip <YOUR-DOCKER-VM> 2>/dev/null)` to set your docker ip for the tests
4. `docker-compose up -d` to start docker containers
5. `mvn clean test -Dspring.profiles.active="test"` to create database schemes
6. `mvn clean compile jetty:run -Dspring.profiles.active="test"` to launch the server (Reachable at http://localhost:8080/ by default)
7. `cd script` to the script directory 
8. `sh fill-backend.sh` to populate the database with accounts test/test and admin/admin
