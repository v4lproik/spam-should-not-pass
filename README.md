# Spam Should Not Pass
[![Circle CI](https://circleci.com/gh/v4lproik/spam-should-not-pass.svg?style=svg)](https://circleci.com/gh/v4lproik/spam-should-not-pass)

## Features implemented

It provides a powerful JEE platform that performs spam checks

## Backends (Available through docker containers)

- Postgresql 9.4
- Redis 2.8.21
- Elasticsearch 1.5

## Pre-requisites

- Install Maven 
- Install docker-machine and docker-compose (cf. docker official website for FAQ)
- JDK 1.8

## Installation

1. Clone the repository
2. `cd` to the created directory.
3. `export DOCKER_IP=$(docker-machine ip <YOUR-DOCKER-VM> 2>/dev/null)` to set your docker ip for the tests
4. `docker-compose -d up` to start docker containers
5. `mvn clean compile jetty:run -Dspring.profiles.active="test"` to launch the server (Reachable at http://localhost:8080/ by default)

## Useful curl commands
`curl -X POST -d "firstname=spider&lastname=cochon&email=spidercochon2@email.fr&password=spidercochonlikesSushis999&status=USER&permission=REGULAR" http://localhost:8080/user/create`  
`curl -X POST -d "login=spidercochon2@email.fr&password=spidercochonlikesSushis999" http://localhost:8080/user/auth`  
`curl -X POST -H "x-auth-token: 30363eb0-5ba0-4023-a754-f7056afad4fd" -H "Content-type: application/json" -H "Accept: application/json" -d "{\"test\":\"test\"}" http://localhost:8080/spammer/create-spammer-document`  