# Openclassrooms Projet 07

# Structure du dév
## 1 processus  exposant les méthodes CRUD sur base PostgreSql :ServiceCrud RESTFull
## 1 processus  exposant les méthodes UI : AppliWeb RESTFull
## 1 processus  de type "Cron" pour envoi de email 
## Persistence  - 3 tables dans 3 shémas distincts
## Communication entre processus : OpenFeign
## Seul CRUD accède à la base
## Design : domain driven (organisation des sources selon préco. de Spring.io)

# Technique
## Swagger (selon préco. OpenClassrooms)
## IPC : openfeign (selon préco. OpenClassrooms)
## Spring Boot ,Jpa, Security
## PostGresSql avec base ,role, tablespace dedié au projet  
## Spring Data Jpa (i.e findUsagerByidUsager ...)
## Lombok pour Entity
## @Transient avec @PostLoad dans Entity Pret

# Build 
## Maven multi-module (selon préco. OpenClassrooms)
## Base Postgres, sous psql , executer :
### 1) sql_uml/prep_projet07.sql
### 2) sql_uml/create_tbl.sql

