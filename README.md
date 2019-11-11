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

# Controle d'accès
## Spring Security 

# Build 
## Maven 

## Base Postgres, sous psql , executer :
### 1) sql_uml/prep_projet07.sql
### 2) sql_uml/create_tbl.sql
### 3) cnx : 
#### role :rl_projet07 (mdp projet07)
#### db : db_projet07


