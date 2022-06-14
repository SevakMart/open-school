-- -----------------------------------------------------
-- TRIGGER on_insert_to_learning_path - AFTER INSERT ON learning_path
-- -----------------------------------------------------

CREATE TRIGGER on_insert_to_learning_path
AFTER INSERT ON learning_path
FOR EACH ROW CAll "app.openschool.common.dbsupport.UpdateUserCourseCountTrigger";


