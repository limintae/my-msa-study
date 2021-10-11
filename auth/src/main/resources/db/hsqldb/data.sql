INSERT INTO authority (name)
VALUES ('ACCOUNT_READ');

INSERT INTO authority (name)
VALUES ('ACCOUNT_WRITE');

INSERT INTO role (name)
VALUES ('ROLE_ADMIN');

INSERT INTO role (name)
VALUES ('ROLE_USER');

INSERT INTO role_authority (role_id, authority_id)
VALUES (
           (
               SELECT r.id
               FROM `role` r
               WHERE r.name = 'ROLE_ADMIN'
           ), (
               SELECT a.id
               FROM authority a
               WHERE a.name = 'ACCOUNT_READ'
           )
       );

INSERT INTO role_authority (role_id, authority_id)
VALUES (
           (
               SELECT r.id
               FROM `role` r
               WHERE r.name = 'ROLE_ADMIN'
           ), (
               SELECT a.id
               FROM authority a
               WHERE a.name = 'ACCOUNT_WRITE'
           )
       );

INSERT INTO role_authority (role_id, authority_id)
VALUES (
           (
               SELECT r.id
               FROM `role` r
               WHERE r.name = 'ROLE_USER'
           ), (
               SELECT a.id
               FROM authority a
               WHERE a.name = 'ACCOUNT_READ'
           )
       );

INSERT INTO account (email, password, name)
VALUES (
    'user01@gmail.com'
    , '$2a$10$kVJz59ozZZm3fIJAv8Rky.9n370d/wqoz3ZyXQdTlnwkHhPbaTHOG'
    , '임인태'
);

INSERT INTO account_role (account_id, role_id)
VALUES (
           (
               SELECT a.id
               FROM account a
               WHERE a.email = 'user01@gmail.com'
           ), (
                SELECT r.id
                FROM `role` r
                WHERE r.name = 'ROLE_ADMIN'
           )
       );