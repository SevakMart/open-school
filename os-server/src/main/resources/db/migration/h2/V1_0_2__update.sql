-- -----------------------------------------------------
-- Table learning_path
-- -----------------------------------------------------
ALTER TABLE learning_path
ADD COLUMN rating FLOAT AFTER description;

-- -----------------------------------------------------
-- Table category
-- -----------------------------------------------------
ALTER TABLE category
DROP CONSTRAINT fk_category_category1;

DROP INDEX fk_category_category1_idx;