# Takenoko releases
presented by Angry Raccoons

## 1.0.0 : First iteration
First draft of the game : minimal representation 
### What's been done
* Basic game engine handling tile placing
* A player that places tiles at random places
* Score increases by default when the player plays
* The game ends when a player is at 9 points
* Basic objective : returns that an objective is completed when 2 tiles are aligned

## SEP24 : Colors
Tiles can now have colors, and the player and the objectives are updated accordingly
### What's been done
* Added colors to tiles
* New objective : 3 tiles with the same color aligned
* Creation of a tile deck in which the player takes a tile
* Tile deck creation implied the player's process of choosing a tile among 3 tiles and putting the two others back under the deck

## SEP25 : Bamboos
We can now grow bamboos on tiles ! All the tiles are considered irrigated by default in this iteration.
As it was a small iteration, we also did a bit of refactoring.
### What's been done
* A bamboo grows on a tile when placed on the board
* The bamboo can grow (not used in this iteration)
* Make all tiles irrigated (and take the flag into account to grow a bamboo)
* **[refact]** Score is now computed based on objective completion
* **[refact]** Players now have an unique ID (int increasing in the order in which they were created)

## SEP26 : Gardener
We now have a gardener that can move on the board ! It grows a bamboo on the tiles next to it if the tile has the same color as the one he is on.
### What's been done
* The gardener can move in a straight line
* Where the gardener moves, a bamboo grows, as well as on the compatible neighbour tiles (color + irrigation).
* **[refact]** Players now can choose an indefinite amount of actions (some of which have a cost) and play them in indefinite order.

## OCT03 : Panda
We now have a moving and eating panda !
### What's been done
* The panda can move in a straight direction
* Where the panda moves, he eats a chunk of banboo
* The player takes the eaten bamboo in its stomach
* You can now launch 1000 games at the same time to display stats !
* The player can now validate two different types of objectives : "three bamboo of the same color align" and "eating 2 bamboos of the same color"
* Each objective is worth a certain amount of points according to the rules of the game
* **[refact]** Constants file for all the fame constants

## OCT10 : Irrigations
The players can now put some irrigation channels and manage the irrigation of the tiles !
### What's been done
*   The players can claim irrigation channels, as an action
*   The players can put irrigation channels on the board, in between two tiles
*   The tiles are irrigated only if an irrigation channel is put on it
*   We now have a logger, managing all the output of the game
*   **[refact]** The objectives are now managed in a pool, allowing to seamlessly update them when needed


## OCT24 : AI
*   The bambot is now way more efficient than the random bot
*   The random bot has been improved, it deals with all the implemented aspects of the game
*   We implemented a lot of useful tools allowing to get informations for the bots :
    *   We can provide them easily the position where an irrigation channel can be put
    *   We can provide them with the irrigated tiles
    *   And a lot more
*   **[refact]** Spring integration ! Our game is now built with the spring framework

## OCT31 : Improvements
*   The improvements are represented in the game : the enclosure, the fertilizer and the watershed
*   A first side of the weather dice is represented, the clouds
*   For now, the player picks an available improvement and put it down immediately
*   Final pattern objective are now playable as in the real game
*   **[refact]** More spring integration : the Board is now implemented as a spring component

## FINAL
The project has ended : Here is our final touches for this final iteration.
### What's been done
*   End of game : the emperor is distributed and the game goes on until we reach the player having won the emperor
*   1000 games : now have the wanter battles : Bambot VS RandomBot and BamBot VS BamBot
*   1000 games : we present the stats the client wanted and display them in a pretty way
*   **[refact]** Panda and Gardener are now components !