create table rating(
    id bigint primary key AUTO_INCREMENT,
    video_course_id bigint references video_course(id),
    user_id bigint references users(id),
    rating int
);

