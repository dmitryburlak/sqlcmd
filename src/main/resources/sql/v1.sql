-- Database: sqlcmd_log

-- DROP DATABASE sqlcmd_log;

CREATE DATABASE sqlcmd_log
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Ukraine.1251'
    LC_CTYPE = 'Russian_Ukraine.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- SCHEMA: public

-- DROP SCHEMA public ;

CREATE SCHEMA public
    AUTHORIZATION postgres;

COMMENT ON SCHEMA public
    IS 'standard public schema';

GRANT ALL ON SCHEMA public TO postgres;

GRANT ALL ON SCHEMA public TO PUBLIC;

-- SEQUENCE: public.user_actions_seq

-- DROP SEQUENCE public.user_actions_seq;

CREATE SEQUENCE public.user_actions_seq;

ALTER SEQUENCE public.user_actions_seq
    OWNER TO postgres;

-- Table: public.user_actions

-- DROP TABLE public.user_actions;

CREATE TABLE public.user_actions
(
    id integer NOT NULL DEFAULT nextval('user_actions_seq'::regclass),
    user_name text COLLATE pg_catalog."default",
    db_name text COLLATE pg_catalog."default",
    action text COLLATE pg_catalog."default",
    CONSTRAINT id_pk PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.user_actions
    OWNER to postgres;

-- Constraint: id_pk

-- ALTER TABLE public.user_actions DROP CONSTRAINT id_pk;

ALTER TABLE public.user_actions
    ADD CONSTRAINT id_pk PRIMARY KEY (id);



