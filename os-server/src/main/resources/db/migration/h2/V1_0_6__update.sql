-- -----------------------------------------------------
-- Table learning_path
-- -----------------------------------------------------
ALTER TABLE learning_path
DROP CONSTRAINT learning_path_status_fk;

DROP INDEX learning_path_status_fk_idx;

ALTER TABLE learning_path
DROP COLUMN learning_path_status_id;

ALTER TABLE learning_path
DROP COLUMN due_date;

ALTER TABLE learning_path
ADD COLUMN mentor_id BIGINT NOT NULL AFTER rating;

ALTER TABLE learning_path
ADD CONSTRAINT mentor_id_fk
  FOREIGN KEY (mentor_id)
  REFERENCES user (id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

CREATE INDEX mentor_id_idx ON learning_path (mentor_id ASC);


-- -----------------------------------------------------
-- Table module
-- -----------------------------------------------------
ALTER TABLE module
DROP CONSTRAINT module_status_fk;

DROP INDEX module_status_fk_idx;

ALTER TABLE module
DROP COLUMN module_status_id;

-- -----------------------------------------------------
-- Table module_item
-- -----------------------------------------------------
ALTER TABLE module_item
DROP CONSTRAINT module_item_has_status_fk;

DROP INDEX module_item_has_status_fk_idx;

ALTER TABLE module_item
DROP COLUMN module_item_status_id;

ALTER TABLE module_item
DROP COLUMN grade;

-- -----------------------------------------------------
-- Table learning_path_mentor
-- -----------------------------------------------------
DROP TABLE learning_path_mentor;

-- -----------------------------------------------------
-- Table learning_path_student
-- -----------------------------------------------------
DROP TABLE learning_path_student;

-- -----------------------------------------------------
-- Table enrolled_learning_path
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS enrolled_learning_path (
 id BIGINT NOT NULL AUTO_INCREMENT,
 due_date DATE NULL,
 learning_path_id BIGINT NOT NULL,
 user_id BIGINT NOT NULL,
 learning_path_status_id BIGINT NOT NULL,
 PRIMARY KEY (id),
 CONSTRAINT elp_has_learning_path_fk
    FOREIGN KEY (learning_path_id)
    REFERENCES learning_path (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
 CONSTRAINT elp_has_user_fk
    FOREIGN KEY (user_id)
    REFERENCES user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
 CONSTRAINT elp_has_status_fk
    FOREIGN KEY (learning_path_status_id)
    REFERENCES learning_path_status (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

  CREATE INDEX elp_has_learning_path_idx ON enrolled_learning_path (learning_path_id ASC);
  CREATE INDEX elp_has_user_idx ON enrolled_learning_path (user_id ASC);
  CREATE INDEX elp_has_status_idx ON enrolled_learning_path (learning_path_status_id ASC);

-- -----------------------------------------------------
-- Table enrolled_module
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS enrolled_module (
 id BIGINT NOT NULL AUTO_INCREMENT,
 module_id BIGINT NOT NULL,
 enrolled_learning_path_id BIGINT NOT NULL,
 module_status_id BIGINT NOT NULL,
 PRIMARY KEY (id),
 CONSTRAINT em_has_module_fk
    FOREIGN KEY (module_id)
    REFERENCES module (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
 CONSTRAINT em_has_elp_fk
    FOREIGN KEY (enrolled_learning_path_id)
    REFERENCES enrolled_learning_path (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
 CONSTRAINT em_has_module_status_fk
    FOREIGN KEY (module_status_id)
    REFERENCES module_status (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

  CREATE INDEX em_has_module_idx ON enrolled_module (module_id ASC);
  CREATE INDEX em_has_elp_idx ON enrolled_module (enrolled_learning_path_id ASC);
  CREATE INDEX em_has_module_status_idx ON enrolled_module (module_status_id ASC);

-- -----------------------------------------------------
-- Table enrolled_module_item
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS enrolled_module_item (
 id BIGINT NOT NULL AUTO_INCREMENT,
 grade INT NULL,
 module_item_id BIGINT NOT NULL,
 enrolled_module_id BIGINT NOT NULL,
 module_item_status_id BIGINT NOT NULL,
 PRIMARY KEY (id),
 CONSTRAINT emi_has_module_item_fk
    FOREIGN KEY (module_item_id)
    REFERENCES module_item (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
 CONSTRAINT emi_has_enrolled_module_fk
    FOREIGN KEY (enrolled_module_id)
    REFERENCES enrolled_module (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
 CONSTRAINT emi_has_module_item_status_fk
    FOREIGN KEY (module_item_status_id)
    REFERENCES module_item_status (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

  CREATE INDEX emi_has_module_item_idx ON enrolled_module_item (module_item_id ASC);
  CREATE INDEX emi_has_enrolled_module_idx ON enrolled_module_item (enrolled_module_id ASC);
  CREATE INDEX emi_has_module_item_status_idx ON enrolled_module_item (module_item_status_id ASC);








