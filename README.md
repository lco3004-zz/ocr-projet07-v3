Projet Bibliothèque - projet 07
=

Technique 
==  
Référence 
---
* https://openclassrooms.com/fr/courses/5684146-create-web-applications-efficiently-with-the-spring-boot-mvc-framework

Environnement 
---
* Spring Boot, PostgreSql, httpClient, Git, Maven

Starters Spring Boot
---
* Web, Security, Data/Jpa, Hibernate, Mail

Jars executables
---
* Un service REST pour les  méthodes de persistence - JPA/Hibernate et  PostgreSql.
* Un batch  pour envoi de email - "@Scheduled" 
* Une Web application MVC - template engine : ThymeLeaf

Communication entre services
---
* Httpclient (jdk 11) placée dans  une lib déployeé dans un repo local

Build, gestion de source
---
* Git version 2.24.0.windows.2
* Apache Maven  3.6.0
* Intellij IDEA 2019.3.1 Ultimate

Installation sous Windows
==
Installation de l'environnement
---
* Installer Apache Maven V3 et  Oracle JDK 11
* Modifier la policy pour autoriser l'exécution des scripts 'Set-ExecutionPolicy Unrestricted'
* Verifier avec mvn --version

Clone depuis GitHub 
---
* Cloner depu is  https://github.com/lco3004/ocr-projet07-v3.git

Création  de la base  PostgresSql
---
* Lancer le service windows PostgreSql (barre de recherche Windows, saisir services.msc)
* Créer le répertoire des tablespaces :'c:\bd_data'
* Sous pgsql, exécuter le script sql_uml/prep_projet07.sql
* Installer pgadmin v4
* Sous pgadmin, attribuer le password projet07 au role rl_projet07
* Choisir la base  db_projet07 - mdp identique au role rl_projet07
* Enfin , exécuter sql_uml/create_tbl.sql

Lancement des applications
==
Application Web et Service Rest
---
* Ouvrir un terminal puis se placer dans le répertoire de l'application ( ex : service_crud)
* Lancer l'application avec mvn spring-boot:run'

Application  Batch
---
* Se placer dans le répertoire de l'application
* Editer le fichier application.yml et valoriser les champs de la rubrique Mail
* lancer le service avec mvn spring-boot:run -Dspring-boot.run.arguments="immediat"

Utilisation de l'application
== 
Login
---
* connexion avec un des usagers du jeu de test (ex : juie / julie)

Les Ouvrages - Recherche et liste
---
* Insensible à la Casse 
* Fonctionne avec début de mot (ex  : chris renvoie Christophe)

Les Prêts (Par défaut 4 semaines - dans properties.yml)
---
* le bouton "update" en regard d'un prêt permet de le prolonger

