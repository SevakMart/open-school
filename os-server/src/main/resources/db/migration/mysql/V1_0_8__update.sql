-- -----------------------------------------------------
-- Table user_saved_learning_paths - Creating table for courses which user has saved
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`user_saved_learning_paths`
(
    `user_id` BIGINT NOT NULL,
    `learning_path_id` BIGINT NOT NULL,
    PRIMARY KEY (`user_id`, `learning_path_id`),
    INDEX `user_saved_learning_paths_idx` (`user_id` ASC) VISIBLE,
    INDEX `learning_path_user_idx` (`learning_path_id` ASC) VISIBLE,
    CONSTRAINT `fk_user_saved_learning_paths`
        FOREIGN KEY (`user_id`)
             REFERENCES `open_school_db`.`user` (`id`)
             ON DELETE CASCADE
             ON UPDATE CASCADE,
    CONSTRAINT `fk_learning_path_user`
        FOREIGN KEY (`learning_path_id`)
            REFERENCES `open_school_db`.`learning_path` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE)

            ENGINE = InnoDB;

