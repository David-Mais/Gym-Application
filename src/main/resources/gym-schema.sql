CREATE DATABASE IF NOT EXISTS gym;
USE gym;

create table if not exists training_types
(
    id   bigint auto_increment primary key,
    training_type_name varchar(255) not null,
    constraint UK_8oq1i4b9q9bxv4p6tw05na3lq
    unique (training_type_name)
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
    FOREIGN KEY (id) REFERENCES users (id)
);

CREATE TABLE trainers
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    specialization       BIGINT,
    FOREIGN KEY (specialization) REFERENCES training_types (id)
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


INSERT INTO gym.users (id, first_name, is_active, last_name, password, username) VALUES (1, 'Davit', true, 'Maisuradze', 'newPass', 'Davit.Maisuradze');
INSERT INTO gym.users (id, first_name, is_active, last_name, password, username) VALUES (2, 'Mariam', true, 'Katamashvili', 'marimagaria', 'Mariam.Katamashvili');
INSERT INTO gym.users (id, first_name, is_active, last_name, password, username) VALUES (3, 'Mariam', true, 'Katamashvili', 'hA5`2S!H!X', 'Mariam.Katamashvili1');
INSERT INTO gym.users (id, first_name, is_active, last_name, password, username) VALUES (5, 'Mariam', true, 'Katamashvili', '42~bx~NW?/', 'Mariam.Katamashvili3');
INSERT INTO gym.users (id, first_name, is_active, last_name, password, username) VALUES (8, 'Davit', true, 'Maisuradze', 'l_7A}%Gkcm', 'Davit.Maisuradze1');


INSERT INTO gym.training_types (id, training_type_name) VALUES (5, 'ballet');
INSERT INTO gym.training_types (id, training_type_name) VALUES (1, 'box');
INSERT INTO gym.training_types (id, training_type_name) VALUES (4, 'cardio');
INSERT INTO gym.training_types (id, training_type_name) VALUES (3, 'dance');
INSERT INTO gym.training_types (id, training_type_name) VALUES (2, 'yoga');

INSERT INTO gym.trainers (trainer_id, specialization, id) VALUES (1, 1, 1);
INSERT INTO gym.trainers (trainer_id, specialization, id) VALUES (2, 2, 2);
INSERT INTO gym.trainers (trainer_id, specialization, id) VALUES (3, 4, 3);

INSERT INTO gym.trainees (trainee_id, address, date_of_birth, id) VALUES (1, 'kiu', '2004-09-20', 1);
INSERT INTO gym.trainees (trainee_id, address, date_of_birth, id) VALUES (3, 'Tbilisi', '2013-03-08', 2);
INSERT INTO gym.trainees (trainee_id, address, date_of_birth, id) VALUES (4, 'Batumi', '2007-07-18', 3);
INSERT INTO gym.trainees (trainee_id, address, date_of_birth, id) VALUES (5, 'Kutaisi', '2000-08-17', 5);