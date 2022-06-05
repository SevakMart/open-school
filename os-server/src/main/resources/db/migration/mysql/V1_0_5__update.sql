-- -----------------------------------------------------
-- Table user - adding column "enabled" in the table, which shows the user's account is verified or not
-- -----------------------------------------------------

ALTER TABLE `open_school_db`.`user`
ADD COLUMN enabled BOOLEAN;

-- -----------------------------------------------------------------------------------
-- Table reset_password_token - creating table for token which is being sent to user for resetting the forgotten password
-- -----------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS `open_school_db`.`reset_password_token` (
 `id` BIGINT NOT NULL AUTO_INCREMENT,
 `token` VARCHAR(30) NOT NULL,
 `user_id` BIGINT NOT NULL,
 `created_at` TIMESTAMP NOT NULL,
 PRIMARY KEY (`id`),
 INDEX `reset_password_token_idx` (`user_id` ASC) VISIBLE,
 CONSTRAINT `fk_reset_password_token`
     FOREIGN KEY (`user_id`)
         REFERENCES `open_school_db`.`user` (`id`)
         ON UPDATE CASCADE
         ON DELETE CASCADE)

    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table verification_token - creating table for token which is being sent to user for verifying the account
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`verification_token` (
 `id` BIGINT NOT NULL AUTO_INCREMENT,
 `token` VARCHAR(45) NOT NULL,
 `user_id` BIGINT NOT NULL,
 `created_date` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_verification_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_verification_user`
  FOREIGN KEY (`user_id`)
  REFERENCES `open_school_db`.`user` (`id`)
  ON UPDATE CASCADE
  ON DELETE CASCADE)

  ENGINE = InnoDB;