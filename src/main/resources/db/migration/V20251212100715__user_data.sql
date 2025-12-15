START TRANSACTION;

INSERT INTO role (id, name)
VALUES
    (UUID_TO_BIN('019b1073-3e74-73b1-a150-476450a76e36'), 'ADMIN'),
    (UUID_TO_BIN('019b1073-3e74-7f35-8067-6d8cd854d0a5'), 'USER')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO users (id, email, username, password)
VALUES (
    UUID_TO_BIN('019b1073-3e74-715b-b4a6-f0d32bd58dd4'),
    'admin@admin.com',
    'admin',
    '$2a$12$wszh.N1HzQaIBouSs9eTrukYZfQOWX6Ef2S9ceYme9buKGqmz48Wq'
)
ON DUPLICATE KEY UPDATE email=email;

INSERT INTO users (id, email, username, password)
VALUES (
    UUID_TO_BIN('019b10b7-17b0-77fa-b9b7-1f2bdd7243f1'),
    'admin@user.com',
    'user',
    '$2a$12$wszh.N1HzQaIBouSs9eTrukYZfQOWX6Ef2S9ceYme9buKGqmz48Wq'
)
ON DUPLICATE KEY UPDATE email=email;

INSERT INTO user_roles (user_id, role_id)
VALUES (UUID_TO_BIN('019b1073-3e74-715b-b4a6-f0d32bd58dd4'), UUID_TO_BIN('019b1073-3e74-73b1-a150-476450a76e36'))
ON DUPLICATE KEY UPDATE user_id = user_id;

INSERT INTO user_roles (user_id, role_id)
VALUES (UUID_TO_BIN('019b10b7-17b0-77fa-b9b7-1f2bdd7243f1'), UUID_TO_BIN('019b1073-3e74-7f35-8067-6d8cd854d0a5'))
ON DUPLICATE KEY UPDATE user_id = user_id;

COMMIT;