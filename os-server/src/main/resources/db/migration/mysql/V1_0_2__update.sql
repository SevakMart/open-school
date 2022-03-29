-- -----------------------------------------------------
-- Table `open_school_db`.`learning_path`
-- -----------------------------------------------------
ALTER TABLE `open_school_db`.learning_path
ADD COLUMN rating FLOAT AFTER description;

-- -----------------------------------------------------
-- Table `open_school_db`.`category`
-- -----------------------------------------------------
ALTER TABLE `open_school_db`.category
DROP FOREIGN KEY fk_category_category1;

ALTER TABLE category
DROP INDEX fk_category_category1_idx;