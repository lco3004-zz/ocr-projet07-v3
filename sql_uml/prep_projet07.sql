-- ***************************************
-- Creation du  Role: rl_projet07
-- **************************************

DROP ROLE IF EXISTS rl_projet07;

CREATE ROLE rl_projet07 WITH
  LOGIN
  SUPERUSER
  CREATEDB
  CREATEROLE
  INHERIT
  NOREPLICATION
  CONNECTION LIMIT -1
  PASSWORD 'xxxxxx';


-- ***************************************
-- Creation du Tablespace: ts_projet07
-- **************************************
DROP TABLESPACE IF EXISTS ts_projet07;

CREATE TABLESPACE ts_projet07
  OWNER rl_projet07
  LOCATION 'c:/bd_data';

ALTER TABLESPACE ts_projet07
  OWNER TO rl_projet07;


-- ***************************************
-- Creation du Database: db_projet07
-- **************************************

DROP DATABASE IF EXISTS db_projet07;

CREATE DATABASE db_projet07
    WITH 
    OWNER = rl_projet07
    ENCODING = 'UTF8'
    LC_COLLATE = 'French_France.1252'
    LC_CTYPE = 'French_France.1252'
    TABLESPACE = ts_projet07
    CONNECTION LIMIT = -1;

GRANT TEMPORARY, CONNECT ON DATABASE db_projet07 TO PUBLIC;

GRANT ALL ON DATABASE db_projet07 TO rl_projet07;

ALTER DEFAULT PRIVILEGES
GRANT ALL ON TABLES TO rl_projet07;



