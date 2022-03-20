-- -----------------------------------------------------
-- Schema open_school_db
-- -----------------------------------------------------
-- CREATE SCHEMA IF NOT EXISTS open_school_db;


-- -----------------------------------------------------
-- Table user_role
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user_role
(
    id INT NOT NULL AUTO_INCREMENT,
    role_type VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);


-- -----------------------------------------------------
-- Table company
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS company
(
    id INT NOT NULL AUTO_INCREMENT,
    company_name VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table user
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(45) NOT NULL,
    last_name VARCHAR(45) NULL,
    phone_number VARCHAR(45) NULL,
    email VARCHAR(45) NOT NULL,
    password VARCHAR(245) NOT NULL,
    profession_name VARCHAR(45) NULL,
    course_count INT NULL,
    user_img_path VARCHAR(145) NULL,
    role_id INT NOT NULL,
    company_id INT NULL,
    email_path VARCHAR(45) NULL,
    linkedin_path VARCHAR(45) NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_user_role1
        FOREIGN KEY (role_id)
            REFERENCES user_role (id)
            ON UPDATE CASCADE,
    CONSTRAINT fk_user_company1
        FOREIGN KEY (company_id)
            REFERENCES company (id)
            ON UPDATE CASCADE
);
    CREATE INDEX fk_user_user_role1_idx ON user (role_id ASC);
    CREATE INDEX fk_user_company1_idx ON user (company_id ASC);
    CREATE UNIQUE INDEX email_UNIQUE ON user(email ASC);


-- -----------------------------------------------------
-- Table category
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS category
(
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(45) NOT NULL,
    parent_category_id INT NULL,
    sub_category_count INT NULL,
    logo_path VARCHAR(45) NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_category_category1
        FOREIGN KEY (parent_category_id)
            REFERENCES category (id)
            ON UPDATE CASCADE
);

    CREATE INDEX fk_category_category1_idx ON category(parent_category_id ASC);

-- -----------------------------------------------------
-- Table language
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS language
(
    id INT NOT NULL AUTO_INCREMENT,
    language_name VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);


-- -----------------------------------------------------
-- Table difficulty
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS difficulty
(
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table learning_path
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS learning_path
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(45) NOT NULL,
    description TEXT NULL,
    category_id INT NOT NULL,
    language_id INT NOT NULL,
    difficulty_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_learning_path_category1
        FOREIGN KEY (category_id)
            REFERENCES category (id)
            ON UPDATE CASCADE,
    CONSTRAINT fk_learning_path_language1
        FOREIGN KEY (language_id)
            REFERENCES language (id)
            ON UPDATE CASCADE,
    CONSTRAINT fk_learning_path_difficulty1
        FOREIGN KEY (difficulty_id)
            REFERENCES difficulty (id)
            ON UPDATE CASCADE
);
    CREATE INDEX fk_learning_path_category1_idx ON learning_path (category_id ASC);
    CREATE INDEX fk_learning_path_language1_idx ON learning_path (language_id ASC);
    CREATE INDEX fk_learning_path_difficulty1_idx ON learning_path (difficulty_id ASC);

-- -----------------------------------------------------
-- Table module
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS module
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    videos TEXT NULL,
    readings TEXT NULL,
    homework TEXT NULL,
    learning_path_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_module_learning_path1
        FOREIGN KEY (learning_path_id)
            REFERENCES learning_path (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);
    CREATE INDEX fk_module_learning_path1_idx ON module (learning_path_id ASC);


-- -----------------------------------------------------
-- Table status
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS status
(
    id INT NOT NULL AUTO_INCREMENT,
    status_type VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table learning_path_student
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS learning_path_student
(
    learning_path_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    status_id INT NOT NULL,
    PRIMARY KEY (learning_path_id, user_id),
    CONSTRAINT fk_learning_path_has_user_learning_path1
        FOREIGN KEY (learning_path_id)
            REFERENCES learning_path (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_learning_path_has_user_user1
        FOREIGN KEY (user_id)
            REFERENCES user (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_learning_path_student_status1
        FOREIGN KEY (status_id)
            REFERENCES status (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

    CREATE INDEX fk_learning_path_has_user_user1_idx ON learning_path_student (user_id ASC);
    CREATE INDEX fk_learning_path_has_user_learning_path1_idx ON learning_path_student(learning_path_id ASC);
    CREATE INDEX fk_learning_path_student_status1_idx ON learning_path_student (status_id ASC);


-- -----------------------------------------------------
-- Table `open_school_db`.`learning_path_mentor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS learning_path_mentor
(
    learning_path_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (learning_path_id, user_id),
    CONSTRAINT fk_learning_path_has_user_learning_path2
        FOREIGN KEY (learning_path_id)
            REFERENCES learning_path (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_learning_path_has_user_user2`
        FOREIGN KEY (user_id)
            REFERENCES user (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);
    CREATE INDEX fk_learning_path_has_user_user2_idx ON learning_path_mentor (user_id ASC);
    CREATE INDEX fk_learning_path_has_user_learning_path2_idx ON learning_path_mentor (learning_path_id ASC);

INSERT INTO user_role (role_type)
VALUES ('STUDENT');
INSERT INTO user_role (role_type)
VALUES ('ADMIN');
INSERT INTO user_role (role_type)
VALUES ('MENTOR');

INSERT INTO status (status_type)
VALUES ('INITIAL');
INSERT INTO status (status_type)
VALUES ('IN_PROGRESS');
INSERT INTO status (status_type)
VALUES ('COMPLETED');