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

ALTER TABLE usager."user" OWNER to rl_projet07;

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
    usager_idusager integer NOT NULL,
    pret_prolonge integer NOT NULL DEFAULT 0,
    date_emprunt date NOT NULL,
    CONSTRAINT pret_pkey PRIMARY KEY (ouvrage_idouvrage, usager_idusager)
        USING INDEX TABLESPACE ts_projet07,
    CONSTRAINT fk_usager FOREIGN KEY (usager_idusager)
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


INSERT INTO ouvrage.ouvrage(
    titre, auteur, quantite)
VALUES ('micro services with spring boot', 'ranga rao karanam',  10);

INSERT INTO ouvrage.ouvrage(
    titre, auteur, quantite)
VALUES ('learning spring boot 2.0', 'greg l. turnquist',  8);

INSERT INTO ouvrage.ouvrage(
    titre, auteur, quantite)
VALUES ('building web apps with spring 5 and angular', 'ranga ajitesh shukla', 12);

INSERT INTO usager."user"(
    userName, password)
VALUES ('ibtisem', 'ibtisem');

INSERT INTO usager."user"(
    userName, password)
VALUES ('lola','lola');

INSERT INTO usager."user"(
    userName, password)
VALUES ('julie', 'julie');

-- fin --
