--  -------------------------------------------------------------------------
-- et les Drop dans l'ordre
-- ---------------------------------------------------------------------------

DROP TABLE IF EXISTS pret.pret;
DROP TABLE IF EXISTS usager.user ;
DROP TABLE IF EXISTS ouvrage.ouvrage ;

DROP SEQUENCE IF EXISTS usager.user_iduser_seq;
DROP SEQUENCE IF EXISTS ouvrage.ouvrage_idouvrage_seq;

DROP SCHEMA IF EXISTS usager CASCADE ;
DROP SCHEMA IF EXISTS pret;
DROP SCHEMA IF EXISTS ouvrage;

-- ---------------------   SEQUENCE --------------------------------------------
-- pour commencer !!
-- SELECT * FROM pret.pret p  inner join user.user u on p.usager_idusager = u.idusager where u.idusager=2;
-- -------------------------------------------------------------------------

CREATE SCHEMA usager
    AUTHORIZATION rl_projet07;

CREATE SCHEMA pret
    AUTHORIZATION rl_projet07;

CREATE SCHEMA ouvrage
    AUTHORIZATION rl_projet07;

CREATE SEQUENCE usager.user_iduser_seq
    INCREMENT  1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE usager.user_iduser_seq
    OWNER TO rl_projet07;

CREATE SEQUENCE ouvrage.ouvrage_idouvrage_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE ouvrage.ouvrage_idouvrage_seq
    OWNER TO rl_projet07;

-- --------------------- TABLE --------------------------------------------
-- Tables :  BIEN RESPECTER l'ORDRE
-- -------------------------------------------------------------------------

CREATE TABLE usager.user
(
    iduser integer NOT NULL DEFAULT nextval('usager.user_iduser_seq'::regclass),
    userName character varying(256) COLLATE pg_catalog."default" NOT NULL DEFAULT 'laurent'::character varying,
    password character varying(1024) COLLATE pg_catalog."default" NOT NULL DEFAULT 'laurent'::character varying,
    email character varying(1024) COLLATE pg_catalog."default" NOT NULL DEFAULT 'laurent.cordier3004@gmail.com'::character varying,
    CONSTRAINT usager_pkey PRIMARY KEY (iduser)
        USING INDEX TABLESPACE ts_projet07
)
    WITH ( OIDS = FALSE ) TABLESPACE ts_projet07;

ALTER TABLE usager.user OWNER to rl_projet07;

CREATE TABLE  ouvrage.ouvrage
(
    idouvrage integer NOT NULL DEFAULT nextval('ouvrage.ouvrage_idouvrage_seq'::regclass),
    titre character varying(2048) COLLATE pg_catalog."default" NOT NULL,
    auteur character varying(256) COLLATE pg_catalog."default" NOT NULL,
    quantite integer NOT NULL DEFAULT 10,
    CONSTRAINT ouvrage_pkey PRIMARY KEY (idouvrage)
        USING INDEX TABLESPACE ts_projet07
)
    WITH
        ( OIDS = FALSE ) TABLESPACE ts_projet07;

ALTER TABLE ouvrage.ouvrage   OWNER to rl_projet07;

CREATE TABLE pret.pret
(
    ouvrage_idouvrage integer NOT NULL,
    user_iduser integer NOT NULL,
    pret_prolonge integer NOT NULL DEFAULT 0,
    date_emprunt date NOT NULL,
    CONSTRAINT pret_pkey PRIMARY KEY (ouvrage_idouvrage, user_iduser)
        USING INDEX TABLESPACE ts_projet07,
    CONSTRAINT fk_usager FOREIGN KEY (user_iduser)
        REFERENCES usager.user (iduser) MATCH FULL
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE ,
    CONSTRAINT fk_reference FOREIGN KEY (ouvrage_idouvrage)
        REFERENCES ouvrage.ouvrage (idouvrage)  MATCH FULL
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE ts_projet07;

ALTER TABLE pret.pret OWNER to rl_projet07;

-- --------------------- JEU de TEST --------------------------------------------
-- valeurs fixes pour ce projet - seul update sur "prolongation de l'emprunt"
-- -------------------------------------------------------------------------

INSERT INTO ouvrage.ouvrage(titre, auteur, quantite)
VALUES ('micro services with spring boot', 'ranga rao karanam',  10);

INSERT INTO ouvrage.ouvrage(titre, auteur, quantite)
VALUES ('learning spring boot 2.0', 'greg l. turnquist',  8);

INSERT INTO ouvrage.ouvrage(titre, auteur, quantite)
VALUES ('building web apps with spring 5 and angular', 'ranga ajitesh shukla', 12);

INSERT INTO ouvrage.ouvrage(titre, auteur, quantite)
VALUES ('Java the complete eleventh edition', 'Herbert Schildt', 2);

INSERT INTO ouvrage.ouvrage(titre, auteur, quantite)
VALUES ('Apache Maven maitrisez  l infra d un projet Java EE' , 'Maxime Greau Etienne Langlet', 4);

INSERT INTO ouvrage.ouvrage(titre, auteur, quantite)
VALUES ('Java JEE developpez des applicaitons Web en Java', 'Thierry richard', 2);

INSERT INTO ouvrage.ouvrage(titre, auteur, quantite)
VALUES ('JPA et Java Hibernate Apprenez le ORM avec Java', 'Martial Banon', 12);

INSERT INTO ouvrage.ouvrage(titre, auteur, quantite)
VALUES ('PostgreSQL Admin et exploit de vos base de données', 'Sébastien Lardiere', 12);

INSERT INTO ouvrage.ouvrage(titre, auteur, quantite)
VALUES ('Java Spring Le scocle technique des applis Java EE', 'Hervé le Morvan', 12);

INSERT INTO ouvrage.ouvrage(titre, auteur, quantite)
VALUES ('HTML5 et CSS3 faite evoluez le design de vos sites web', 'Christophe Aubry', 12);

INSERT INTO ouvrage.ouvrage(titre, auteur, quantite)
VALUES ('HTML5 et CSS3 maitrisez les standards de la creation de sites web', 'Christophe Aubry', 12);

INSERT INTO ouvrage.ouvrage(titre, auteur, quantite)
VALUES ('Mastering MicroServices with Java', 'Sourabh Sharma', 12);

INSERT INTO ouvrage.ouvrage(titre, auteur, quantite)
VALUES ('SCRUM piur une pratique vivante de l agilité', 'Claude Aubry', 12);

-- --

INSERT INTO usager.user(userName, password)
VALUES ('ibtisem', 'ibtisem');

INSERT INTO usager.user(userName, password)
VALUES ('lola','lola');

INSERT INTO usager.user(userName, password)
VALUES ('julie', 'julie');

INSERT INTO usager.user(userName, password)
VALUES ('jeff', 'jeff');

INSERT INTO usager.user(userName, password)
VALUES ('herbert', 'herbert');

INSERT INTO usager.user(userName, password)
VALUES ('greg', 'greg');

INSERT INTO usager.user(userName, password)
VALUES ('lao', 'lao');


-- --
INSERT INTO pret.pret (ouvrage_idouvrage, user_iduser, pret_prolonge, date_emprunt)
VALUES (8, 1, 0, '2019-11-08');

INSERT INTO pret.pret (ouvrage_idouvrage, user_iduser, pret_prolonge, date_emprunt)
VALUES (9, 1, 0, '2019-11-08');

INSERT INTO pret.pret (ouvrage_idouvrage, user_iduser, pret_prolonge, date_emprunt)
VALUES (10, 1, 0, '2019-11-08');

INSERT INTO pret.pret (ouvrage_idouvrage, user_iduser, pret_prolonge, date_emprunt)
VALUES (11, 1, 0, '2019-11-08');

INSERT INTO pret.pret (ouvrage_idouvrage, user_iduser, pret_prolonge, date_emprunt)
VALUES (3, 2, 0, '2019-10-01');

INSERT INTO pret.pret (ouvrage_idouvrage, user_iduser, pret_prolonge, date_emprunt)
VALUES (4, 2, 0, '2019-09-21');

INSERT INTO pret.pret (ouvrage_idouvrage, user_iduser, pret_prolonge, date_emprunt)
VALUES (5, 2, 0, '2019-10-07');

INSERT INTO pret.pret (ouvrage_idouvrage, user_iduser, pret_prolonge, date_emprunt)
VALUES (6, 2, 0, '2019-11-08');

INSERT INTO pret.pret (ouvrage_idouvrage, user_iduser, pret_prolonge, date_emprunt)
VALUES (7, 2, 0, '2019-11-08');


INSERT INTO pret.pret (ouvrage_idouvrage, user_iduser, pret_prolonge, date_emprunt)
VALUES (2, 3, 1, '2019-11-08');

INSERT INTO pret.pret (ouvrage_idouvrage, user_iduser, pret_prolonge, date_emprunt)
VALUES (1, 3, 0, '2019-11-10');

INSERT INTO pret.pret (ouvrage_idouvrage, user_iduser, pret_prolonge, date_emprunt)
VALUES (12, 3, 0, '2019-11-08');


INSERT INTO pret.pret (ouvrage_idouvrage, user_iduser, pret_prolonge, date_emprunt)
VALUES (13, 3, 0, '2019-11-08');


-- fin --