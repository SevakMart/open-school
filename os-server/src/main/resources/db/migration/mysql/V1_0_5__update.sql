-- -----------------------------------------------------
-- Table `open_school_db`.`user`
-- -----------------------------------------------------
ALTER TABLE `open_school_db`.`user`
ADD COLUMN `reset_password_token` VARCHAR(45) NULL AFTER `linkedin_path`;

