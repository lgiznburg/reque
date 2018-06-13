CREATE TABLE public.document_names
(
  id bigserial NOT NULL,
  name character varying(255) NOT NULL,
  numb_order INT,
  PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
);

CREATE TABLE public.appliance_documents
(
  appliance_type_id bigint NOT NULL,
  document_id bigint NOT NULL,
  CONSTRAINT fk_ad_documents FOREIGN KEY (document_id)
  REFERENCES public.document_names (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT fk_ad_appliances FOREIGN KEY (appliance_type_id)
  REFERENCES public.appliance_types (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);
