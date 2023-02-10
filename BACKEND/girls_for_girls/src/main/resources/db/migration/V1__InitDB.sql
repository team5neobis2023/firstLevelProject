
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
    user_id int REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    product_id int REFERENCES product(id) ON UPDATE CASCADE,
    amount int NOT NULL,
    order_date timestamp default now(),
    PRIMARY KEY (user_id, product_id)
);

CREATE TABLE refresh_tokens (
    id serial not null,
    rec_time timestamp default now (),
    token text not null,
    user_id int not null
);