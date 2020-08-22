CREATE TABLE public.sms_queue
(
  id bigserial NOT NULL,
  announce_text text NOT NULL,
  phone_number character varying(255) NOT NULL,
  send_status BOOLEAN DEFAULT FALSE,
  error_status BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (id)
)
  WITH (
    OIDS = FALSE
  );