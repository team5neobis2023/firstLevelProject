DROP SCHEMA IF EXISTS g4g_db;
CREATE SCHEMA g4g_db;

CREATE TABLE g4g_db.user_group(
    id serial not null,
    name varchar
);

CREATE TABLE g4g_db.user(
    ID serial not null,
    rec_time timestamp default now(),
    email varchar,
    password varchar,
    date_of_birth timestamp,
    phone_number varchar,
    group_id int not null,
    file_id int,
    CONSTRAINT fk_group FOREIGN KEY(group_id) REFERENCES group(id),
    CONSTRAINT fk_file FOREIGN KEY(file_id) REFERENCES file(id)
);

CREATE TABLE g4g_db.article (
    Id serial not null,
    rec_time timestamp default now(),
    update_time timestamp,
    title varchar,
    description text,
    views_count int,
    like_count int,
    user_id int not null,
    file_id int,
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES user(id),
    CONSTRAINT fk_user_like FOREIGN KEY(like_id) REFERENCES user(id),
    CONSTRAINT fk_file FOREIGN KEY(file_id) REFERENCES file(id)
);

CREATE TABLE g4g_db.product(
    id serial not null,
    name varchar,
    description varchar,
    price int,
    size varchar,
    product_group_id int,
    file_id int,
    CONSTRAINT fk_product_group FOREIGN KEY(product_group_id) REFERENCES product_group(id),
    CONSTRAINT fk_file FOREIGN KEY(file_id) REFERENCES file(id)
);

CREATE TABLE g4g_db.product_group(
    id serial not null,
    name varchar
);

CREATE TABLE g4g_db.order(
    user_id int REFERENCES user (id) ON UPDATE CASCADE ON DELETE CASCADE,
    product_id int REFERENCES product(id) ON UPDATE CASCADE,
    amount int NOT NULL,
    order_date timestamp default now(),
    CONSTRAINT user_product_pk PRIMARY KEY (user_id, product_id)
);

CREATE TABLE g4g_db.file(
    id serial not null,
    rec_time timestamp default now(),
    name varchar,
    file_code varchar,
    is_deleted boolean default false
);