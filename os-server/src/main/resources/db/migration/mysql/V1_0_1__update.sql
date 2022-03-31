-- -----------------------------------------------------
-- Table `open_school_db`.`category_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`category_user` (
    `category_id` INT NOT NULL,
    `user_id` BIGINT NOT NULL,
    PRIMARY KEY (`category_id`, `user_id`),
    INDEX `fk_category_has_user_user1_idx` (`user_id` ASC) VISIBLE,
    INDEX `fk_category_has_user_category1_idx` (`category_id` ASC) VISIBLE,
    CONSTRAINT `fk_category_has_user_category1`
      FOREIGN KEY (`category_id`)
      REFERENCES `open_school_db`.`category` (`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
    CONSTRAINT `fk_category_has_user_user1`
      FOREIGN KEY (`user_id`)
      REFERENCES `open_school_db`.`user` (`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE)
ENGINE = InnoDB;

USE `open_school_db` ;

