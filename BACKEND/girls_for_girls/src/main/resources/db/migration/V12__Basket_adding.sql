create table basket(
     id bigint primary key AUTO_INCREMENT,
     product_id int references product(id),
     size_id bigint references size(id),
     amount int,
     user_id int references users(id)
);
