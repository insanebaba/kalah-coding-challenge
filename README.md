# Project Description

## Project details 
1. No type of testing could be added due to the time constraint. Needs a week to implement unit testing, integration testing and contract testing.
2. Swagger ui is implemented to experiment with the api. url : ```http://localhost:8080/swagger-ui.html```
3. Both the endpoints are implemented _createNewGame_ and _makeMove_
4. Both endpoints are tested on local environment. 
5. Current endpoints are not limited to json only datatype.
6. Considering only one entity was used for operation, i.e. Kalah game, entire project is used as main directory instead of separating them by entities, i.e. if you want to add another entity to this api, e.g. _Accounts_ , create a root package __com.backbase.kalahcoding.challenge.accounts__ and then use corresponding directory structure as right now.
7. __HATEOS__ is not used to create a URL  as It was an overhead with little benefit. But would be recommended if more features are required from it.
8. Separation of concerns was one of the highest priorities to make this project maintainable.

Architecture details are as below

### Package wise Architecture
1. __adapter__ - This package provides high level adapter to interact for _controllers_ . Adapters help abstract functionality for controllers and remove any overhead of conversions, data interaction, etc. In case any conversion of exception to be done from runtime to front end exceptions . It should be done here.
2. __controller__ - This is the web controller for spring boot. This is where the api calls begin and get resolved. Any kind of changes to be done in the api should be done here.
3. __exception__ - This package holds all the exceptions used in the application. Considering we are not separating frontend and backend exceptions for this challenge, there is not other classification done. All the exceptions in this package can be send to the user. In case you want to classify and map. 
4. __mapper__ - This package contains mapper from DAO objects to DTO objects. I have not used any libraries for mapping as I found this to be easier. There are many customizations needed like adding URL. 
5. __models__ - 3 types of models are added to this application using java 14 records, 
    - __dao__ - Data Access Objects that are persisted in database.
    - __dto__ - Data transfer Objects used to send data to front end.
    - __app__ - Application wide Objects used across application
6. __repository__ - This package contains Database interacting repositories which are implemented using Spring CRUD repositories and injected in services.
7. __service__ - Perhaps the most important package. This is where all the functionality is implemented. Entire game and rules are implemented here. This packages should have been tested intensively as this is where all the business logic is implemented. Any issues in game can be drilled down to here.
8. __swagger__ - Swagger UI Configuration bean

---
## Coding challenge
#### The Goal
The goal of this test is for us to have some code to chat about on the interview, and for you to showcase your
programming skills.
Please note that the test is not so much about finishing and solving the problem, but about delivering a well designed
solution with code, tests and documentation that you find of good quality. Because we are mainly working with Java,
we would like you to do this in Java.
We are asking you to program a *Java RESTful Web Service* that runs a game of 6-stone Kalah. The general rules
of the game are explained on Wikipedia: https://en.wikipedia.org/wiki/Kalah and also below in this document. Please
note that the Wikipedia article explains 3 and 4-stone Kalah; we would like your implementation to be 6-stone.
This web service should enable to let 2 human players to play the game, each in his own computer. There is no AI
required.
#### Kalah Rules
Each of the two players has **six pits** in front of him/her. To the right of the six pits, each player has a larger pit, his
Kalah or house.
At the start of the game, six stones are put in each pit.
The player who begins picks up all the stones in any of their own pits, and sows the stones on to the right, one in
each of the following pits, including his own Kalah. No stones are put in the opponent's' Kalah. If the players last
stone lands in his own Kalah, he gets another turn. This can be repeated any number of times before it's the other
player's turn.
When the last stone lands in an own empty pit, the player captures this stone and all stones in the opposite pit (the
other players' pit) and puts them in his own Kalah.
The game is over as soon as one of the sides run out of stones. The player who still has stones in his/her pits keeps
them and puts them in his/hers Kalah. The winner of the game is the player who has the most stones in his Kalah.
#### Endpoint design specification
1. Creation of the game should be possible with the command:
 curl --header "Content-Type: application/json" \
 --request POST \
 http://<host>:<port>/games
 Response:
 HTTP code: 201
 Response Body: { "id": "1234", "uri": "http://<host>:<port>/games/1234" }
id: unique identifier of a game
url: link to the game created
2. Make a move:
```shell script
 curl --header "Content-Type: application/json" \
 --request PUT \
 http://<host>:<port>/games/{gameId}/pits/{pitId}
```
gameId: unique identifier of a game
pitId: id of the pit selected to make a move. Pits are numbered from 1 to 14 where 7 and 14 are the kalah (or house)
of each player
 Response:
 HTTP code: 200
 Response Body:
```json
 {"id":"1234","url":"http://<host>:<port>/games/1234","status":{"1":"4","2":"4","3":"4","4":"4","5":"4","6":"4","7":"0","8":"4","9":"4","10":"4","11":"4","12":"4","13":"4","14":"0"}}
```
status: json object key-value, where key is the pitId and value is the number of stones in the pit
