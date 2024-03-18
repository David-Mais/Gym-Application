create table if not exists training_types
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    training_type_name VARCHAR(255) NOT NULL UNIQUE
    );

CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    username   VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    is_active  BOOLEAN      NOT NULL
);

CREATE TABLE trainees
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    date_of_birth DATE NOT NULL,
    address       VARCHAR(255),
    user_id       bigint null,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE trainers
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    specialization       BIGINT,
    user_id              BIGINT,
    FOREIGN KEY (specialization) REFERENCES training_types (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
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


INSERT INTO users (first_name, is_active, last_name, password, username) VALUES ('Davit', true, 'Maisuradze', 'newPass', 'Davit.Maisuradze');
INSERT INTO users (first_name, is_active, last_name, password, username) VALUES ('Mariam', true, 'Katamashvili', 'marimagaria', 'Mariam.Katamashvili');
INSERT INTO users (first_name, is_active, last_name, password, username) VALUES ('Merab', true, 'Dvlaishvili', 'merabmerab', 'Merab.Dvalishvili');
INSERT INTO users (first_name, is_active, last_name, password, username) VALUES ('David', true, 'Kheladze', 'davdav', 'David.Kheladze');
INSERT INTO users (first_name, is_active, last_name, password, username) VALUES ('Salome', true, 'Chachua', 'salosalo', 'Salome.Chachua');
INSERT INTO users (first_name, is_active, last_name, password, username) VALUES ('John', false, 'Doe', 'johnny', 'John.Doe');
INSERT INTO users (first_name, is_active, last_name, password, username) VALUES ('Ilia', true, 'Topuria', 'ufcchamp', 'Ilia.Topuria');

INSERT INTO training_types (training_type_name) VALUES ('box');
INSERT INTO training_types (training_type_name) VALUES ('dance');
INSERT INTO training_types (training_type_name) VALUES ('mma');


INSERT INTO trainees (address, date_of_birth, user_id)
VALUES ('Kutaisi',
        '2024-09-20',
        (select id from users where username = 'Davit.Maisuradze'));
INSERT INTO trainees (address, date_of_birth, user_id)
VALUES ('Tbilisi',
        '2004-03-01',
        (select id from users where username = 'Mariam.Katamashvili'));
INSERT INTO trainees (address, date_of_birth, user_id)
VALUES ('Batumi',
        '2000-06-17',
        (select id from users where username = 'David.Kheladze'));
INSERT INTO trainees (address, date_of_birth, user_id)
VALUES ('Xashuri',
        '1999-12-23',
        (select id from users where username = 'John.Doe'));


INSERT INTO trainers (specialization, user_id)
VALUES ((select id from training_types where training_type_name = 'box'),
        (select u.id from users u where u.username = 'Merab.Dvalishvili'));
INSERT INTO trainers (specialization, user_id)
VALUES ((select id from training_types where training_type_name = 'dance'),
        (select u.id from users u where u.username = 'Salome.Chachua'));
INSERT INTO trainers (specialization, user_id)
VALUES ((select id from training_types where training_type_name = 'mma'),
        (select u.id from users u where u.username = 'Ilia.Topuria'));



INSERT INTO trainings (training_duration, training_date, training_name, trainee_id, trainer_id, training_type_id)
VALUES (90,
        '1999-11-05',
        'Marathon Prep',
        (select t.id from trainees t where date_of_birth = '2024-09-20'),
        (select id from trainers where user_id = (select id from users where username = 'Merab.Dvalishvili')),
        (select t.id from training_types t where training_type_name = 'box'));
INSERT INTO trainings (training_duration, training_date, training_name, trainee_id, trainer_id, training_type_id)
VALUES (60,
        '2010-12-15',
        'Yoga Basics',
        (select t.id from trainees t where date_of_birth = '2004-03-01'),
        (select id from trainers where user_id = (select id from users where username = 'Salome.Chachua')),
        (select t.id from training_types t where training_type_name = 'dance'));
INSERT INTO trainings (training_duration, training_date, training_name, trainee_id, trainer_id, training_type_id)
VALUES (75,
        '2020-04-10',
        'Weightlifting 101',
        (select t.id from trainees t where date_of_birth = '2000-06-17'),
        (select id from trainers where user_id = (select id from users where username = 'Ilia.Topuria')),
        (select t.id from training_types t where training_type_name = 'mma'));
INSERT INTO trainings (training_duration, training_date, training_name, trainee_id, trainer_id, training_type_id)
VALUES (60,
        '2024-08-12',
        'Interval Training',
        (select t.id from trainees t where date_of_birth = '1999-12-23'),
        (select id from trainers where user_id = (select id from users where username = 'Merab.Dvalishvili')),
        (select t.id from training_types t where training_type_name = 'box'));
