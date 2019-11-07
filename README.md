# Openclassrooms Projet 07

# Structure du dév
## 1 processus  exposant les méthodes CRUD sur base PostgreSql :ServiceCrud RESTFull
## 1 processus  de type "Cron" pour envoi de email 
## 1 processus  exposant les méthodes UI : AppliWeb RESTFull
## 1 appli web 

# Persistence  
## 3 tables dans 3 shémas distincts

# Communication entre processus 
## HtttpClient

# Build 
## Maven multi-module (selon préco. OpenClassrooms)
## Base Postgres, sous psql , executer :
### 1) sql_uml/prep_projet07.sql
### 2) sql_uml/create_tbl.sql
### 3) cnx : 
#### role :rl_projet07 (mdp projet07)
#### db : db_projet07
#### shema : ouvrage, pret, usager


