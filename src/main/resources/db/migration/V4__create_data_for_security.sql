INSERT INTO users (username,password) VALUES
('john','pass'),
('mary','pass'),
('susan','pass');


INSERT INTO roles (name) VALUES
('ROLE_USER'),('ROLE_ADMIN');


INSERT INTO users_roles (user_id,role_id)
VALUES
(1, 1),
(2, 1),
(3, 2);