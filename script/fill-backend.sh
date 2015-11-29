#!/bin/sh

curl -X POST -H "Content-type: application/json" -H "Accept: application/json" -d "{\"firstname\":\"spider\", \"lastname\":\"cochon\", \"email\":\"spider.cochon@email.fr\", \"permission\":\"REGULAR\", \"status\":\"USER\", \"password\":\"spidercochon\", \"corporation\":\"google\"}" http://localhost:8080/user/create
token=$(curl -X POST -H "Content-type: application/json" -H "Accept: application/json" -d "{\"email\":\"spider.cochon@email.fr\", \"password\":\"spidercochon\"}" http://localhost:8080/user/auth | python -c 'import json,sys;obj=json.load(sys.stdin);print obj["token"]')
curl -X POST -H "x-auth-token: $token" -H "Content-type: application/json" -H "Accept: application/json" d "{\"properties\": [{\"variableName\":\"userId\", \"variableType\":\"java.lang.String\"}]}" http://localhost:8080/spammer/create-spammer-document
#creaste new context
curl -X POST -H "Content-type: application/json" -H "Accept: application/json"  -H "x-auth-token: $token" -d "{\"name\": \"User register\"}" http://localhost:8080/context/create
curl -X POST -H "Content-type: application/json" -H "Accept: application/json"  -H "x-auth-token: $token" -d "{\"name\": \"rule1\", \"rule\": \"rule1\", \"type\": \"SPAM\"}" http://localhost:8080/rule/create
curl -X POST -H "Content-type: application/json" -H "Accept: application/json"  -H "x-auth-token: $token" -d "{\"name\": \"rule2\", \"rule\": \"rule2\", \"type\": \"SPAM\"}" http://localhost:8080/rule/create
curl -X POST -H "Content-type: application/json" -H "Accept: application/json"  -H "x-auth-token: $token" http://localhost:8080/rule/list