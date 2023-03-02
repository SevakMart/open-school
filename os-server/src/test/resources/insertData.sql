INSERT INTO company (company_name)
VALUES ('RTWare Solutions');
INSERT INTO user (first_name, last_name, phone_number, email, password, profession_name, role_id, company_id, enabled)
VALUES ('Poxos', 'Poxosyan', '093157436', 'poxos@gm.com', 'pass123', 'Java Developer', 3, 1, TRUE);

INSERT INTO category (title) VALUES ('Development');
INSERT INTO difficulty (title) VALUES ('Basic');
INSERT INTO language (language_name) VALUES ('English');

INSERT INTO learning_path (title, description, rating, mentor_id, category_id, language_id, difficulty_id)
VALUES ('Java core', 'Java ', 4.7, 1, 1, 1, 1);

INSERT INTO faq (id, question, answer, learning_path_id)
VALUES (1, 'question1', 'answer1', 1),(2, 'question2', 'answer2', 1);
