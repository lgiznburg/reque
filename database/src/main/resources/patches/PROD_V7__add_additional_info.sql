CREATE TABLE dbo.additional_users_info
(
  id bigint IDENTITY NOT NULL,
  user_id bigint NOT NULL,
  middle_name nvarchar(255) NOT NULL,
  citizenship nvarchar(255) NOT NULL,
  birth_date date NOT NULL,
  document_number nvarchar(255) NOT NULL,
  session_end_date date NOT NULL,
  representative_name nvarchar(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_add_info_users FOREIGN KEY (user_id)
  REFERENCES dbo.users (id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,

);
