
CREATE TABLE IF NOT EXISTS module_item_type
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    type VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE module_item
ADD COLUMN title VARCHAR(45) NOT NULL AFTER module_id;

ALTER TABLE module_item
DROP COLUMN module_item_type;

ALTER TABLE module_item
ADD COLUMN module_item_type_id BIGINT AFTER module_id;

ALTER TABLE module_item
ADD CONSTRAINT fk_module_item_type
   FOREIGN KEY (module_item_type_id)
   REFERENCES module_item_type (id)
   ON DELETE CASCADE
   ON UPDATE CASCADE;

CREATE INDEX module_item_type_idx ON module_item (module_item_type_id ASC);

INSERT INTO module_item_type (type)
VALUES ('VIDEO'), ('READING'), ('PRACTICE EXERCISES'), ('OTHER');
