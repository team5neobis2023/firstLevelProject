ALTER TABLE feedback DROP COLUMN full_name;
ALTER TABLE feedback DROP COLUMN email;
ALTER TABLE feedback DROP COLUMN phone_number;
ALTER TABLE feedback DROP COLUMN video_course_id;
ALTER TABLE feedback ADD rating int;
ALTER TABLE feedback ADD video_course_id bigint references video_course(id);
ALTER TABLE feedback ADD user_id bigint references users(id);

CREATE TABLE speaker(
    id bigint primary key AUTO_INCREMENT,
    full_name varchar(255),
    full_info varchar(500)
);

ALTER TABLE file ADD speaker_id bigint references speaker(id);

ALTER TABLE product DROP COLUMN product_group_id;
DROP TABLE product_group;