ALTER TABLE mentor_question
ADD COLUMN mentor_id BIGINT NOT NULL AFTER learning_path_id;

ALTER TABLE mentor_question
ADD CONSTRAINT mentor_question_mentor_id_fk
  FOREIGN KEY (mentor_id)
  REFERENCES user (id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;