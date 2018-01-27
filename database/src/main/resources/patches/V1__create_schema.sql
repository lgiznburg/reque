CREATE TABLE public.roles
(
  id bigserial NOT NULL,
  name character varying(32),
  PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
)
TABLESPACE pg_default;

CREATE TABLE public.users
(
  id bigserial NOT NULL,
  username character varying(255) NOT NULL,
  password character varying(255) NOT NULL,
  first_name character varying(255) NOT NULL,
  last_name character varying(255) NOT NULL,
  phone_number character varying(255) DEFAULT '',
  enabled boolean,
  created_time timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_updated timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
)
TABLESPACE pg_default;

CREATE TABLE public.user_roles
(
  user_id bigint NOT NULL,
  role_id bigint NOT NULL,
  CONSTRAINT fk_userroles_users FOREIGN KEY (user_id)
  REFERENCES public.users (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT fk_userroles_roles FOREIGN KEY (role_id)
  REFERENCES public.roles (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);

CREATE TABLE public.stored_properties
(
  id bigserial NOT NULL,
  property_name character varying(255) NOT NULL,
  value character varying(255),
  PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
);

CREATE TABLE public.queue_statuses
(
  id bigserial NOT NULL,
  name character varying(255) NOT NULL,
  description text DEFAULT '',
  type character varying(255) NOT NULL,
  PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
);

CREATE TABLE public.queue_entries
(
  id bigserial NOT NULL,
  uid character varying(255) NOT NULL,
  service_status_id bigint NOT NULL,
  service_key character varying(255) DEFAULT '',
  client_id bigint,
  coming_time timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT fk_queue_entry_statuses FOREIGN KEY (service_status_id)
  REFERENCES public.queue_statuses (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT fk_queue_entry_users FOREIGN KEY (client_id)
  REFERENCES public.users (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);

CREATE TABLE public.service_place_types
(
  id bigserial NOT NULL,
  role_id bigint NOT NULL,
  name character varying(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_place_type_roles FOREIGN KEY (role_id)
  REFERENCES public.roles (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
)
TABLESPACE pg_default;

CREATE TABLE public.service_places
(
  id bigserial NOT NULL,
  name character varying(255) NOT NULL,
  user_id bigint NOT NULL,
  place_type_id bigint NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_place_users FOREIGN KEY (user_id)
  REFERENCES public.users (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT fk_place_types FOREIGN KEY (place_type_id)
  REFERENCES public.service_place_types (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);

CREATE TABLE public.place_type_statuses
(
  place_type_id bigint NOT NULL,
  status_id bigint NOT NULL,
  CONSTRAINT fk_pts_statuses FOREIGN KEY (status_id)
  REFERENCES public.queue_statuses (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT fk_pts_types FOREIGN KEY (place_type_id)
  REFERENCES public.service_place_types (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
)
TABLESPACE pg_default;

CREATE TABLE public.appliance_types
(
  id bigserial NOT NULL,
  name character varying(255) NOT NULL,
  description text DEFAULT '',
  PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
);

CREATE TABLE public.reception_campaigns
(
  id bigserial NOT NULL,
  name character varying(255) NOT NULL,
  start_date date NOT NULL,
  end_date date NOT NULL,
  active BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
);

CREATE TABLE public.campaign_appliances
(
  campaign_id bigint NOT NULL,
  appliance_type_id bigint NOT NULL,
  CONSTRAINT fk_ca_campaigns FOREIGN KEY (campaign_id)
  REFERENCES public.reception_campaigns (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT fk_ca_appliances FOREIGN KEY (appliance_type_id)
  REFERENCES public.appliance_types (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);

CREATE TABLE public.appointments
(
  id bigserial NOT NULL,
  client_id bigint,
  type_id bigint,
  campaign_id BIGINT,
  online_number character varying(255) DEFAULT '',
  scheduled_date date NOT NULL,
  scheduled_time time without time zone NOT NULL,
  created_time timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT fk_appointments_appliance_types FOREIGN KEY (type_id)
  REFERENCES public.appliance_types (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT fk_appointments_campaignss FOREIGN KEY (campaign_id)
  REFERENCES public.reception_campaigns (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT fk_appointments_users FOREIGN KEY (client_id)
  REFERENCES public.users (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);
