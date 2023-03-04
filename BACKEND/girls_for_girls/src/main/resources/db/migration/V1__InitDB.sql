
CREATE TABLE user_group(
    id serial primary key,
    name varchar
);

CREATE TABLE file(
    id serial primary key,
    rec_time timestamp default now(),
    name varchar,
    file_code varchar,
    is_deleted boolean default false,
    incident_id int,
    product_id int,
    user_id int not null
);

CREATE TABLE product_group(
    id serial primary key,
    title varchar unique
);

CREATE TABLE users(
    id serial primary key,
    rec_time timestamp default now(),
    first_name varchar,
    last_name varchar,
    email varchar,
    password varchar,
    place_of_birth varchar,
    phone_number varchar,
    group_id int not null,
    file_id int,
    reset_token varchar,
    enabled bool default false,
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
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(like_id) REFERENCES users(id)
);

CREATE TABLE product(
    id serial primary key,
    title varchar,
    description varchar,
    price int,
    size varchar,
    product_group_id int,
    FOREIGN KEY(product_group_id) REFERENCES product_group(id)
);

CREATE TABLE orders(
    id serial primary key,
    user_id int REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    product_id int REFERENCES product(id) ON UPDATE CASCADE,
    amount int NOT NULL,
    order_date timestamp default now()
);

CREATE TABLE refresh_token (
    id serial not null,
    rec_time timestamp default now (),
    token text not null,
    user_id int not null
);

CREATE TABLE training (
    id serial not null,
    rec_time timestamp default now (),
    description text not null,
    user_id int
);

CREATE TABLE conferences (
    id serial not null,
    rec_time timestamp default now (),
    conference_date timestamp,
    description varchar,
    user_id int
);

CREATE TABLE mentor_program (
    id serial not null,
    rec_time timestamp default now (),
    description text not null,
    user_id int
);

CREATE TABLE application (
    id serial not null,
    rec_time timestamp default now (),
    date_of_birth timestamp,
    email varchar,
    address varchar,
    work_format varchar,
    motivation varchar(1000),
    about_me varchar(1000),
    achievements varchar(1000),
    my_fails varchar(1000),
    my_skills varchar(1000),
    training_id int,
    mentor_program_id int,
    conferences_id int
);

CREATE TABLE notification (
    id serial not null,
    rec_time timestamp default now (),
    message varchar,
    user_id int
);

CREATE TABLE video_course (
    id serial not null,
    rec_time timestamp default now (),
    description varchar,
    rating int,
    user_id int
);

CREATE TABLE feedback (
    id serial not null,
    rec_time timestamp default now (),
    full_name varchar,
    email varchar,
    phone_number varchar,
    message varchar(1000),
    video_course_id int
);

INSERT INTO user_group(id, name)
VALUES (1, 'ADMIN'),
       (2, 'MENTOR'),
       (3, 'USER');

INSERT INTO users(first_name, last_name, email, password, place_of_birth, phone_number, group_id, file_id, reset_token, enabled)
VALUES ('undefined', 'undefined', 'g4g@mail.ru', '$2a$10$SyIi.z/OMzZh2pNDGXAgyuaKuZ0r.8svAURkRyi30ZsJmOSroy6my', 'Bishkek', '0703361322', '1', null, null, true);