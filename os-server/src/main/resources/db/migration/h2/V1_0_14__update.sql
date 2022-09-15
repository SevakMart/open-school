-- -----------------------------------------------------
-- Table quiz_status
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS quiz_status (
    id BIGINT NOT NULL,
    status_type VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table question_type
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS question_type (
    id BIGINT NOT NULL,
    type VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table quiz
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS quiz (
    id BIGINT NOT NULL AUTO_INCREMENT,
    max_grade INT NOT NULL,
    student_grade INT NOT NULL,
    passing_score INT NOT NULL,
    module_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_quiz_module
      FOREIGN KEY (module_id)
      REFERENCES module (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

    CREATE INDEX fk_quiz_module_idx ON quiz (module_id ASC);

-- -----------------------------------------------------
-- Table enrolled_quiz
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS enrolled_quiz (
    id BIGINT NOT NULL AUTO_INCREMENT,
    quiz_status_id BIGINT NOT NULL,
    quiz_id BIGINT NOT NULL,
    enrolled_module_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_enrolled_quiz_quiz_status
      FOREIGN KEY (quiz_status_id)
      REFERENCES quiz_status (id),
    CONSTRAINT fk_enrolled_quiz_quiz
      FOREIGN KEY (quiz_id)
      REFERENCES quiz (id),
    CONSTRAINT fk_enrolled_quiz_enrolled_module
      FOREIGN KEY (enrolled_module_id)
      REFERENCES enrolled_module (id)
);

    CREATE INDEX fk_enrolled_quiz_quiz_status_idx ON enrolled_quiz (quiz_status_id ASC);
    CREATE INDEX fk_enrolled_quiz_quiz_idx ON enrolled_quiz (quiz_id ASC);
    CREATE INDEX fk_enrolled_quiz_enrolled_module_idx ON enrolled_quiz (enrolled_module_id ASC);

-- -----------------------------------------------------
-- Table questions
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS questions (
    id BIGINT NOT NULL AUTO_INCREMENT,
    question TEXT NOT NULL,
    quiz_id BIGINT NOT NULL,
    question_type_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_questions_quiz
      FOREIGN KEY (quiz_id)
      REFERENCES quiz (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
    CONSTRAINT fk_questions_question_type
      FOREIGN KEY (question_type_id)
      REFERENCES question_type (id)
);

   CREATE INDEX fk_questions_quiz_idx ON questions (quiz_id ASC);
   CREATE INDEX fk_questions_question_type_idx ON questions (question_type_id ASC);

-- -----------------------------------------------------
-- Table answer_options
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS answer_options (
   id BIGINT NOT NULL AUTO_INCREMENT,
   answer_option TEXT NOT NULL,
   is_right_answer BOOLEAN NOT NULL,
   question_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_answer_options_question
    FOREIGN KEY (question_id)
    REFERENCES questions (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

    CREATE INDEX fk_answer_options_question_idx ON answer_options(question_id ASC);

INSERT INTO quiz_status(id, status_type) VALUES (1,'IN_PROGRESS');
INSERT INTO quiz_status(id, status_type) VALUES (2,'COMPLETED');
INSERT INTO quiz_status(id, status_type) VALUES (3,'FAILED');

INSERT INTO question_type(id, type) VALUES (1,'MATCHING');
INSERT INTO question_type(id, type) VALUES (2,'MULTIPLE_CHOICE');
