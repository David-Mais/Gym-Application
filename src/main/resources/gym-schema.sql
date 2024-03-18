CREATE DATABASE IF NOT EXISTS gym;
USE gym;

create table if not exists training_types
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    training_type_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE if not exists users
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    username   VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    is_active  BOOLEAN      NOT NULL
);

CREATE TABLE if not exists trainees
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    date_of_birth DATE NOT NULL,
    address       VARCHAR(255),
    FOREIGN KEY (id) REFERENCES users (id)
);

CREATE TABLE if not exists trainers
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    specialization       BIGINT,
    FOREIGN KEY (specialization) REFERENCES training_types (id),
    FOREIGN KEY (id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS trainings
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    trainee_id        BIGINT NOT NULL,
    trainer_id        BIGINT NOT NULL,
    training_name     VARCHAR(255),
    training_type_id  BIGINT NOT NULL,
    training_date     DATE   NOT NULL,
    training_duration INT    NOT NULL,
    FOREIGN KEY (trainee_id) REFERENCES trainees (id),
    FOREIGN KEY (trainer_id) REFERENCES trainers (id),
    FOREIGN KEY (training_type_id) REFERENCES training_types (id)
);


INSERT INTO users (first_name, is_active, last_name, password, username)
VALUES
    ('Davit', true, 'Maisuradze', 'newPass', 'Davit.Maisuradze'),
    ('Mariam', true, 'Katamashvili', 'marimagaria', 'Mariam.Katamashvili'),
    ('Merab', true, 'Dvlaishvili', 'merabmerab', 'Merab.Dvalishvili'),
    ('David', true, 'Kheladze', 'davdav', 'David.Kheladze'),
    ('Salome', true, 'Chachua', 'salosalo', 'Salome.Chachua'),
    ('John', false, 'Doe', 'johnny', 'John.Doe'),
    ('Ilia', true, 'Topuria', 'ufcchamp', 'Ilia.Topuria');

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
