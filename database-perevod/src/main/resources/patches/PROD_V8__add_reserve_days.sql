
CREATE TABLE dbo.campaign_reserve_days
(
  id bigint IDENTITY NOT NULL,
  campaign_id bigint NOT NULL,
  reserve_day date NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_crd_campaigns FOREIGN KEY (campaign_id)
    REFERENCES dbo.reception_campaigns (id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);
