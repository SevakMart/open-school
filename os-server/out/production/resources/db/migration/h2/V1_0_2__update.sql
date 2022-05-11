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

ALTER TABLE category_user
DROP CONSTRAINT fk_category_has_user_category1;

ALTER TABLE category_user
ALTER COLUMN category_id BIGINT NOT NULL;

ALTER TABLE learning_path
DROP CONSTRAINT fk_learning_path_category1;

ALTER TABLE learning_path
ALTER COLUMN category_id BIGINT NOT NULL;

ALTER TABLE category
ALTER COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT;

ALTER TABLE category
ALTER COLUMN parent_category_id BIGINT NULL;

ALTER TABLE category_user
ADD CONSTRAINT fk_category_has_user_category1
FOREIGN KEY (category_id)
REFERENCES category (id);

ALTER TABLE learning_path
ADD CONSTRAINT fk_learning_path_category1
FOREIGN KEY (category_id)
REFERENCES category (id);