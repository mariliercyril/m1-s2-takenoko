# REST ARCHITECTURE OF TAKENOKO
---

## SUMMARY:
> 1. What is REST architecture?
2. Takenoko:
>> 1. Game Engine
>> 2. Player
3. Takenoko as a REST application (through a game scenario):
>> 1. Services
>> 2. REST controller

## REST ARCHITECTURE:
> Representational State Trasfer or REST is an architectural style allowing for the creation of RESTful Web Services (RWS). These web services allow for communication and resource manipulation between a server and a client through Requesting Systems.

> Data transmitted and manipulated with a RESTful web service can take many forms, among which is **JSON** short for **J**ava**S**cript **O**bject **N**otation.

> Through the Takenoko project, we will communicate data in JSON format, which is a serialization of objects, which we will refer to as model objects or instances, and in the other way around, a deserialization of the JSON data into those models instances identified by Media Types.

> The following paragraphs will briefly cover the current architecture of the Takenoko game we are working on. Through this architectural analysis, we will identify the different interfaces that are part of this REST API.

## TAKENOKO:
### GAME ENGINE:
> The game engine is the architectural part that basically runs the logic of the game, manages its rules, and links its different parts (the player, the characters, and the objectives).

> In this particular example, the game engine is represented by the package named "*game*" and precisely in the "*Game*" class.

> An object method "*start*" belonging to the class *Game* allows the players to take turns in playing through the object method "*play*" belonging to the *Player* class.

> This brings us to the Player side of the architecture which will be covered in the following sub-paragraph.

### PLAYER:
> A players in *Takenoko* is represented by a bot, either *BamBot* or *RandomBot*.

> Players, or bots, take turns in making moves through the object method "*execute*" mentioned before. Each type of bot has a different strategy.

## TAKENOKO AS A REST APPLICATION:
### SERVICES:
> In a REST web service, the concept of service is very important as it is the architectural part of the application or API that interacts with models and stored data.

> Services perform the **business logic**, they are the layer that comes between a controller and the storage.

> In a Spring Boot application implementing a RESFUL service, an annotation "**@Service**" to a class defines a service class. This service can be later referenced in a controller class which uses it to manipulate data or to simply fetch data.

> In order to implement a REST web service into the game *Takenoko*, we should create service classes for each of the game's components, namely *Game*, *Gardener*, *Panda*, *Tile*, *HashBoard*, *BamBot*, and *RandomBot*. Basically, any class whose objects can be manipulated by controllers or that can provide information or data about other classes, for instance, the class *HashBoard* that can provide information about the position of each tile and the free sposts where tiles could be placed...

> This brings us to controllers. Controller classes are defined in a REST web service with an annotation "**@RESTController**", which tells the framework Spring Boot to treat them as such. These latters can be subjects to routing, in other words, **Request Mapping**, which means that for each **HTTP request URI**, a specific controller is referred to, which in turns, refers to the right service and calling the right method.

> Resources on the other hand are not to be explicitly defined, they are data that can either be created, fetched or manipulated by the API.

> In our case, resources could be data about the different components of the game, they could be the coordinates of each tile, its color, its bamboo content, they also could be information about each bot such as the score...

> To illustrate this mecanism, we can suppose the following scenario, although brief, but gets to the point:
>> On the server side of our hypothetical RESTFUL web application/API Takenoko we can have a service and a controller class for each of the classes *Game*, *HashBoard*, *Tile*, and *Panda*, whereas on the client side, we can have a client application defining the class Bot.

>> Through the client application, a bot can send **GET-HTTP requests**. In one of the requests, the URI is defined as follows:

>> Domaine:Port/Game/Start/

>> The application on the server side will then call the method "*start*" of the **GameController**, which on turns, calls the method "*start*" of the corresponding service class, which starts a *Takenoko* game.

>> Depending on the implementation, the service class will call a method that fetches data about the Game object and forward it to the controller class which sends it back as a JSON object.

>> The application on the client side deserializes the JSON object and also depending on its own implementation, uses it to provide data for the bot to make other moves.
