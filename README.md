# Openclassrooms Projet 07

# Structure du dév
## 1 service Rest  exposant les méthodes CRUD sur base PostgreSql 
## 1 service Rest  de type "Cron" pour envoi de email 
## 1 service Rest  exposant les méthodes UI ainsi que le controle d'accès 
## 1 appli web 

# Persistence  
## un tablespace pour 3 tables dans 3 shémas distincts

# Communication entre processus 
## HtttpClient ;  Json

# Controle d'accès : authentification, autorisation
## Spring Security 
## Note d'avancement : Mise en oeuvre complète (par une simple classe CustomAuthentication )
## la démarche est simpliste mais me semble suffisante dans ce contexte 
## authentification permet de retrouver les infos de l'Usager (id, passwd ...) 
## lors de l'appel des API de service_web.
## Test de  authentification et de autorisation pour les APIS du service_web (Ok toutes
## Test des APis  liste des ouvrages, liste des prets, prolongation (OK toutes)
## !!!!!!!! Le mdp est  en clair : la création d'un user est hors périmètre projet
## ----- il n'a y a pas de Spring Session ou équivalent ,ni de csrf ni de "CORS"

# Build 
## Maven 

## Base Postgres, sous psql , executer :
### 1) sql_uml/prep_projet07.sql
### 2) sql_uml/create_tbl.sql
### 3) cnx : 
#### role :rl_projet07 (mdp projet07)
#### db : db_projet07


