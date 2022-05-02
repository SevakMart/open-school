-- -----------------------------------------------------
-- verification_token
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS reset_password_token (
 id BIGINT NOT NULL AUTO_INCREMENT,
 token VARCHAR(30) NOT NULL,
 user_id BIGINT NOT NULL,
 created_at TIMESTAMP NOT NULL,
 PRIMARY KEY (id),
 CONSTRAINT fk_reset_password_token
 FOREIGN KEY (user_id)
 REFERENCES user (id)
  ON UPDATE CASCADE
  ON DELETE CASCADE);

CREATE INDEX fk_reset_password_token_idx ON reset_password_token (user_id ASC);