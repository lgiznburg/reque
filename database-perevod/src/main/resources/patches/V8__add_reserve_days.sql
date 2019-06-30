CREATE TABLE public.campaign_reserve_days
(
  id bigserial NOT NULL,
  campaign_id bigint NOT NULL,
  reserve_day date NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_crd_campaigns FOREIGN KEY (campaign_id)
    REFERENCES public.reception_campaigns (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
)
  WITH (
    OIDS = FALSE
  );