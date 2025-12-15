START TRANSACTION;

-- SET SESSION sql_mode = 'NO_ENGINE_SUBSTITUTION';
SET GLOBAL time_zone = '+00:00';
CREATE TABLE notes
  (
     id         BINARY(16) NOT NULL,
     content    VARCHAR(255),
     username   VARCHAR(255) NOT NULL,
     created_at DATETIME(6) DEFAULT NOW(6),
     updated_at DATETIME(6) DEFAULT NOW(6) ON UPDATE NOW(6),
     PRIMARY KEY (id)
  );

CREATE TABLE role
  (
     id   BINARY(16) NOT NULL,
     NAME VARCHAR(255) NOT NULL,
     PRIMARY KEY (id)
  );

CREATE TABLE user_roles
  (
     role_id BINARY(16) NOT NULL,
     user_id BINARY(16) NOT NULL,
     PRIMARY KEY (role_id, user_id)
  );

CREATE TABLE users
  (
     id         BINARY(16) NOT NULL,
     email      VARCHAR(255) NOT NULL,
     password   VARCHAR(255) NOT NULL,
     username   VARCHAR(255) NOT NULL,
     created_at DATETIME(6) DEFAULT NOW(6),
     updated_at DATETIME(6) DEFAULT NOW(6) ON UPDATE NOW(6),
     PRIMARY KEY (id)
  );

ALTER TABLE role
  ADD CONSTRAINT uk8sewwnpamngi6b1dwaa88askk UNIQUE (NAME);

ALTER TABLE users
  ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);

ALTER TABLE user_roles
  ADD CONSTRAINT fkrhfovtciq1l558cw6udg0h0d3 FOREIGN KEY (role_id) REFERENCES
  role (id);

ALTER TABLE user_roles
  ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES
  users (id);

COMMIT;