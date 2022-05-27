-- -----------------------------------------------------
-- Table `open_school_db`.`learning_path`
-- -----------------------------------------------------
ALTER TABLE `open_school_db`.`learning_path`
ADD COLUMN goal VARCHAR(45);

-- -----------------------------------------------------
-- Table `open_school_db`.`module`
-- -----------------------------------------------------
ALTER TABLE `open_school_db`.`module`
ADD COLUMN title VARCHAR(45);

-- -----------------------------------------------------
-- Table `open_school_db`.`module_item`
-- -----------------------------------------------------
ALTER TABLE `open_school_db`.`module_item`
ADD COLUMN link VARCHAR(255);

