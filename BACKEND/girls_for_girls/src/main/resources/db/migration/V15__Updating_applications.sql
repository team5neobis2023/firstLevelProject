drop table application;

create table training_application(
    id bigint primary key AUTO_INCREMENT,
    rec_time timestamp,
    full_name varchar,
    date_of_birth timestamp,
    email varchar,
    phone_number varchar,
    address varchar,
    opportunity_to_come varchar,
    reason_to_participate varchar(1000),
    expectation varchar(1000),
    most_interest_training_topic varchar,
    other_interest_topics varchar,
    where_found_training varchar,
    training_id bigint references training(id),
    user_id bigint references users(id),
    approved boolean
);

create table mentor_program_application(
     id bigint primary key AUTO_INCREMENT,
     rec_time timestamp,
     full_name varchar,
     date_of_birth timestamp,
     email varchar,
     phone_number varchar,
     address varchar,
     goals varchar(500),
     help_of_program varchar(1000),
     experience_with_mentor varchar,
     ideal_mentor_desc varchar(500),
     resume_url varchar,
     where_found varchar,
     mentor_program_id bigint references mentor_program(id),
     user_id bigint references users(id),
     approved boolean
);

alter table mentor_program add column header varchar;
alter table mentor_program add column image_url varchar;
alter table training add column header varchar;
alter table training add column image_url varchar;
