create table video_course_category(
                                      id bigint primary key AUTO_INCREMENT,
                                      name varchar(255) unique
);

ALTER TABLE video_course ADD category_id bigint references video_course_category(id);
ALTER TABLE video_course DROP COLUMN user_id;
ALTER TABLE video_course ADD user_id bigint references users(id);