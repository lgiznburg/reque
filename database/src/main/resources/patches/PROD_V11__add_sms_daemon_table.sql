CREATE TABLE dbo.sms_queue
(
  id bigint IDENTITY NOT NULL,
  announce_text NVARCHAR(max) NOT NULL,
  phone_number nvarchar(255) NOT NULL,
  send_status SMALLINT DEFAULT 0,
  error_status SMALLINT DEFAULT 0,
  PRIMARY KEY (id)
);