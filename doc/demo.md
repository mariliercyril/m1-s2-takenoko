# Démonstration
## Fonctionnement de notre utilisation de Docker
Le script « launcher.sh » commence par ouvrir un second terminal, dans lequel il exécute deux commandes Docker (fournies par le script « docker\_run\_server.sh ») :

	docker pull projets2192co/takenoko:server
qui tire l'image « server » qui est dans le dépôt (privé) « takenoko » de notre registre « projets2192co » (voir la documentation officielle sur la commande [docker pull](https://docs.docker.com/engine/reference/commandline/pull/)) ;

	docker run -p 8080:8080 -t projets2192co/takenoko:server
qui instancie l'image précédemment tirée et exécute l'instance ainsi obtenue (dans un conteneur) sur l'hôte (local) Docker (voir la documentation officielle sur la commande [docker run](https://docs.docker.com/engine/reference/commandline/run/)).

Le serveur est alors démarré (dans ce second terminal)... (Notons que le « Dockerfile » qui sert à construire les images du serveur permet à Docker d'utiliser (directement) la classe principale (com.cco.takenoko.server.TakenokoServer) pour exécuter une instance.)

Dans le premier terminal, le script lance ensuite une compilation du (module du) client, avec exécution des tests ; or les seuls tests qui ont été développés jusqu'à présent dans le client sont les tests d'intégration, couvrant les (deux) services REST : nous pouvons non seulement voir, dans le terminal du « client », que ces tests réussissent, mais aussi, dans le second terminal, que le « serveur » (dockérisé) réagit correctement à l'exécution des tests du service de souscription/inscription. L'affichage des (*deux*) représentations de joueur (sous format JSON), suivi des résultats des 1000 parties entre les (*deux*) joueurs, indique en effet que la méthode Java définissant le service de souscription/inscription a bien été consommé.

## Plan de « build » spécifique dans Travis CI
Nous ne pouvons pas parler de « build », à ce jour, dans le sens où un seul « job » est effectué par Travis CI.
La démonstration reproduit ce « job » (à travers une phase « before_script », durant laquelle le serveur est démarré, et une phase « script », durant laquelle le client est compilé) : la seule différence importante avec ce qui se passe lors de la démonstration tient au fait qu'à l'intérieur de l'environnement de Travis CI, l'exécution du conteneur Docker a lieu en arrière-plan (grâce à l'option « -d », ou « -detach »). (Nous ne savions pas comment faire pour que Travis CI crée deux environnements en même temps...)

Enfin, nous sommes conscients du fait que la dockérisation de notre projet a à peine été entreprise. Il aurait été intéressant d'avoir, notamment, une dockérisation totale, de notre projet. Une première difficulté se serait alors posée, avec la dockérisation du client : afin de construire une image du « client » qui prenne en compte les tests d'intégration, il aurait fallu démarrer le serveur avant de lancer le « build »... Demander à Docker de lancer plusieurs « instances » de client aurait sans doute été aussi intéressant, tout comme demander à Docker de lancer ces « instances » sur des machines séparées, etc.
