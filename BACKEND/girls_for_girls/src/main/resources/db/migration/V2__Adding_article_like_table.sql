create table article_like(
    id serial primary key,
    article_id int references article(id),
    user_id int references users(id)
);

alter table article drop like_id;