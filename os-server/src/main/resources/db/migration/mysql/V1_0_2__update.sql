
-- -- -----------------------------------------------------
-- -- Table `open_school_db`.`learning_path`
-- -- -----------------------------------------------------

ALTER TABLE `open_school_db`.learning_path
ADD COLUMN rating FLOAT AFTER description;

-- -----------------------------------------------------
-- Table `open_school_db`.category
-- -----------------------------------------------------

ALTER TABLE `open_school_db`.category
DROP FOREIGN KEY fk_category_category1;

ALTER TABLE `open_school_db`.category
DROP INDEX fk_category_category1_idx;

ALTER TABLE `open_school_db`.`category_user`
DROP FOREIGN KEY fk_category_has_user_category1;

ALTER TABLE `open_school_db`.`category_user`
CHANGE COLUMN `category_id` `category_id` BIGINT NOT NULL;

ALTER TABLE `open_school_db`.`learning_path`
DROP FOREIGN KEY fk_learning_path_category1;

ALTER TABLE `open_school_db`.`learning_path`
CHANGE COLUMN `category_id` `category_id` BIGINT NOT NULL;

ALTER TABLE `open_school_db`.`category`
CHANGE COLUMN `id` `id` BIGINT NOT NULL AUTO_INCREMENT;

ALTER TABLE `open_school_db`.`category`
CHANGE COLUMN `parent_category_id` `parent_category_id` BIGINT NULL;

ALTER TABLE `open_school_db`.`category_user`
ADD CONSTRAINT fk_category_has_user_category1
FOREIGN KEY (`category_id`)
REFERENCES `open_school_db`.`category` (`id`);

ALTER TABLE `open_school_db`.`learning_path`
ADD CONSTRAINT fk_learning_path_category1
FOREIGN KEY (`category_id`)
REFERENCES `open_school_db`.`category` (`id`);
