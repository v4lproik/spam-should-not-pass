#!/bin/sh

#create user
curl -X POST -H "Content-type: application/json" -H "Accept: application/json" -d "{\"firstname\":\"spider\", \"lastname\":\"cochon\", \"email\":\"spider.cochon@email.fr\", \"permission\":\"REGULAR\", \"status\":\"USER\", \"password\":\"spidercochon\", \"corporation\":\"google\"}" http://localhost:8080/user/create
#get token
token=$(curl -X POST -H "Content-type: application/json" -H "Accept: application/json" -d "{\"email\":\"spider.cochon@email.fr\", \"password\":\"spidercochon\"}" http://localhost:8080/user/auth | python -c 'import json,sys;obj=json.load(sys.stdin);print obj["token"]')
#create models
curl -X POST -H "x-auth-token: $token" -H "Content-type: application/json" -H "Accept: application/json" -d "{\"properties\": [{\"variableName\":\"userId\", \"variableType\":\"java.lang.String\"},{\"variableName\":\"firstname\", \"variableType\":\"java.lang.String\"},{\"variableName\":\"lastname\", \"variableType\":\"java.lang.String\"}, {\"variableName\":\"isPremium\", \"variableType\":\"java.lang.Boolean\"}]}" http://localhost:8080/spammer/create-spammer-document
curl -X POST -H "x-auth-token: $token" -H "Content-type: application/json" -H "Accept: application/json" -d "{\"properties\": [{\"variableName\":\"documentId\", \"variableType\":\"java.lang.String\"}, {\"variableName\":\"object\", \"variableType\":\"java.lang.String\"},{\"variableName\":\"content\", \"variableType\":\"java.lang.String\"}]}" http://localhost:8080/spam/create-spam-document
#create new context
curl -X POST -H "Content-type: application/json" -H "Accept: application/json"  -H "x-auth-token: $token" -d "{\"name\": \"User register\"}" http://localhost:8080/context/create
curl -X POST -H "Content-type: application/json" -H "Accept: application/json"  -H "x-auth-token: $token" -d "{\"name\": \"Submit publication\"}" http://localhost:8080/context/create
#create rules
curl -X POST -H "Content-type: application/json" -H "Accept: application/json"  -H "x-auth-token: $token" -d "{\"name\": \"Non-Premium user with lastname equal firstname\", \"rule\": \"isPremium == true && firstname.equals(lastname)\", \"type\": \"SPAMMER\"}" http://localhost:8080/rule/create
curl -X POST -H "Content-type: application/json" -H "Accept: application/json"  -H "x-auth-token: $token" -d "{\"name\": \"Object equals content\", \"rule\": \"object.equals(content)\", \"type\": \"SPAM\"}" http://localhost:8080/rule/create
