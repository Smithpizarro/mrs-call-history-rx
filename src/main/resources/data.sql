CREATE SEQUENCE IF NOT EXISTS public.tbl_call_history_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.tbl_call_history
(
    id bigint DEFAULT nextval('tbl_call_history_id_seq') PRIMARY KEY,
    datetime timestamp without time zone NOT NULL,
    endpoint character varying(255) COLLATE pg_catalog."default" NOT NULL,
    request character varying(255) COLLATE pg_catalog."default" NOT NULL,
    result character varying(255) COLLATE pg_catalog."default" NOT NULL,
    created_date timestamp without time zone NOT NULL,
    updated_date timestamp without time zone NOT NULL
);

ALTER SEQUENCE tbl_call_history_id_seq OWNED BY tbl_call_history.id;

