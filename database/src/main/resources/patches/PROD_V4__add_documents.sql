CREATE TABLE dbo.document_names
(
  id bigint IDENTITY NOT NULL,
  name nvarchar(255) NOT NULL,
  numb_order INT,
  PRIMARY KEY (id)
);

CREATE TABLE dbo.appliance_documents
(
  appliance_type_id bigint NOT NULL,
  document_id bigint NOT NULL,
  CONSTRAINT fk_ad_campaigns FOREIGN KEY (document_id)
  REFERENCES dbo.document_names (id)
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT fk_ad_appliances FOREIGN KEY (appliance_type_id)
  REFERENCES dbo.appliance_types (id)
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
);
