-- -----------------------------------------------------
-- Table `open_school_db`.`learning_path_student`
-- -----------------------------------------------------

ALTER TABLE `open_school_db`.`learning_path_student`
DROP FOREIGN KEY fk_learning_path_student_status1;

ALTER TABLE `open_school_db`.`learning_path_student`
DROP INDEX fk_learning_path_student_status1_idx;

ALTER TABLE `open_school_db`.`learning_path_student`
DROP COLUMN `status_id`;

-- -----------------------------------------------------
-- Table `open_school_db`.`status`
-- -----------------------------------------------------
ALTER TABLE `open_school_db`.`status`
CHANGE COLUMN `id` `id` BIGINT NOT NULL AUTO_INCREMENT;
-- -----------------------------------------------------
-- Table `open_school_db`.`learning_path`
-- -----------------------------------------------------
ALTER TABLE `open_school_db`.`learning_path`
ADD COLUMN `due_date` DATE NULL AFTER `description`,
ADD COLUMN `learning_path_status_id` BIGINT NOT NULL AFTER `rating`,
ADD INDEX `learning_path_status_fk_idx` (`learning_path_status_id` ASC) VISIBLE,
ADD CONSTRAINT `learning_path_status_fk`
  FOREIGN KEY (`learning_path_status_id`)
  REFERENCES `open_school_db`.`status` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
-- -----------------------------------------------------
-- Table `open_school_db`.`module`
-- -----------------------------------------------------
ALTER TABLE `open_school_db`.`module`
DROP COLUMN `homework`,
DROP COLUMN `readings`,
DROP COLUMN `videos`,
ADD COLUMN `module_status_id` BIGINT NOT NULL AFTER `learning_path_id`,
ADD INDEX `module_status_fk_idx` (`module_status_id` ASC) VISIBLE,
ADD CONSTRAINT `module_status_fk`
  FOREIGN KEY (`module_status_id`)
  REFERENCES `open_school_db`.`status` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
-- -----------------------------------------------------
-- Table `open_school_db`.`module_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`module_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `module_item_type` VARCHAR(45) NOT NULL,
  `estimated_time_in_minutes` BIGINT NULL,
  `grade` INT NULL,
  `module_id` BIGINT NULL,
  `module_item_status_id` BIGINT NULL,
  PRIMARY KEY (`id`),
  INDEX `module_item_has_module_fk_idx` (`module_id` ASC) VISIBLE,
  INDEX `module_item_has_status_fk_idx` (`module_item_status_id` ASC) VISIBLE,
  CONSTRAINT `module_item_has_module_fk`
    FOREIGN KEY (`module_id`)
    REFERENCES `open_school_db`.`module` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `module_item_has_status_fk`
    FOREIGN KEY (`module_item_status_id`)
    REFERENCES `open_school_db`.`status` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;

    USE `open_school_db` ;

