INSERT INTO roles (name) VALUES ('ROLE_ANONYMOUS'),
  ('ROLE_CLIENT'),
  ('ROLE_USER'),
  ('ROLE_SERVICER'),
  ('ROLE_MANAGER'),
  ('ROLE_ADMIN');

INSERT INTO users (username,password,first_name,last_name,phone_number,enabled)
    VALUES ('pk_admin@rsmu.ru', md5('rsmu123'), 'Admin', 'Admin', '',true);

INSERT INTO user_roles (user_id,role_id) VALUES (1,6);

INSERT INTO appliance_types (name,description)
    VALUES ('поступление','простое заявление абитуриента'),
      ('целевое','заявление на целевой прием'),
      ('особые права','заявление с особыми правами'),
      ('без ВИ','поступление без вступительных испытаний');