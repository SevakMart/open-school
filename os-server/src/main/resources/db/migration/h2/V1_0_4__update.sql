-- -----------------------------------------------------
-- Table learning_path_student
-- -----------------------------------------------------

ALTER TABLE learning_path_student
DROP CONSTRAINT fk_learning_path_student_status1;

DROP INDEX fk_learning_path_student_status1_idx;

ALTER TABLE learning_path_student
DROP COLUMN status_id;

-- -----------------------------------------------------
-- Table status
-- -----------------------------------------------------
ALTER TABLE status
ALTER COLUMN id BIGINT NOT NULL AUTO_INCREMENT;
-- -----------------------------------------------------
-- Table learning_path
-- -----------------------------------------------------
ALTER TABLE learning_path
ADD COLUMN learning_path_status_id BIGINT NOT NULL AFTER rating;

ALTER TABLE learning_path
ADD CONSTRAINT learning_path_status_fk
  FOREIGN KEY (learning_path_status_id)
  REFERENCES status (id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

CREATE INDEX learning_path_status_fk_idx ON learning_path (learning_path_status_id ASC);

ALTER TABLE learning_path
ADD COLUMN due_date DATE NULL AFTER description;
-- -----------------------------------------------------
-- Table module
-- -----------------------------------------------------
ALTER TABLE module
DROP COLUMN homework;
ALTER TABLE module
DROP COLUMN readings;
ALTER TABLE module
DROP COLUMN videos;
ALTER TABLE module
ADD COLUMN module_status_id BIGINT NOT NULL AFTER learning_path_id;
ALTER TABLE module
ADD CONSTRAINT module_status_fk
  FOREIGN KEY (module_status_id)
  REFERENCES status (id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

CREATE INDEX module_status_fk_idx ON module (module_status_id ASC);
-- -----------------------------------------------------
-- Table module_item
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS module_item (
  id BIGINT NOT NULL AUTO_INCREMENT,
  module_item_type VARCHAR(45) NOT NULL,
  estimated_time_in_minutes BIGINT NULL,
  grade INT NULL,
  module_id BIGINT NULL,
  module_item_status_id BIGINT NULL,
  PRIMARY KEY (id),
  CONSTRAINT module_item_has_module_fk
    FOREIGN KEY (module_id)
    REFERENCES module (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT module_item_has_status_fk
    FOREIGN KEY (module_item_status_id)
    REFERENCES status (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE INDEX module_item_has_module_fk_idx ON module_item (module_id ASC);
CREATE INDEX module_item_has_status_fk_idx ON module_item (module_item_status_id ASC);



