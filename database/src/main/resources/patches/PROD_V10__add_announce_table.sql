CREATE TABLE dbo.homepage_announce
(
  id bigint IDENTITY NOT NULL,
  announce_text NVARCHAR(max) NOT NULL,
  locale nvarchar(255) NOT NULL,
  PRIMARY KEY (id)
);
