-- -----------------------------------------------------
-- Table user_saved_learning_paths
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user_saved_learning_paths
(
    user_id BIGINT NOT NULL,
    learning_path_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, learning_path_id),
    CONSTRAINT fk_user_saved_learning_paths
        FOREIGN KEY (user_id)
             REFERENCES user (id)
             ON DELETE CASCADE
             ON UPDATE CASCADE,
    CONSTRAINT fk_learning_path_user
        FOREIGN KEY (learning_path_id)
            REFERENCES learning_path (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

    CREATE INDEX user_saved_learning_paths_idx ON user_saved_learning_paths (user_id ASC);
    CREATE INDEX learning_path_user_idx ON user_saved_learning_paths (learning_path_id ASC);
