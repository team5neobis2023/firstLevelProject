create table region(
      id bigint primary key AUTO_INCREMENT,
      name varchar(255) unique
);

insert into region (name)
values ('Чуйская область'), ('Иссык-Кульская область'), ('Нарынская область'), ('Таласская область'),
       ('Джалал-Абадская область'), ('Ошская область'), ('Баткенская область');