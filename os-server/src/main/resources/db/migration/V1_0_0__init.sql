
-- -----------------------------------------------------
-- Schema open_school_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `open_school_db` DEFAULT CHARACTER SET utf8 ;
USE `open_school_db` ;

-- -----------------------------------------------------
-- Table `open_school_db`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`user_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `open_school_db`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`company` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `company_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `open_school_db`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NULL,
  `phone_number` VARCHAR(45) NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(245) NOT NULL,
  `profession_name` VARCHAR(45) NULL,
  `course_count` INT NULL,
  `user_img_path` VARCHAR(145) NULL,
  `role_id` INT NOT NULL,
  `company_id` INT NULL,
  `email_path` VARCHAR(45) NULL,
  `linkedin_path` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_user_role1_idx` (`role_id` ASC) VISIBLE,
  INDEX `fk_user_company1_idx` (`company_id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  CONSTRAINT `fk_user_user_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `open_school_db`.`user_role` (`id`)
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_company1`
    FOREIGN KEY (`company_id`)
    REFERENCES `open_school_db`.`company` (`id`)
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `open_school_db`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `parent_category_id` INT NULL,
  `sub_category_count` INT NULL,
  `logo_path` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_category_category1_idx` (`parent_category_id` ASC) VISIBLE,
  CONSTRAINT `fk_category_category1`
    FOREIGN KEY (`parent_category_id`)
    REFERENCES `open_school_db`.`category` (`id`)
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `open_school_db`.`language`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`language` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `language_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `open_school_db`.`difficulty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`difficulty` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `open_school_db`.`learning_path`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`learning_path` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `description` TEXT NULL,
  `category_id` INT NOT NULL,
  `language_id` INT NOT NULL,
  `difficulty_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_learning_path_category1_idx` (`category_id` ASC) VISIBLE,
  INDEX `fk_learning_path_language1_idx` (`language_id` ASC) VISIBLE,
  INDEX `fk_learning_path_difficulty1_idx` (`difficulty_id` ASC) VISIBLE,
  CONSTRAINT `fk_learning_path_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `open_school_db`.`category` (`id`)
    ON UPDATE CASCADE,
  CONSTRAINT `fk_learning_path_language1`
    FOREIGN KEY (`language_id`)
    REFERENCES `open_school_db`.`language` (`id`)
    ON UPDATE CASCADE,
  CONSTRAINT `fk_learning_path_difficulty1`
    FOREIGN KEY (`difficulty_id`)
    REFERENCES `open_school_db`.`difficulty` (`id`)
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `open_school_db`.`module`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`module` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `videos` TEXT NULL,
  `readings` TEXT NULL,
  `homework` TEXT NULL,
  `learning_path_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_module_learning_path1_idx` (`learning_path_id` ASC) VISIBLE,
  CONSTRAINT `fk_module_learning_path1`
    FOREIGN KEY (`learning_path_id`)
    REFERENCES `open_school_db`.`learning_path` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `open_school_db`.`status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `open_school_db`.`learning_path_student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`learning_path_student` (
  `learning_path_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `status_id` INT NOT NULL,
  PRIMARY KEY (`learning_path_id`, `user_id`),
  INDEX `fk_learning_path_has_user_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_learning_path_has_user_learning_path1_idx` (`learning_path_id` ASC) VISIBLE,
  INDEX `fk_learning_path_student_status1_idx` (`status_id` ASC) VISIBLE,
  CONSTRAINT `fk_learning_path_has_user_learning_path1`
    FOREIGN KEY (`learning_path_id`)
    REFERENCES `open_school_db`.`learning_path` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_learning_path_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `open_school_db`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_learning_path_student_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `open_school_db`.`status` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `open_school_db`.`learning_path_mentor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`learning_path_mentor` (
  `learning_path_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`learning_path_id`, `user_id`),
  INDEX `fk_learning_path_has_user_user2_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_learning_path_has_user_learning_path2_idx` (`learning_path_id` ASC) VISIBLE,
  CONSTRAINT `fk_learning_path_has_user_learning_path2`
    FOREIGN KEY (`learning_path_id`)
    REFERENCES `open_school_db`.`learning_path` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_learning_path_has_user_user2`
    FOREIGN KEY (`user_id`)
    REFERENCES `open_school_db`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

USE `open_school_db` ;


INSERT INTO user_role (role_type) VALUES ('STUDENT');
INSERT INTO user_role (role_type) VALUES ('ADMIN');
INSERT INTO user_role (role_type) VALUES ('MENTOR');

INSERT INTO status (status_type) VALUES ('INITIAL');
INSERT INTO status (status_type) VALUES ('IN_PROGRESS');
INSERT INTO status (status_type) VALUES ('COMPLETED');