CREATE TABLE IF NOT EXISTS `open_school_db`.`peers_question`(
`id` BIGINT NOT NULL AUTO_INCREMENT,
`text` TEXT NOT NULL,
`user_id` BIGINT,
`learning_path_id` BIGINT,
`create_date` TIMESTAMP NOT NULL,
PRIMARY KEY(`id`),
CONSTRAINT `discussion_question_user_id_fk`
FOREIGN KEY(`user_id`) REFERENCES `open_school_db`.`user`(`id`)
ON DELETE CASCADE
ON UPDATE CASCADE,
CONSTRAINT `discussion_question_learning_path_id_fk`
FOREIGN KEY(`learning_path_id`) REFERENCES `open_school_db`.`learning_path`(`id`)
ON DELETE CASCADE
ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `open_school_db`.`peers_answer`(
`id` BIGINT NOT NULL AUTO_INCREMENT,
`text` TEXT NOT NULL,
`user_id` BIGINT,
`peers_question_id` BIGINT,
`create_date` TIMESTAMP NOT NULL,
PRIMARY KEY(`id`),
CONSTRAINT `peers_answer_user_id_fk`
FOREIGN KEY(`user_id`) REFERENCES `open_school_db`.`user`(`id`)
ON DELETE CASCADE
ON UPDATE CASCADE,
CONSTRAINT `peers_answer_discussion_question_id_fk`
FOREIGN KEY(`peers_question_id`) REFERENCES `open_school_db`.`peers_question`(`id`)
ON DELETE CASCADE
ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `open_school_db`.`mentor_question`(
`id` BIGINT NOT NULL AUTO_INCREMENT,
`text` TEXT NOT NULL,
`user_id` BIGINT,
`learning_path_id` BIGINT,
`create_date` TIMESTAMP NOT NULL,
PRIMARY KEY(`id`),
CONSTRAINT `discussion_question_ask_mentor_user_id_fk`
FOREIGN KEY(`user_id`) REFERENCES `open_school_db`.`user`(`id`)
ON DELETE CASCADE
ON UPDATE CASCADE,
CONSTRAINT `discussion_question_ask_mentor_learning_path_id_fk`
FOREIGN KEY(`learning_path_id`) REFERENCES `open_school_db`.`learning_path`(`id`)
ON DELETE CASCADE
ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `open_school_db`.`mentor_answer`(
`id` BIGINT NOT NULL AUTO_INCREMENT,
`text` TEXT NOT NULL,
`user_id` BIGINT,
`mentor_question_id` BIGINT,
`create_date` TIMESTAMP NOT NULL,
PRIMARY KEY(`id`),
CONSTRAINT `mentor_question_id_user_id_fk`
FOREIGN KEY(`user_id`) REFERENCES `open_school_db`.`user`(`id`)
ON DELETE CASCADE
ON UPDATE CASCADE,
CONSTRAINT `mentor_question_id_discussion_question_id_fk`
FOREIGN KEY(`mentor_question_id`) REFERENCES `open_school_db`.`mentor_question`(`id`)
ON DELETE CASCADE
ON UPDATE CASCADE
);



