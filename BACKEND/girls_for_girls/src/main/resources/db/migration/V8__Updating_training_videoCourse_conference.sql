ALTER TABLE video_course ADD speaker_id bigint references speaker(id);
ALTER TABLE training ADD speaker_id bigint references speaker(id);
ALTER TABLE conference ADD speaker_id bigint references speaker(id);