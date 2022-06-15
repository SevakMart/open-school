-- -----------------------------------------------------
-- TRIGGER on_insert_to_learning_path - AFTER INSERT ON learning_path
-- -----------------------------------------------------

CREATE TRIGGER on_insert_to_learning_path
AFTER INSERT ON learning_path
FOR EACH ROW CAll "app.openschool.common.dbsupport.UpdateUserCourseCountTrigger";

-- -----------------------------------------------------
-- Table user_has_mentor
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user_has_mentor (
  user_id BIGINT(20) NOT NULL,
  mentor_id BIGINT(20) NOT NULL,
  PRIMARY KEY (user_id, mentor_id),
  CONSTRAINT fk_user_has_user_user1
    FOREIGN KEY (user_id)
    REFERENCES user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_user_has_user_user2
    FOREIGN KEY (mentor_id)
    REFERENCES user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE INDEX fk_user_has_user_user2_idx ON user_has_mentor (mentor_id ASC);
CREATE INDEX fk_user_has_user_user1_idx ON user_has_mentor (user_id ASC);
