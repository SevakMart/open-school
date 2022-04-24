-- -----------------------------------------------------
-- Table `open_school_db`.`user`
-- -----------------------------------------------------
ALTER TABLE `open_school_db`.`user`
ADD COLUMN `reset_password_token` VARCHAR(45) NULL AFTER `linkedin_path`,
ADD COLUMN `enabled` BOOLEAN;

-- -----------------------------------------------------
-- Table `open_school_db`.`verification_token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`verification_token` (
 `id` BIGINT NOT NULL AUTO_INCREMENT,
 `token` VARCHAR(45) NOT NULL,
 `user_id` BIGINT NOT NULL,
 `expires_data` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_verification_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_verification_user`
   FOREIGN KEY (`user_id`)
   REFERENCES `open_school_db`.`user` (`id`)
   ON UPDATE CASCADE
   ON DELETE CASCADE)
ENGINE = InnoDB;

