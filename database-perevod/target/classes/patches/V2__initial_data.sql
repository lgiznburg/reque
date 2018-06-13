INSERT INTO roles (name) VALUES ('ROLE_ANONYMOUS'),
  ('ROLE_CLIENT'),
  ('ROLE_USER'),
  ('ROLE_SERVICER'),
  ('ROLE_MANAGER'),
  ('ROLE_ADMIN');

INSERT INTO users (username,password,first_name,last_name,phone_number,enabled)
    VALUES ('perevod_admin@rsmu.ru', md5('rsmu123'), 'Admin', 'Admin', '',true);

INSERT INTO user_roles (user_id,role_id) VALUES (1,6);

INSERT INTO appliance_types (name,description)
    VALUES ('перевод','заявление на перевод');

INSERT INTO stored_properties (property_name,value)
VALUES ('SCHEDULE_SERVICE_INTERVAL','60'),
  ('SCHEDULE_SERVICE_AMOUNT','2'),
  ('EMAIL_FROM_ADDRESS','perevod_rsmu@rsmu.ru'),
  ('EMAIL_FROM_SIGNATURE','Аттестационная комиссия РНИМУ им.Пирогова');