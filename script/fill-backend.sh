#!/bin/sh

curl -X POST -H "Content-type: application/json" -H "Accept: application/json" -d "{\"firstname\":\"spider\", \"lastname\":\"cochon\", \"email\":\"spider.cochon@email.fr\", \"permission\":\"REGULAR\", \"status\":\"USER\", \"password\":\"spidercochon\", \"corporation\":\"google\"}" http://localhost:8080/user/create
token=$(curl -X POST -H "Content-type: application/json" -H "Accept: application/json" -d "{\"email\":\"spider.cochon@email.fr\", \"password\":\"spidercochon\"}" http://localhost:8080/user/auth | python -c 'import json,sys;obj=json.load(sys.stdin);print obj["token"]')
curl -X POST -H "x-auth-token: $token" -H "Content-type: application/json" -H "Accept: application/json" -d "{\"java.lang.String\":\"userId\"}" http://localhost:8080/spammer/create-spammer-document
