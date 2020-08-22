CREATE TABLE public.homepage_announce
(
  id bigserial NOT NULL,
  announce_text text NOT NULL,
  locale character varying(255) NOT NULL,
  PRIMARY KEY (id)
)
  WITH (
    OIDS = FALSE
  );