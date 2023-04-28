ALTER TABLE notification ADD readed boolean DEFAULT FALSE;
ALTER TABLE notification ADD FOREIGN KEY (user_id) REFERENCES users (id);
