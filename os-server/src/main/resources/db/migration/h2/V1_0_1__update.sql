-- -----------------------------------------------------
-- Table category_user
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS category_user (
    category_id INT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (category_id, user_id),
    CONSTRAINT fk_category_has_user_category1
      FOREIGN KEY (category_id)
      REFERENCES category (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
    CONSTRAINT fk_category_has_user_user1
      FOREIGN KEY (user_id)
      REFERENCES user (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE);

    CREATE INDEX fk_category_has_user_user1_idx ON category_user(user_id ASC);
    CREATE INDEX fk_category_has_user_category1_idx ON category_user(category_id ASC);

