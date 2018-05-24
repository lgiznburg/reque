CREATE TABLE dbo.roles
(
  id bigint IDENTITY NOT NULL,
  name nvarchar(32),
  PRIMARY KEY (id)
);

CREATE TABLE dbo.users
(
  id bigint IDENTITY NOT NULL,
  username nvarchar(255) NOT NULL,
  password nvarchar(255) NOT NULL,
  first_name nvarchar(255) NOT NULL,
  last_name nvarchar(255) NOT NULL,
  phone_number nvarchar(255) DEFAULT '',
  enabled smallint,
  created_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_updated datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE dbo.user_roles
(
  user_id bigint NOT NULL,
  role_id bigint NOT NULL,
  CONSTRAINT fk_userroles_users FOREIGN KEY (user_id)
  REFERENCES dbo.users (id) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT fk_userroles_roles FOREIGN KEY (role_id)
  REFERENCES dbo.roles (id) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
);

CREATE TABLE dbo.stored_properties
(
  id bigint IDENTITY NOT NULL,
  property_name nvarchar(255) NOT NULL,
  value nvarchar(255),
  PRIMARY KEY (id)
);

CREATE TABLE dbo.queue_statuses
(
  id bigint IDENTITY NOT NULL,
  name nvarchar(255) NOT NULL,
  description text DEFAULT '',
  type nvarchar(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE dbo.queue_entries
(
  id bigint IDENTITY NOT NULL,
  uid nvarchar(255) NOT NULL,
  service_status_id bigint NOT NULL,
  service_key nvarchar(255) DEFAULT '',
  client_id bigint,
  coming_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT fk_queue_entry_statuses FOREIGN KEY (service_status_id)
  REFERENCES dbo.queue_statuses (id) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT fk_queue_entry_users FOREIGN KEY (client_id)
  REFERENCES dbo.users (id) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
);

CREATE TABLE dbo.service_place_types
(
  id bigint IDENTITY NOT NULL,
  role_id bigint NOT NULL,
  name nvarchar(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_place_type_roles FOREIGN KEY (role_id)
  REFERENCES dbo.roles (id) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
);

CREATE TABLE dbo.service_places
(
  id bigint IDENTITY NOT NULL,
  name nvarchar(255) NOT NULL,
  user_id bigint NOT NULL,
  place_type_id bigint NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_place_users FOREIGN KEY (user_id)
  REFERENCES dbo.users (id) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT fk_place_types FOREIGN KEY (place_type_id)
  REFERENCES dbo.service_place_types (id) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
);

CREATE TABLE dbo.place_type_statuses
(
  place_type_id bigint NOT NULL,
  status_id bigint NOT NULL,
  CONSTRAINT fk_pts_statuses FOREIGN KEY (status_id)
  REFERENCES dbo.queue_statuses (id) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT fk_pts_types FOREIGN KEY (place_type_id)
  REFERENCES dbo.service_place_types (id) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
);

CREATE TABLE dbo.appliance_types
(
  id bigint IDENTITY NOT NULL,
  name nvarchar(255) NOT NULL,
  description text DEFAULT '',
  PRIMARY KEY (id)
);

CREATE TABLE dbo.reception_campaigns
(
  id bigint IDENTITY NOT NULL,
  name nvarchar(255) NOT NULL,
  start_date date NOT NULL,
  end_date date NOT NULL,
  active smallint DEFAULT 1,
  PRIMARY KEY (id)
);

CREATE TABLE dbo.campaign_appliances
(
  campaign_id bigint NOT NULL,
  appliance_type_id bigint NOT NULL,
  CONSTRAINT fk_ca_campaigns FOREIGN KEY (campaign_id)
  REFERENCES dbo.reception_campaigns (id) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT fk_ca_appliances FOREIGN KEY (appliance_type_id)
  REFERENCES dbo.appliance_types (id) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
);

CREATE TABLE dbo.appointments
(
  id bigint IDENTITY NOT NULL,
  client_id bigint,
  type_id bigint,
  campaign_id BIGINT,
  online_number nvarchar(255) DEFAULT '',
  scheduled_date date NOT NULL,
  scheduled_time time NOT NULL,
  created_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT fk_appointments_appliance_types FOREIGN KEY (type_id)
  REFERENCES dbo.appliance_types (id) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT fk_appointments_campaigns FOREIGN KEY (campaign_id)
  REFERENCES dbo.reception_campaigns (id) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT fk_appointments_users FOREIGN KEY (client_id)
  REFERENCES dbo.users (id) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
);

CREATE TABLE dbo.password_keys
(
  id bigint IDENTITY NOT NULL,
  user_id BIGINT,
  unique_key nvarchar(255),
  expired_time datetime,
  PRIMARY KEY (id),
  CONSTRAINT fk_password_key_users FOREIGN KEY (user_id)
    REFERENCES dbo.users (id) 
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);