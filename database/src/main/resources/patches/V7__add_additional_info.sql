CREATE TABLE public.additional_users_info
(
  id bigserial NOT NULL,
  user_id bigint NOT NULL,
  middle_name character varying(255) NOT NULL,
  citizenship character varying(255) NOT NULL,
  birth_date date NOT NULL,
  document_number character varying(255) NOT NULL,
  session_end_date date NOT NULL,
  representative_name character varying(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_add_info_users FOREIGN KEY (user_id)
  REFERENCES public.users (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);