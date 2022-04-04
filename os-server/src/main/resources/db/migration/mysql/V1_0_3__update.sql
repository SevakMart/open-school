-- -----------------------------------------------------
-- Table `open_school_db`.`keyword`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`keyword` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `open_school_db`.`keyword_learning_path`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`keyword_learning_path` (
    `keyword_id` BIGINT NOT NULL,
    `learning_path_id` BIGINT NOT NULL,
    PRIMARY KEY (`keyword_id`, `learning_path_id`),
    INDEX `fk_keyword_has_learning_path_idx` (`keyword_id` ASC) VISIBLE,
    INDEX `fk_learning_path_has_keyword_idx` (`learning_path_id` ASC) VISIBLE,
    CONSTRAINT `fk_keyword_has_keyword_learning_path`
      FOREIGN KEY (`keyword_id`)
      REFERENCES `open_school_db`.`keyword` (`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
    CONSTRAINT `fk_learning_path_has_keyword_learning_path`
      FOREIGN KEY (`learning_path_id`)
      REFERENCES `open_school_db`.`learning_path` (`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE)
ENGINE = InnoDB;

USE `open_school_db` ;

