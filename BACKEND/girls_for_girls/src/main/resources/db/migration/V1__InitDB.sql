
CREATE TABLE user_group(
    id serial primary key,
    name varchar
);

CREATE TABLE file(
    id serial primary key,
    rec_time timestamp default now(),
    name varchar,
    file_code varchar,
    is_deleted boolean default false
);

CREATE TABLE product_group(
    id serial primary key,
    name varchar
);

CREATE TABLE users(
    id serial primary key,
    rec_time timestamp default now(),
    first_name varchar,
    last_name varchar,
    email varchar,
    password varchar,
    date_of_birth timestamp,
    phone_number varchar,
    group_id int not null,
    file_id int,
    FOREIGN KEY(group_id) REFERENCES user_group(id),
    FOREIGN KEY(file_id) REFERENCES file(id)
);

CREATE TABLE article (
    id serial primary key,
    rec_time timestamp default now(),
    update_time timestamp,
    title varchar,
    description text,
    views_count int,
    like_id int,
    user_id int not null,
    file_id int,
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(like_id) REFERENCES users(id),
    FOREIGN KEY(file_id) REFERENCES file(id)
);

CREATE TABLE product(
    id serial primary key,
    name varchar,
    description varchar,
    price int,
    size varchar,
    product_group_id int,
    file_id int,
    FOREIGN KEY(product_group_id) REFERENCES product_group(id),
    FOREIGN KEY(file_id) REFERENCES file(id)
);

CREATE TABLE orders(
    id serial primary key,
    user_id int REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    product_id int REFERENCES product(id) ON UPDATE CASCADE,
    amount int NOT NULL,
    order_date timestamp default now()
);

CREATE TABLE refresh_tokens (
    id serial not null,
    rec_time timestamp default now (),
    token text not null,
    user_id int not null
);

INSERT INTO user_group(id, name)
VALUES (1, 'ADMIN'),
       (2, 'MENTOR'),
       (3, 'USER');

INSERT INTO users(id, first_name, last_name, email, password, date_of_birth, phone_number, group_id, file_id)
VALUES (1, 'undefined', 'undefined', 'g4g@mail.ru', '$2a$10$SyIi.z/OMzZh2pNDGXAgyuaKuZ0r.8svAURkRyi30ZsJmOSroy6my', '1998-05-05 15:56:46.196000', '0703361322', '1', null);