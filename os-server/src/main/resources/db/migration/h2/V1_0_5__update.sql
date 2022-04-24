-- -----------------------------------------------------
-- Table user
-- -----------------------------------------------------
ALTER TABLE user
ADD COLUMN reset_password_token VARCHAR(45) NULL AFTER linkedin_path;

ALTER TABLE user
ADD COLUMN enabled BOOLEAN;

-- -----------------------------------------------------
-- verification_token
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS verification_token (
 id BIGINT NOT NULL AUTO_INCREMENT,
 token VARCHAR(45) NOT NULL,
 user_id BIGINT NOT NULL,
 expires_data BIGINT NOT NULL,
 PRIMARY KEY (id),
 CONSTRAINT fk_verification_user
 FOREIGN KEY (user_id)
 REFERENCES user (id)
  ON UPDATE CASCADE
  ON DELETE CASCADE);

CREATE INDEX fk_verification_user_idx ON verification_token (user_id ASC);