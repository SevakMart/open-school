-- -----------------------------------------------------
-- Table `open_school_db`.`quiz_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`quiz_status` (
  `id` BIGINT NOT NULL,
  `status_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `open_school_db`.`question_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`question_type` (
  `id` BIGINT NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `open_school_db`.`quiz`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`quiz` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `max_grade` INT NOT NULL,
  `passing_score` INT NOT NULL,
  `quiz_status_id` BIGINT NOT NULL,
  `module_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_quiz_quiz_status_idx` (`quiz_status_id` ASC) VISIBLE,
  INDEX `fk_quiz_module_idx` (`module_id` ASC) VISIBLE,
  CONSTRAINT `fk_quiz_quiz_status`
    FOREIGN KEY (`quiz_status_id`)
    REFERENCES `open_school_db`.`quiz_status` (`id`),
  CONSTRAINT `fk_quiz_module`
    FOREIGN KEY (`module_id`)
    REFERENCES `open_school_db`.`module` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
  )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `open_school_db`.`enrolled_quiz`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`enrolled_quiz` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `student_grade` INT NOT NULL,
  `quiz_status_id` BIGINT NOT NULL,
  `quiz_id` BIGINT NOT NULL,
  `enrolled_module_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_enrolled_quiz_quiz_status_idx` (`quiz_status_id` ASC) VISIBLE,
  INDEX `fk_enrolled_quiz_quiz_idx` (`quiz_id` ASC) VISIBLE,
  INDEX `fk_enrolled_quiz_enrolled_module_idx` (`enrolled_module_id` ASC) VISIBLE,
  CONSTRAINT `fk_enrolled_quiz_quiz_status`
    FOREIGN KEY (`quiz_status_id`)
    REFERENCES `open_school_db`.`quiz_status` (`id`),
  CONSTRAINT `fk_enrolled_quiz_quiz`
    FOREIGN KEY (`quiz_id`)
    REFERENCES `open_school_db`.`quiz` (`id`),
  CONSTRAINT `fk_enrolled_quiz_enrolled_module`
    FOREIGN KEY (`enrolled_module_id`)
    REFERENCES `open_school_db`.`enrolled_module` (`id`)
  )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `open_school_db`.`questions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`questions` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `question` TEXT NOT NULL,
  `right_answer_count` INT NOT NULL,
  `quiz_id` BIGINT NOT NULL,
  `question_type_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_questions_quiz_idx` (`quiz_id` ASC) VISIBLE,
  INDEX `fk_questions_question_type_idx` (`question_type_id` ASC) VISIBLE,
  CONSTRAINT `fk_questions_quiz`
    FOREIGN KEY (`quiz_id`)
    REFERENCES `open_school_db`.`quiz` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_questions_question_type`
    FOREIGN KEY (`question_type_id`)
    REFERENCES `open_school_db`.`question_type` (`id`)
    )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `open_school_db`.`answer_options`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`answer_options` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `answer_option` TEXT NOT NULL,
  `is_right_answer` BOOLEAN NOT NULL,
  `question_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_answer_options_question_idx` (`question_id` ASC) VISIBLE,
  CONSTRAINT `fk_answer_options_question`
    FOREIGN KEY (`question_id`)
    REFERENCES `open_school_db`.`questions` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

INSERT INTO quiz_status(id, status_type) VALUES (1,'IN_PROGRESS');
INSERT INTO quiz_status(id, status_type) VALUES (2,'COMPLETED');
INSERT INTO quiz_status(id, status_type) VALUES (3,'FAILED');

INSERT INTO question_type(id, type) VALUES (1,'MATCHING');
INSERT INTO question_type(id, type) VALUES (2,'MULTIPLE_CHOICE');
