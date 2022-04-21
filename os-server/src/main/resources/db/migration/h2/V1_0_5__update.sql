-- -----------------------------------------------------
-- Table user
-- -----------------------------------------------------
ALTER TABLE user
ADD COLUMN reset_password_token VARCHAR(45) NULL AFTER linkedin_path;
