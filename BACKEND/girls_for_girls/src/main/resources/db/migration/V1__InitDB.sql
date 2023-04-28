
CREATE TABLE user_group (
    id int primary key AUTO_INCREMENT,
    name varchar(255)
);

CREATE TABLE file(
    id int primary key AUTO_INCREMENT,
    rec_time timestamp default now(),
    name varchar(255),
    file_code varchar(255),
    is_deleted boolean default false,
    article_id int,
    product_id int,
    user_id int not null
);

CREATE TABLE product_group(
    id int primary key AUTO_INCREMENT,
    title varchar(255) unique
);

CREATE TABLE users(
    id int primary key AUTO_INCREMENT,
    rec_time timestamp default now(),
    first_name varchar(255),
    last_name varchar(255),
    email varchar(255),
    password varchar(255),
    place_of_birth varchar(255),
    phone_number varchar(255),
    group_id int not null,
    file_id int,
    reset_token varchar(255),
    enabled bool default false,
    FOREIGN KEY(group_id) REFERENCES user_group(id),
    FOREIGN KEY(file_id) REFERENCES file(id)
);

CREATE TABLE article (
    id int primary key AUTO_INCREMENT,
    rec_time timestamp default now(),
    update_time timestamp,
    title varchar(2500),
    description text,
    views_count int,
    user_id int not null,
    FOREIGN KEY(user_id) REFERENCES users(id)
);

create table article_like(
    id serial primary key AUTO_INCREMENT,
    article_id int references article(id),
    user_id int references users(id)
);

CREATE TABLE product(
    id int primary key AUTO_INCREMENT,
    title varchar(255),
    description varchar(2500),
    price int,
    size varchar(255),
    product_group_id int,
    file_id int,
    FOREIGN KEY(product_group_id) REFERENCES product_group(id),
    FOREIGN KEY(file_id) REFERENCES file(id)
);

CREATE TABLE orders(
    id int primary key AUTO_INCREMENT,
    user_id int REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    product_id int REFERENCES product(id) ON UPDATE CASCADE,
    amount int NOT NULL,
    order_date timestamp default now()
);

CREATE TABLE refresh_token (
    id int primary key AUTO_INCREMENT,
    rec_time timestamp default now(),
    token text not null,
    user_id int not null
);

CREATE TABLE training (
    id int primary key AUTO_INCREMENT,
    rec_time timestamp default now(),
    description text not null,
    user_id int
);

CREATE TABLE conference (
    id int primary key AUTO_INCREMENT,
    rec_time timestamp default now(),
    conference_date timestamp,
    description varchar(255),
    user_id int
);

CREATE TABLE mentor_program (
    id int primary key AUTO_INCREMENT,
    rec_time timestamp default now(),
    description text not null,
    user_id int
);

CREATE TABLE application (
    id int primary key AUTO_INCREMENT,
    full_name varchar(255),
    rec_time timestamp default now(),
    date_of_birth timestamp,
    email varchar(255),
    address varchar(255),
    work_format varchar(255),
    motivation varchar(1000),
    about_me varchar(1000),
    achievements varchar(1000),
    my_fails varchar(1000),
    my_skills varchar(1000),
    training_id int,
    mentor_program_id int,
    conference_id int
);

CREATE TABLE notification (
    id int primary key AUTO_INCREMENT,
    rec_time timestamp default now(),
    message varchar(2500),
    user_id int
);

CREATE TABLE video_course (
    id int primary key AUTO_INCREMENT,
    rec_time timestamp default now(),
    description varchar(2500),
    rating int,
    user_id int
);

CREATE TABLE feedback (
    id int primary key AUTO_INCREMENT,
    rec_time timestamp default now(),
    full_name varchar(255),
    email varchar(255),
    phone_number varchar(255),
    message varchar(1000),
    video_course_id int
);

INSERT INTO user_group(id, name)
VALUES (1, 'ADMIN'),
       (2, 'MENTOR'),
       (3, 'USER');

INSERT INTO users(first_name, last_name, email, password, place_of_birth, phone_number, group_id, file_id, reset_token, enabled)
VALUES ('undefined', 'undefined', 'g4g@mail.ru', '$2a$10$SyIi.z/OMzZh2pNDGXAgyuaKuZ0r.8svAURkRyi30ZsJmOSroy6my', 'Bishkek', '0703361322', '1', null, null, true);