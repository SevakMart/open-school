-- -----------------------------------------------------
-- Table learning_path
-- -----------------------------------------------------
ALTER TABLE learning_path
ADD COLUMN goal VARCHAR(45);

-- -----------------------------------------------------
-- Table module
-- -----------------------------------------------------
ALTER TABLE module
ADD COLUMN title VARCHAR(45);

-- -----------------------------------------------------
-- Table module_item
-- -----------------------------------------------------
ALTER TABLE module_item
ADD COLUMN link VARCHAR(255);