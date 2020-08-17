insert into users (username, password, enabled)
    values ('user',
        '{noop}pass',
        true);

insert into users (username, password, enabled)
    values ('admin',
        '{noop}pass',
        true);


insert into authorities (username, authority)
    values ('user', 'ROLE_USER');

insert into authorities (username, authority)
    values ('admin', 'ROLE_ADMIN');