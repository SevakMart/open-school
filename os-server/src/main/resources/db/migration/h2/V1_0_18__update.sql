CREATE TABLE IF NOT EXISTS peers_question(
id BIGINT NOT NULL AUTO_INCREMENT,
text TEXT NOT NULL,
learning_path_id BIGINT,
user_id BIGINT NOT NULL,
create_date TIMESTAMP NOT NULL,
PRIMARY KEY(id),
CONSTRAINT discussion_question_user_id_fk
FOREIGN KEY(user_id) REFERENCES user(id)
ON DELETE CASCADE
ON UPDATE CASCADE,
CONSTRAINT discussion_question_learning_path_id_fk
FOREIGN KEY(learning_path_id) REFERENCES learning_path(id)
ON DELETE CASCADE
ON UPDATE CASCADE
);



CREATE TABLE IF NOT EXISTS peers_answer(
id BIGINT NOT NULL AUTO_INCREMENT,
text TEXT NOT NULL,
user_id BIGINT,
peers_question_id BIGINT,
create_date TIMESTAMP NOT NULL,
PRIMARY KEY(id),
CONSTRAINT discussion_answer_user_id_fk
FOREIGN KEY(user_id) REFERENCES user(id)
ON DELETE CASCADE
ON UPDATE CASCADE,
CONSTRAINT peers_answer_discussion_question_id_fk
FOREIGN KEY(peers_question_id) REFERENCES peers_question(id)
ON DELETE CASCADE
ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS mentor_question(
id BIGINT NOT NULL AUTO_INCREMENT,
text TEXT NOT NULL,
learning_path_id BIGINT,
user_id BIGINT NOT NULL,
create_date TIMESTAMP NOT NULL,
PRIMARY KEY(id),
CONSTRAINT discussion_question_ask_mentor_user_id_fk
FOREIGN KEY(user_id) REFERENCES user(id)
ON DELETE CASCADE
ON UPDATE CASCADE,
CONSTRAINT discussion_question_ask_mentor_learning_path_id_fk
FOREIGN KEY(learning_path_id) REFERENCES learning_path(id)
ON DELETE CASCADE
ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS mentor_answer(
id BIGINT NOT NULL AUTO_INCREMENT,
text TEXT NOT NULL,
user_id BIGINT,
mentor_question_id BIGINT,
create_date TIMESTAMP NOT NULL,
PRIMARY KEY(id),
CONSTRAINT mentor_question_id_user_id_fk
FOREIGN KEY(user_id) REFERENCES user(id)
ON DELETE CASCADE
ON UPDATE CASCADE,
CONSTRAINT discussion_answer_mentor_discussion_question_id_fk
FOREIGN KEY(mentor_question_id) REFERENCES mentor_question(id)
ON DELETE CASCADE
ON UPDATE CASCADE
);




