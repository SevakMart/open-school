-- -----------------------------------------------------
-- TRIGGER `on_insert_to_learning_path` - AFTER INSERT ON `learning_path`
-- -----------------------------------------------------

CREATE TRIGGER `on_insert_to_learning_path`
AFTER INSERT ON `open_school_db`.`learning_path`
FOR EACH ROW
UPDATE `open_school_db`.`user` u SET u.course_count =
       (SELECT COUNT(*) FROM `open_school_db`.`learning_path` lp
        WHERE lp.mentor_id = u.id);

-- -----------------------------------------------------
-- Table `open_school_db`.`user_has_mentor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`user_has_mentor` (
  `user_id` BIGINT(20) NOT NULL,
  `mentor_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`user_id`, `mentor_id`),
  INDEX `fk_user_has_user_user2_idx` (`mentor_id` ASC) VISIBLE,
  INDEX `fk_user_has_user_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `open_school_db`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_has_user_user2`
    FOREIGN KEY (`mentor_id`)
    REFERENCES `open_school_db`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;