INTRODUCTION

Travis CI permet de faire de l'intégration continue.



1 - L'Intégration continue

L'intégration continue est une pratique qui consiste à faire de l'intégration fréquente de code ; l'objectif de
cette pratique est de détecter tout de suite les erreurs.

Pour faire de l'intégration continue, on automatise le déclenchement des tests (les tests unitaires aussi bien que
les tests d'intégration), grâce à un outil tel que Travis, qui, à chaque fois qu'un développeur effectue un "push",
par exemple, fusionnera le code poussé.



2 - Travis CI

Travis fonctionne à partir d'un fichier de configuration ("YAML" ou "YML"), nommé ".travis", à l'intérieur duquel
on indique notamment le langage du logiciel, les instructions de compilation (à l'aide de Maven, par exemple, en
donnant la ligne de commande qui convient) et, peut-être, une demande de notifications (par "email", par exemple)
à chaque fois que la compilation échoue, par exemple.

Mais Travis se présente globalement comme une plate-forme, à partir de laquelle, notamment, nous pouvons paramétrer
les fréquences des "build" (par rapport aux "push", d'une part, et par rapport aux "pull request", d'autre part) et
faire un certain "monitoring" des compilations.



CONCLUSION

Travis peut également être chargé du déploiement (au cas, évidemment, où le "build" réussit)...
Nous pouvons voir concrètement ce que contient le fichier ".travis" avec l'exemple de fichier de configuration pour
Travis créé par ailleurs.



RÉFÉRENCES

Notamment, les pages du site officiel de Travis CI qui suivent :

- https://docs.travis-ci.com/user/for-beginners/
(cette page est à propos de la notion d'intégration continue et du principe général de fonctionnement de Travis ;
on trouve aussi, sur cette page, les notions fondamentales, de "phase", "job", "build", etc.)

- https://docs.travis-ci.com/user/tutorial/
(cette page explique, étape par étape, ce qu'il faut faire pour pouvoir utiliser Travis...)

- https://docs.travis-ci.com/user/languages/java/
(cette page donne un premier détail du contenu du fichier de configuration de Travis)

- https://docs.travis-ci.com/user/notifications/
(cette page précise plusieurs moyens d'envoi de notifications par Travis)

Nous pouvons aussi nous référer à la page suivante (qui indique très clairement comment utiliser Travis CI) :
http://www.vogella.com/tutorials/TravisCi/article.html#travis-yml-configuration