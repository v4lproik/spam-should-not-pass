#!/bin/sh

#create user
email="spider.cochon5@email.fr"
curl -X POST -H "Content-type: application/json" -H "Accept: application/json" -d "{\"firstname\":\"spider\", \"lastname\":\"cochon\", \"email\":\"$email\", \"permission\":\"REGULAR\", \"status\":\"USER\", \"password\":\"spidercochon\", \"corporation\":\"google\"}" http://localhost:8080/user/create
#get token
token=$(curl -X POST -H "Content-type: application/json" -H "Accept: application/json" -d "{\"email\":\"$email\", \"password\":\"spidercochon\"}" http://localhost:8080/user/auth | python -c 'import json,sys;obj=json.load(sys.stdin);print obj["token"]')
#create models
curl -X POST -H "x-auth-token: $token" -H "Content-type: application/json" -H "Accept: application/json" -d "{\"properties\": [{\"variableName\":\"userId\", \"variableType\":\"java.lang.String\", \"locked\":\"true\", \"position\":\"1\", \"provided\":\"false\"},{\"variableName\":\"firstname\", \"variableType\":\"java.lang.String\", \"position\":\"2\", \"locked\":\"true\", \"provided\":\"false\"},{\"variableName\":\"lastname\", \"variableType\":\"java.lang.String\", \"position\":\"3\", \"locked\":\"true\", \"provided\":\"false\"}, {\"variableName\":\"isPremium\", \"variableType\":\"java.lang.Boolean\", \"position\":\"4\", \"locked\":\"false\", \"provided\":\"false\"}, {\"variableName\":\"docSubmittedLast5Min\", \"variableType\":\"java.lang.Integer\", \"position\":\"5\", \"locked\":\"true\", \"provided\":\"true\"}]}" http://localhost:8080/scheme/create/user
curl -X POST -H "x-auth-token: $token" -H "Content-type: application/json" -H "Accept: application/json" -d "{\"properties\": [{\"variableName\":\"documentId\", \"variableType\":\"java.lang.String\", \"position\":\"1\", \"locked\":\"true\", \"provided\":\"false\"}, {\"variableName\":\"object\", \"variableType\":\"java.lang.String\", \"position\":\"2\", \"locked\":\"true\", \"provided\":\"false\"},{\"variableName\":\"content\", \"variableType\":\"java.lang.String\", \"position\":\"3\", \"locked\":\"true\", \"provided\":\"false\"}]}" http://localhost:8080/scheme/create/document
#create new context
contextId1=$(uuidgen)
contextId2=$(uuidgen)
curl -X POST -H "Content-type: application/json" -H "Accept: application/json"  -H "x-auth-token: $token" -d "{\"id\":\"$contextId1\", \"name\": \"User register\"}" http://localhost:8080/context/create
curl -X POST -H "Content-type: application/json" -H "Accept: application/json"  -H "x-auth-token: $token" -d "{\"id\":\"$contextId2\", \"name\": \"Submit publication\"}" http://localhost:8080/context/create
#create rules
curl -X POST -H "Content-type: application/json" -H "Accept: application/json"  -H "x-auth-token: $token" -d "{\"name\": \"Non-Premium user with lastname equal firstname\", \"rule\": \"isPremium == true && firstname.equals(lastname)\", \"type\": \"USER\"}" http://localhost:8080/rule/create
curl -X POST -H "Content-type: application/json" -H "Accept: application/json"  -H "x-auth-token: $token" -d "{\"name\": \"Object equals content\", \"rule\": \"object.equals(content)\", \"type\": \"DOCUMENT\"}" http://localhost:8080/rule/create
#verify api call
curl -X POST -H "Content-type: application/json" -H "Accept: application/json"  -H "x-auth-token: $token" -d "{\"context\":\"contextId2\",\"information\":[{\"key\":\"documentId\",\"value\":\"cb7f82f1-4dcd-4d62-80e5-deedb97e6972\"},{\"key\":\"userId\",\"value\":\"24e34891-b993-43e5-bc02-c0bd3439212c\"},{\"key\":\"email\",\"value\":\"fakeEmail@fakeEmail.fr\"},{\"key\":\"content\",\"value\":\"super content news\"},{\"key\":\"object\",\"value\":\"title\"}]}" http://localhost:8080/api/v1/check
