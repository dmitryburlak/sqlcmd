-- SEQUENCE: public.database_connection_seq

-- DROP SEQUENCE public.database_connection_seq;

CREATE SEQUENCE public.database_connection_seq;

ALTER SEQUENCE public.database_connection_seq
    OWNER TO postgres;

-- Table: public.database_connection

-- DROP TABLE public.database_connection;

CREATE TABLE public.database_connection
(
    id integer NOT NULL DEFAULT nextval('database_connection_seq'::regclass),
    db_name text COLLATE pg_catalog."default",
    user_name text COLLATE pg_catalog."default",
    CONSTRAINT database_connection_id_pk PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.database_connection
    OWNER to postgres;

-- Constraint: database_connection_id_pk

-- ALTER TABLE public.database_connection DROP CONSTRAINT database_connection_id_pk;

ALTER TABLE public.database_connection
    ADD CONSTRAINT database_connection_id_pk PRIMARY KEY (id);

-- Column: public.user_actions.database_conection_id

-- ALTER TABLE public.user_actions DROP COLUMN database_conection_id;

ALTER TABLE public.user_actions
    ADD COLUMN database_conection_id integer;

-- Constraint: user_action_database_connection_id_fk

-- ALTER TABLE public.user_actions DROP CONSTRAINT user_action_database_connection_id_fk;

ALTER TABLE public.user_actions
    ADD CONSTRAINT user_action_database_connection_id_fk FOREIGN KEY (database_conection_id)
    REFERENCES public.database_connection (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

-- Data migration

INSERT INTO database_connection(id, user_name, db_name)
SELECT nextval('database_connection_seq'::regclass) AS id, subquery.user_name, subquery.db_name
FROM(SELECT DISTINCT user_name, db_name from user_actions) subquery;

-- Update user_actions

UPDATE user_actions
SET database_connection_id = subquery.id
FROM (SELECT id, user_name, db_name FROM database_connection) subquery
WHERE user_actions.user_name=subquery.user_name AND user_actions.db_name=subquery.db_name;

-- drop unuse
ALTER TABLE user_actions DROP COLUMN user_name;
ALTER TABLE user_actions DROP COLUMN db_name;
