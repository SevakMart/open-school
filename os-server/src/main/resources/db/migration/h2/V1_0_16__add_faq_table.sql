-- -----------------------------------------------------
-- Add table `FAQ`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS faq(
    id BIGINT NOT NULL AUTO_INCREMENT,
    question VARCHAR(255) NOT NULL,
    answer VARCHAR(255),
    learning_path_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_learning_path_faq
        FOREIGN KEY (learning_path_id)
            REFERENCES learning_path (id)
            ON UPDATE CASCADE
            ON DELETE CASCADE
);