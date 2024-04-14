INSERT INTO users (first_name, is_active, last_name, password, username)
VALUES
    ('Davit', 1, 'Maisuradze', 'newPass', 'Davit.Maisuradze'),
    ('Mariam', 1, 'Katamashvili', 'marimagaria', 'Mariam.Katamashvili'),
    ('Merab', 1, 'Dvlaishvili', 'merabmerab', 'Merab.Dvalishvili'),
    ('David', 1, 'Kheladze', 'davdav', 'David.Kheladze'),
    ('Salome', 1, 'Chachua', 'salosalo', 'Salome.Chachua'),
    ('John', 0, 'Doe', 'johnny', 'John.Doe'),
    ('Ilia', 1, 'Topuria', 'ufcchamp', 'Ilia.Topuria');

INSERT INTO training_types (training_type_name)
VALUES ('box'), ('dance'), ('mma');

INSERT INTO trainees (address, date_of_birth, id)
VALUES
    ('Kutaisi', '2024-09-20', 1),
    ('Tbilisi', '2004-03-01', 2),
    ('Batumi', '2000-06-17', 4),
    ('Xashuri', '1999-12-23', 6);

INSERT INTO trainers (specialization, id)
VALUES
    (1, 3),
    (2, 5),
    (3, 7);

INSERT INTO trainings (training_duration, training_date, training_name, trainee_id, trainer_id, training_type_id)
VALUES
    (90, '1999-11-05', 'Marathon Prep', 1, 3, 1),
    (60, '2010-12-15', 'Yoga Basics', 2, 5, 2),
    (75, '2020-04-10', 'Weightlifting 101', 4, 7, 3),
    (60, '2024-08-12', 'Interval Training', 6, 3, 1);
