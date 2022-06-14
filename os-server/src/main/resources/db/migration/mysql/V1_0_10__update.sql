-- -----------------------------------------------------
-- TRIGGER `on_insert_to_learning_path` - AFTER INSERT ON `learning_path`
-- -----------------------------------------------------

CREATE TRIGGER `on_insert_to_learning_path`
AFTER INSERT ON `open_school_db`.`learning_path`
FOR EACH ROW
UPDATE `open_school_db`.`user` u SET u.course_count =
       (SELECT COUNT(*) FROM `open_school_db`.`learning_path` lp
        WHERE lp.mentor_id = u.id);

