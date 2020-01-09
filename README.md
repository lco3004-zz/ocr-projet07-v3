Refonte : spring mvc thymeleaf
==

Technique
==  
Services Rest (Jar exécutables)
--
* Un service pour les  méthodes CRUD sur base PostgreSql
* Un service type "Cron" pour envoi de email  

WebApp Spring MVC/ThymeLeaf  (Jar exécutable)
--
* Une application qui gère les pages html  ainsi que le controle d'accès (login/password)

Spring Boot  (OPENCLASSROOMS  - cours en anglais)
--
* Web Spring MVC avec ThymeLeaf (OPENCLASSROOMS  - cours en anglais)
* Security ( Login-Password, CORS)
* Data/jpa
* Hibernate (API Criteria)
* Embedded tomcat
* Note : utilisation de  la librairie Lombok

Base de donnée
--
* PostgreSql v12 

Communication entre services
--
* Httpclient (jdk 11) placée dans  une lib déployeé dans un repo local


Build, gestion de source
--
* Git version 2.24.0.windows.2
* Apache Maven  3.6.0
* Intellij IDEA 2019.3.1 Ultimate

Mise en oeuvre sous Windows
==
* Note : Le compte local doit appartenir au groupe 'administrateurs'
#### Installer Apache Maven V3 et  Oracle JDK 11
#### Modifier la policy pour autoriser l'exécution des scripts 'Set-ExecutionPolicy Unrestricted'
#### Verifier avec mvn --version
* Apache Maven 3.6.0 (97c98ec64a1fdfee7767ce5ffb20918da4f719f3; 2018-10-24T20:41:47+02:00)
* Java version: 11.0.4, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-11.0.4
* Default locale: fr_FR, platform encoding: Cp1252
* OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"

Clone et build des exécutables java
--
#### Créer un repertoire racine du projet - exemple c:\prj07
* Se placer dans le répertoire qui vient d'être créer
#### Cloner le projet : git clone https://github.com/lco3004/ocr-projet07-v3.git .
*  Générer les Jar exécutables avec mvn clean  install
*  Puis aller dans le répertoire lib_httpclient pour déployer le repo local avec mvn deploy
#### installer PostgresSql
* Lancer le service windows PostgreSql (barre de recherche Windows, saisir services.msc)
* La version utilisée est la 12
* Créer le répertoire des tablespaces :'c:\bd_data'
* Sous pgsql, exécuter le script sql_uml/prep_projet07.sql
* Installer pgadmin v4
* Sous pgadmin, attribuer le password projet07 au role rl_projet07
* Choisir la base  db_projet07 - mdp identique au role rl_projet07
* Enfin , exécuter sql_uml/create_tbl.sql
#### pour les services CRUD et WEB  
* Ouvrir un terminal puis se placer dans le répertoire du service ( ex : service_crud)
* Lancer le service avec mvn spring-boot:run'
#### pour le service Batch
#####dans le contexte de demo, il faut lancer le CRON immédiatement
* Se placer dans le répertoire du service
* Editer le fichier application.yml et valoriser les champs de la rubrique Mail
* lancer le service avec mvn spring-boot:run -Dspring-boot.run.arguments="immediat"
#### répertoires particuliers
* logs contient les traces applicatives  
* projet07-repo est le repo maven local (cf configuration "deploy" dans le pom de 'lib_httpclient' )
* Note : Ces répertoires sont supprimés lors d'un mvn clean

Utilisation de l'application
==
#### connexion avec un des usagers du jeu de test (ex : juie / julie)
#### Recherche d'ouvrage : 
* Insensible à la Casse 
* Fonctionne avec début de mot (ex  : chris renvoie Christophe)
#### Prêts : 
* le bouton "update" en regard d'un prêt déja prolongé n'apparait plus
#### Parametrage durée du prêt 
* Par défaut 4 semaines
* Paramètre dans properties.yml
#### Batch
* Déclenché tous les jours à 10 heures CET
