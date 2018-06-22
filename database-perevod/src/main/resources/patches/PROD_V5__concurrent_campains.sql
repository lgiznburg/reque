UPDATE dbo.roles SET name = 'ROLE_SERVICEMAN' where name = 'ROLE_SERVICER';

ALTER TABLE dbo.reception_campaigns
  ADD priority int DEFAULT 0,
      concurrent_amount int DEFAULT 0;

