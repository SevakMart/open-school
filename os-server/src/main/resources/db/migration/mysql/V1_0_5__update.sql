-- -----------------------------------------------------
-- Table `open_school_db`.`reset_password_token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_school_db`.`reset_password_token` (
 `id` BIGINT NOT NULL AUTO_INCREMENT,
 `token` VARCHAR(30) NOT NULL,
 `user_id` BIGINT NOT NULL,
 created_at TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_reset_password_token_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_reset_password_token`
   FOREIGN KEY (`user_id`)
   REFERENCES `open_school_db`.`user` (`id`)
   ON UPDATE CASCADE
   ON DELETE CASCADE)
ENGINE = InnoDB;

