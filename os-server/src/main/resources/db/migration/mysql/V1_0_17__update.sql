CREATE TABLE IF NOT EXISTS `open_school_db`.`discussion_question_ask_peers`(
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

CREATE TABLE IF NOT EXISTS `open_school_db`.`discussion_answer_peers`(
`id` BIGINT NOT NULL AUTO_INCREMENT,
`text` TEXT NOT NULL,
`user_id` BIGINT  UNIQUE,
`discussion_question_id` BIGINT  UNIQUE,
`create_date` TIMESTAMP NOT NULL,
PRIMARY KEY(`id`),
CONSTRAINT `discussion_answer_user_id_fk`
FOREIGN KEY(`user_id`) REFERENCES `open_school_db`.`user`(`id`)
ON DELETE CASCADE
ON UPDATE CASCADE,
CONSTRAINT `discussion_answer_discussion_question_id_fk`
FOREIGN KEY(`discussion_question_id`) REFERENCES `open_school_db`.`discussion_question_ask_peers`(`id`)
ON DELETE CASCADE
ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `open_school_db`.`discussion_question_ask_mentor`(
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

CREATE TABLE IF NOT EXISTS `open_school_db`.`discussion_answer_mentor`(
`id` BIGINT NOT NULL AUTO_INCREMENT,
`text` TEXT NOT NULL,
`user_id` BIGINT  UNIQUE,
`discussion_question_id` BIGINT  UNIQUE,
`create_date` TIMESTAMP NOT NULL,
PRIMARY KEY(`id`),
CONSTRAINT `discussion_answer_mentor_user_id_fk`
FOREIGN KEY(`user_id`) REFERENCES `open_school_db`.`user`(`id`)
ON DELETE CASCADE
ON UPDATE CASCADE,
CONSTRAINT `discussion_answer_mentor_discussion_question_id_fk`
FOREIGN KEY(`discussion_question_id`) REFERENCES `open_school_db`.`discussion_question_ask_mentor`(`id`)
ON DELETE CASCADE
ON UPDATE CASCADE
);



