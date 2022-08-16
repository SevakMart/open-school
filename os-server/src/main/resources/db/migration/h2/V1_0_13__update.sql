ALTER TABLE category
ALTER COLUMN logo_path SET DATA TYPE VARCHAR(255);

ALTER TABLE category
DROP COLUMN sub_category_count;