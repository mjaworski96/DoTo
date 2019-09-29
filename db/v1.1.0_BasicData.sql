INSERT INTO roles("name") VALUES
    ('USER'), 
    ('ADMIN');
INSERT INTO states("name") VALUES
    ('todo'),
    ('inprogress'),
    ('done'),
    ('archived');
INSERT INTO public.users (username, password, email) VALUES
    ('user', '$2a$10$.BR9gtXEQFZsVxQCFXgavOvTfkyTjDYqBtyzNKIpwvxdDI2tuEooa', 'user@example.com'),
    ('admin','$2a$10$w6j7reyRSZzBps0s23CZgeYYg2QlSjNUDxzwPxBiSqcWPNWWQMDge', 'admin@example.com');
INSERT INTO user_roles(user_id, role_id) VALUES
    (1, 1),
    (2, 1),
    (2, 2)
