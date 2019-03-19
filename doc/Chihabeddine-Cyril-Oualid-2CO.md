# Feedback de la première livraison

Groupe https://github.com/uca-m1informatique-softeng/projet-s2-19-2co

## Intégration continue ##
C’est bien opérationnel : https://travis-ci.com/uca-m1informatique-softeng/projet-s2-19-2co/builds

## Gestion de projet ##
vous avez utilisé des milestones, des issues et un kanban : c’est bien. Ce serait bien de prévoir les prochaines milestones.

## TDD appliqué à Takenoko ##
Il y a un scenario outline portant sur les objectifs bambous, avec 42 scénarii. Il y a juste le nom "motif" pour le l'objectif panda qui n'est pas très adapté.
C’est là aussi un bon début

## Etude d’architecture sur le découpage en services REST ##
L’étude présente un plan de conception de chaque composant en un service :
>we should create service classes for each of the game's components, namely Game, Gardener, Panda, Tile, HashBoard, BamBot, and RandomBot.

Cette approche est déconseillée :
  * cela multiplie les points d'échanges, et donc, cela complique la mise en place des services
  * certains composants ne doivent pas être exposés :
     * par exemple, les personnages, ne doivent pas être manipulés par les joueurs directement : cela pose des problèmes de vérification et de décomptes des coups joués par les clients/joueurs. Cela se traduira par une augmentation des liens/dépendences entre les composants.
     * exposer sa mécanique interne pour une API / un serveur, ce n'est pas une bonne idée : cela contraint les développements futurs et donne trop d'informations à l'extérieur

Il est plutôt conseillé d'avoir seulement deux web services : 1 client ("instancié plusieurs fois"), qui masque la stratégie (la nature du bot) et un autre serveur qui masque les opérations et ne donne accès en lecture aux données à travers le json.

Il est également conseillé d'expliciter l'ensemble des routes/échanges dans le scénario.
