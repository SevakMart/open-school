-- -----------------------------------------------------
-- Table keyword
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS keyword (
  id BIGINT NOT NULL AUTO_INCREMENT,
  title VARCHAR(45) NOT NULL,
  PRIMARY KEY (id));

-- -----------------------------------------------------
-- Table keyword_learning_path
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS keyword_learning_path (
    keyword_id BIGINT NOT NULL,
    learning_path_id BIGINT NOT NULL,
    PRIMARY KEY (keyword_id, learning_path_id),
    CONSTRAINT fk_keyword_has_keyword_learning_path
      FOREIGN KEY (keyword_id)
      REFERENCES keyword (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
    CONSTRAINT fk_learning_path_has_keyword_learning_path
      FOREIGN KEY (learning_path_id)
      REFERENCES learning_path (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE);

      CREATE INDEX fk_keyword_has_learning_path_idx ON keyword_learning_path(keyword_id ASC);
      CREATE INDEX fk_learning_path_has_keyword_idx ON keyword_learning_path(learning_path_id ASC);


