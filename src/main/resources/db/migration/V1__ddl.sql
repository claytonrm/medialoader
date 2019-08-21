CREATE TABLE users
(
    id bigint NOT NULL,
    facebook_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    gender character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    profile_picture character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT user_pk PRIMARY KEY (id)
)

CREATE TABLE photos
(
    id bigint NOT NULL,
    facebook_id character varying(255) COLLATE pg_catalog."default",
    facebook_url character varying(255) COLLATE pg_catalog."default",
    source_url character varying(255) COLLATE pg_catalog."default",
    user_id bigint,
    CONSTRAINT photo_pk PRIMARY KEY (id),
    CONSTRAINT photo_user_fk FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

CREATE TABLE reactions
(
    id bigint NOT NULL,
    type character varying(255) COLLATE pg_catalog."default",
    value bigint,
    photo_id bigint,
    CONSTRAINT reactions_pk PRIMARY KEY (id),
    CONSTRAINT reaction_photo_fk FOREIGN KEY (photo_id)
        REFERENCES photos (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)