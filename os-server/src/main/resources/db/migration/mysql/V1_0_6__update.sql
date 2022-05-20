-- -----------------------------------------------------
-- Table `open_school_db`.`learning_path`
-- -----------------------------------------------------
ALTER TABLE `open_school_db`.`learning_path`
DROP FOREIGN KEY learning_path_status_fk,
DROP INDEX learning_path_status_fk_idx,
DROP COLUMN `learning_path_status_id`,
DROP COLUMN `due_date`,
ADD COLUMN `mentor_id` BIGINT NOT NULL AFTER `rating`,
ADD INDEX `mentor_id_idx` (`mentor_id` ASC) VISIBLE,
ADD CONSTRAINT `mentor_id_fk`
  FOREIGN KEY (`mentor_id`)
  REFERENCES `open_school_db`.`user` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

-- -----------------------------------------------------
-- Table `open_school_db`.`module`
-- -----------------------------------------------------
ALTER TABLE `open_school_db`.`module`
DROP FOREIGN KEY module_status_fk,
DROP INDEX module_status_fk_idx,
DROP COLUMN `module_status_id`;

-- -----------------------------------------------------
-- Table `open_school_db`.`module_item`
-- -----------------------------------------------------
ALTER TABLE `open_school_db`.`module_item`
DROP FOREIGN KEY module_item_has_status_fk,
DROP INDEX module_item_has_status_fk_idx,
DROP COLUMN `module_item_status_id`,
DROP COLUMN `grade`;

-- -----------------------------------------------------
-- Table `open_school_db`.`learning_path_mentor`
-- -----------------------------------------------------
DROP TABLE `open_school_db`.`learning_path_mentor`;

-- -----------------------------------------------------
-- Table `open_school_db`.`learning_path_student`
-- -----------------------------------------------------
DROP TABLE `open_school_db`.`learning_path_student`;

-- -----------------------------------------------------
-- Table `open_school_db`.`enrolled_learning_path`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`enrolled_learning_path` (
 `id` BIGINT NOT NULL AUTO_INCREMENT,
 `due_date` DATE NULL,
 `learning_path_id` BIGINT NOT NULL,
 `user_id` BIGINT NOT NULL,
 `learning_path_status_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `elp_has_learning_path_idx` (`learning_path_id` ASC) VISIBLE,
  INDEX `elp_has_user_idx` (`user_id` ASC) VISIBLE,
  INDEX `elp_has_status_idx` (`learning_path_status_id` ASC) VISIBLE,
  CONSTRAINT `elp_has_learning_path_fk`
    FOREIGN KEY (`learning_path_id`)
    REFERENCES `open_school_db`.`learning_path` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `elp_has_user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `open_school_db`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `elp_has_status_fk`
    FOREIGN KEY (`learning_path_status_id`)
    REFERENCES `open_school_db`.`learning_path_status` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `open_school_db`.`enrolled_module`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`enrolled_module` (
 `id` BIGINT NOT NULL AUTO_INCREMENT,
 `module_id` BIGINT NOT NULL,
 `enrolled_learning_path_id` BIGINT NOT NULL,
 `module_status_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `em_has_module_idx` (`module_id` ASC) VISIBLE,
  INDEX `em_has_elp_idx` (`enrolled_learning_path_id` ASC) VISIBLE,
  INDEX `em_has_module_status_idx` (`module_status_id` ASC) VISIBLE,
  CONSTRAINT `em_has_module_fk`
    FOREIGN KEY (`module_id`)
    REFERENCES `open_school_db`.`module` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `em_has_elp_fk`
    FOREIGN KEY (`enrolled_learning_path_id`)
    REFERENCES `open_school_db`.`enrolled_learning_path` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `em_has_module_status_fk`
    FOREIGN KEY (`module_status_id`)
    REFERENCES `open_school_db`.`module_status` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `open_school_db`.`enrolled_module_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`enrolled_module_item` (
 `id` BIGINT NOT NULL AUTO_INCREMENT,
 `grade` INT NULL,
 `module_item_id` BIGINT NOT NULL,
 `enrolled_module_id` BIGINT NOT NULL,
 `module_item_status_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `emi_has_module_item_idx` (`module_item_id` ASC) VISIBLE,
  INDEX `emi_has_enrolled_module_idx` (`enrolled_module_id` ASC) VISIBLE,
  INDEX `emi_has_module_item_status_idx` (`module_item_status_id` ASC) VISIBLE,
  CONSTRAINT `emi_has_module_item_fk`
    FOREIGN KEY (`module_item_id`)
    REFERENCES `open_school_db`.`module_item` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `emi_has_enrolled_module_fk`
    FOREIGN KEY (`enrolled_module_id`)
    REFERENCES `open_school_db`.`enrolled_module` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `emi_has_module_item_status_fk`
    FOREIGN KEY (`module_item_status_id`)
    REFERENCES `open_school_db`.`module_item_status` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;

  USE `open_school_db` ;








