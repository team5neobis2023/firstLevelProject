create table size(
       id bigint primary key AUTO_INCREMENT,
       name varchar(10) not null
);

insert into size(name) values ('S'),('M'),('L'),('XL');
ALTER TABLE product DROP COLUMN size;

create table product_size(
    id bigint primary key AUTO_INCREMENT,
    product_id bigint references product(id),
    size_id bigint references  size(id)
);

ALTER TABLE orders ADD size_id bigint references size(id);
ALTER TABLE article ADD image_url varchar;
ALTER TABLE speaker ADD image_url varchar;
