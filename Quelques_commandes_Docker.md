# Quelques commandes Docker...
Une fois que le compte sur le hub de Docker est créé (par exemple, "projets2192co"), on créé un dépôt (par exemple, "takenoko") ;
puis, afin d'y avoir une première image, on commence par s'authentifier :

	$ docker login -u projets2192co -p <password>

puis l'on construit la première image :

	$ ./mvnw install dockerfile:build
(ou "**docker build -t projets2192co/takenoko:<tag>**", la valeur du "tag" pouvant être pour le serveur, par exemple, "server")

après s'être assuré que son contenu "fonctionne" :

	$ ./mvnw clean package && <execution_command>
(la commande peut être, par exemple, pour l'exécution du serveur : "**java -jar target/takenoko-server-1.0-CLIENT-SERVER.jar**")

image que l'on pousse alors vers la plate-forme de Docker :

	$ ./mvnw dockerfile:push
(ou "**docker push projets2192co/takenoko:<tag>**")

Enfin, on peut s'assurer qu'elle fonctionnne (l'image du serveur, par exemple) :

	$ docker run -p 8080:8080 -t projets2192co/takenoko:server
