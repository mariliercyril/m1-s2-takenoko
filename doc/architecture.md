# Architecture
## Introduction
Nous avons à peine initié une architecture REST ; cette dernière ne compte en effet, actuellement, que deux services : un **service de connexion** et un **service de souscription/inscription**. Par ailleurs, ces services sont, tous les deux, des services du serveur (du jeu). Dès lors, ils utilisent le même début d'URL : « **http://localhost:8080/takenoko** ». (Cependant, bien que les clients ne fournissent, à ce jour, aucun service, chacun d'eux est, *a priori*, lui aussi accessible, une valeur de port unique (et, déjà, différente de la valeur du port du serveur) pouvant lui être attribuée lorsqu'il est « instancié » (lorsqu'il est « démarré »). (Même si la valeur de l'adresse IP est la même (dans le cas où c'est la même machine), un client a en effet une valeur de port qui est égale à la somme de 9000 et la valeur de l'identifiant du client.) Ces attributions d'une valeur de port ont été mises en place au départ et, bien que nous ignorassions, à l'époque, comment nous allions développer les services, nous avons pensé, dès ce moment, que la communication devrait être non seulement possible du client au serveur mais aussi, possible, du serveur au client...)

## 1 – Le découpage REST et la documentation des routes
Le découpage REST ne s'effectuant que sur deux services, nous avons un routage trop simple pour que la documentation des routes soit contenue dans une section à part.

### 1.1 – Le service de connexion
Le service de connexion est le premier service que nous avons développé. N'ayant jamais eu, jusque-là, d'enseignement sur les architectures REST, ni jamais d'enseignement sur les architectures "client-serveur" en général, l'auteur de ce service l'a avant tout créé pour se faire la main ; aussi, il est probable qu'il y ait des défauts de conception à cet endroit. (La valeur de la route, notamment, pose peut-être question...)

#### 1.1.1 – La ressource
Bien que l'identifiant du client soit donné (en tant que variable de l'URI), aucune ressource n'est vraiment communiquée ici.

#### 1.1.2 – La méthode
La méthode de requête HTTP qui est employée à travers la consommation de ce service est une méthode **GET** : si le serveur est démarré, reçoit la requête et reçoit cette dernière correctement, il renvoie une réponse « OK » (avec un code « 200 ») sans corps ni en-tête.

#### 1.1.3 – La route
La valeur de la route est seulement la valeur de la route générale (du jeu, c'est-à-dire « /takenoko ») étendue par la valeur de l'identifiant (du client) ; pour un client donné, l'URL total est alors « http://localhost:8080/takenoko**/{id}** ». (Par exemple, pour le client dont la valeur de l'identifiant est **1**, l'URI total est « http://localhost:8080/takenoko**/1** ».) Le service permettant un simple « ping », nous ne voulions pas que la route soit « /takenoko**/clients**/{id} » : en effet, au moment où il consomme ce service, un client n'a pas nécessairement de ressource qui le représente.

### 1.2 – Le service de souscription/inscription
Le service de souscription/inscription est le premier service vraiment important, car, comme nous le précisons plus bas, c'est à partir de l'une des consommations de celui-ci que les parties (de jeu) sont déclenchées.

#### 1.2.1 – La ressource
La ressource qui est communiquée à travers la consommation de ce service est un objet de type Client. (La classe (Java) Client est une représentation simple du client, surtout définie par l'identifiant. On pourrait s'interroger sur l'intérêt d'avoir une telle classe : Pourquoi utiliser une enveloppe pour une donnée aussi simple qu'un nombre entier de type (primitif) « int » ? En fait, nous avions l'intention d'enrichir cette classe et, à terme, lui substituer une classe proche de la classe Player actuelle.)

#### 1.2.2 – La méthode
La méthode de requête HTTP qui est employée à travers la consommation de ce service est une méthode **POST** : si le serveur est démarré, reçoit la requête et la reçoit correctement, il récupère l'identifiant (à partir de l'objet de type Client, qui lui a été communiqué par le « client ») et s'en sert pour, d'une part, afficher l'information selon laquelle le client est inscrit et, d'autre part, générer une instance de Player correspondante...

#### 1.2.3 – La route
La valeur de la route est « /takenoko/clients »... (Nous avions à l'esprit d'avoir, peut-être, un service qui permette à un client d'interroger l'état du joueur (objet de type Player) qui lui a été « attribué ».)

## 2 – Les scénarios de démarrage et de déroulement de partie
La consommation par le dernier client du service de souscription/inscription déclenche le démarrage des parties...

En fait, le serveur « attend » un certain nombre de clients. Ce nombre, qui lui est donné juste avant qu'il soit démarré, est 2 par défaut, mais il est possible qu'il soit supérieur à 2, 2 étant aussi, ici, un minimum. Comme nous l'avons dit plus haut, une instance de Player est générée à chaque fois qu'un client consomme le service de souscription/inscription : dès lors, lorsque la taille de la liste des joueurs est égale au nombre de clients « attendus », le serveur lance les 1000 parties. (Pour bien faire, il aurait, entre autres, été nécessaire d'interdire la consommation du service en question à partir du moment où le nombre de clients « attendus » est atteint, ou mettre en place un système de file d'attente, ou, encore, dans le cas d'une version bien plus évoluée, permettre plusieurs jeux en parallèle.)

Une fois démarrées, les parties se déroulent comme elles se déroulaient dans le cadre de la version (finale) du 1er semestre.

## 3 – L'organisation des composants Spring implémentant l'architecture
Du côté du serveur (c'est-à-dire le fournisseur des services), on a un composant **@RestController** par service. Ces composants sont des composants *@Controller* ; chacun d'eux contient donc la définition d'un service, c'est-à-dire la définition d'une méthode déclarée comme une requête HTTP (au moyen d'une annotation de type @RequestMapping : respectivement @GetMapping et @PostMapping, actuellement)...

À chacun de ces composants correspond un composant du côté du client. Chaque composant correspondant est déclaré au moyen de l'annotation **@Bean** et doit s'exécuter, en suivant un certain ordre, après le démarrage de l'application : le service de connexion est d'abord consommé...

Pour que la consommation des services puisse être effectuée (par le client), un composant de type **RestTemplate** est utilisé dynamiquement. (Nous avons découvert tardivement qu'il existe une nouvelle classe de client, la classe [WebClient](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.html), représentant un client réactif et non bloquant (contrairement au client représenté par la classe RestTemplate) ; toutefois, étant donné que nous n'avons, actuellement, pas de mécanisme asynchrone, nous avons considéré que la classe RestTemplate suffisait.)

Concluons ce point en disant, par ailleurs, que, si nous avions développé plus de services, nous aurions probablement implémenté un contrôleur « façade », grâce à la classe [org.springframework.web.servlet.DispatcherServlet](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html). Actuellement, la « façade » est simplement représentée par le package qui contient les composants @RestController (et qui, pour le coup, porte le nom « facade »).

## Conclusion
Nous aurions voulu aller bien plus loin dans le développement de notre architecture ; par exemple, la mise en œuvre d'un service d'envoi des résultats aurait ajouté de la valeur à notre projet, car, ainsi, nous aurions eu une communication biunivoque : une fois que la parie est terminée, le serveur aurait envoyé ses résultats à chaque client. (Qui plus est, par ce service, nous aurions pu permettre un arrêt contrôlé de l'application : une fois qu'un client a reçu ses résultats, il s'arrête et, une fois que tous les clients sont arrêtés, le serveur s'arrête.)
