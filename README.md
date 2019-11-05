# Openclassrooms Projet 07

# Couverture fonctionnelle (100%)
## liste des ouvrages
## liste des prêts d'un usager si usager existe
## prologation de pret si pret/usager existe
## création d'un pret si ouvrage disponible et usager existe
## restitution d'un pret si pret/usager existe

# Structure du dév
## 1 processus  exposant les méthodes CRUD sur base PostgreSql :ServiceCrud RESTFull
## 1 processus  exposant les méthodes UI : AppliWeb RESTFull
## Persistence  - 3 tables dans 3 shémas distincts
## Communication entre processus : OpenFeign

# Technique
## Swagger
## Spring Boot ,Jpa, Security
## PostGresSql avec base ,role, tablespace dedié au projet  
## Spring Data Jpa (i.e findUsagerByidUsager ...)
## Lombok pour Entity
## @Transient avec @PostLoad dans Entity Pret

# Queries : 
## JPQL - @NamedQuery ( avec jointure)
## Api Criteria (avec jointure ON ... WHERE) +DTO like
## Querie "auto" basée sur nom attribut 
## DTO - TODO car il faut un converter ??
