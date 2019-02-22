# Qu'est-ce que Travis CI ?

## INTRODUCTION

Travis CI permet de faire de l'intégration continue.

## 1 - L'Intégration continue

L'intégration continue est une pratique qui consiste à faire de l'intégration de code qui soit la plus fréquente possible ; l'objectif de cette pratique est d'avoir le meilleur contrôle possible sur les risques de régression et, par là, permettre un développement plus sain.

Pour faire de l'intégration continue, on automatise donc la compilation (avec les tests (unitaires, entre autres, etc.), préférentiellement), grâce à un outil tel que Travis, qui, à chaque fois qu'un développeur effectue un "push", par exemple, lance une compilation.

## 2 - Travis CI

Travis fonctionne à partir d'un fichier de configuration ("YML", qui est une réduction de "YAML" (YAML Ain't Markup Language), nommé ".travis", à l'intérieur duquel on indique notamment le langage du logiciel, les instructions de compilation (à l'aide de Maven, par exemple, en donnant la ligne de commande qui convient...) et, peut-être, une demande de notifications (par "email", par exemple) à chaque fois que la compilation échoue, par exemple.

Travis se présente globalement comme une plate-forme, à partir de laquelle, notamment, nous pouvons paramétrer les fréquences des "build" (sur les "push", d'une part, et les "pull request", d'autre part) et faire un certain "monitoring" des compilations.

**Travis procède comme suit** :
1. Il clone le code. (Ainsi, il "travaillera" sur des copies du code...)
2. Il fait une première compilation (définie par défaut: sans test ni génération de la Javadoc)...
3. Il exécute le script contenant les instructions d'intégration, pour une seconde compilation par exemple (avec les tests unitaires, cette fois).
4. Il retourne un résultat :
		"the command <command_script> exited with 0" si le "build" a réussi,
		"the command <command_script> failed and exited with <during_value> during" s'il a échoué finalement. (Travis CI relance la commande un _certain_ nombre de fois en cas d'échec.)

## CONCLUSION

Travis peut également être chargé du déploiement (au cas, évidemment, où le "build" réussit)...
Nous pouvons voir concrètement ce que peut contenir le fichier ".travis" avec [l'exemple de fichier de configuration pour Travis créé par ailleurs](https://github.com/uca-m1informatique-softeng/projet-s2-19-2co/blob/preparatory-work/travis-ci_studies/travis-configuration-file-example/.travis.yml).

## RÉFÉRENCES

Nous pouvons d'abord nous référer aux pages du site officiel de Travis CI dont les URL suivent :

- https://docs.travis-ci.com/user/for-beginners/
(Cette page porte sur la notion d'intégration continue et le principe général de fonctionnement de Travis ; on y trouve aussi les notions fondamentales, de "phase", "job", "build", etc.)

- https://docs.travis-ci.com/user/tutorial/
(Cette page présente, étape par étape, ce qu'il faut faire pour utiliser Travis...)

- https://docs.travis-ci.com/user/languages/java/
(Cette page offre un premier détail du contenu du fichier de configuration de Travis pour un projet en Java.)

- https://docs.travis-ci.com/user/notifications/
(Cette page précise plusieurs moyens d'envoi de notifications par Travis, dont les envois par "email".)

Nous pouvons aussi nous référer à la page dont l'URL suit :
http://www.vogella.com/tutorials/TravisCi/article.html#travis-yml-configuration
(Cette page indique notamment comment paramétrer Travis CI.)
