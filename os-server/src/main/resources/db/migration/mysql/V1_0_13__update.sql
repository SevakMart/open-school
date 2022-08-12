ALTER TABLE `open_school_db`.`category`
MODIFY COLUMN logo_path VARCHAR(255),
MODIFY COLUMN title VARCHAR(255),
DROP COLUMN sub_category_count;
