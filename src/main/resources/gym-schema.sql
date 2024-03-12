create table if not exists training_types
(
    training_type_id   bigint auto_increment
    primary key,
    training_type_name varchar(255) not null,
    constraint UK_8oq1i4b9q9bxv4p6tw05na3lq
    unique (training_type_name)
    );

create table if not exists users
(
    user_id    bigint auto_increment
    primary key,
    first_name varchar(255) not null,
    is_active  bit          not null,
    last_name  varchar(255) not null,
    password   varchar(255) not null,
    username   varchar(255) not null,
    user_name  varchar(255) not null,
    constraint UK_k8d0f2n7n88w1a16yhua64onx
    unique (username)
    );

create table if not exists trainees
(
    trainee_id    bigint auto_increment
    primary key,
    address       varchar(255) null,
    date_of_birth date         null,
    user_id       bigint       null,
    constraint UK_6yjkrphplllspvtco5gaax03b
    unique (user_id),
    constraint FKcy6e52861i61gaajadiq70lnt
    foreign key (user_id) references users (user_id)
    );

create table if not exists trainers
(
    trainer_id     bigint auto_increment
    primary key,
    specialization bigint null,
    user_id        bigint null,
    constraint UK_se8qmhomor3guutjui5wfmrk3
    unique (user_id),
    constraint FKkeus6jdvnbu28mi7xi7ffarh
    foreign key (specialization) references training_types (training_type_id),
    constraint FKmkxcvfr0uu3pwv772aurye5w7
    foreign key (user_id) references users (user_id)
    );

create table if not exists trainee_trainer
(
    trainee_id bigint not null,
    trainer_id bigint not null,
    primary key (trainee_id, trainer_id),
    constraint FK27f1le516f824l8owvnc4mvs1
    foreign key (trainee_id) references trainees (trainee_id),
    constraint FKsis342w57b177o84peanydlrp
    foreign key (trainer_id) references trainers (trainer_id)
    );

create table if not exists trainings
(
    training_id      bigint auto_increment
    primary key,
    duration         varbinary(255) null,
    training_date    date           null,
    training_name    varchar(255)   null,
    trainee_id       bigint         null,
    trainer_id       bigint         null,
    training_type_id bigint         null,
    constraint FK4btmw0yu4tbogiw8qi0ewba74
    foreign key (trainee_id) references trainees (trainee_id),
    constraint FKbqwj5wievnuonuxv60wg6rr48
    foreign key (trainer_id) references trainers (trainer_id),
    constraint FKovn6ewpej7cxhjx55tovddhnj
    foreign key (training_type_id) references training_types (training_type_id)
    );


INSERT INTO gym.users (user_id, first_name, is_active, last_name, password, username, user_name) VALUES (1, 'Davit', true, 'Maisuradze', 'newPass', 'Davit.Maisuradze', '');
INSERT INTO gym.users (user_id, first_name, is_active, last_name, password, username, user_name) VALUES (2, 'Mariam', true, 'Katamashvili', 'marimagaria', 'Mariam.Katamashvili', '');
INSERT INTO gym.users (user_id, first_name, is_active, last_name, password, username, user_name) VALUES (3, 'Mariam', true, 'Katamashvili', 'hA5`2S!H!X', 'Mariam.Katamashvili1', '');
INSERT INTO gym.users (user_id, first_name, is_active, last_name, password, username, user_name) VALUES (5, 'Mariam', true, 'Katamashvili', '42~bx~NW?/', 'Mariam.Katamashvili3', '');
INSERT INTO gym.users (user_id, first_name, is_active, last_name, password, username, user_name) VALUES (8, 'Davit', true, 'Maisuradze', 'l_7A}%Gkcm', 'Davit.Maisuradze1', '');


INSERT INTO gym.training_types (training_type_id, training_type_name) VALUES (5, 'ballet');
INSERT INTO gym.training_types (training_type_id, training_type_name) VALUES (1, 'box');
INSERT INTO gym.training_types (training_type_id, training_type_name) VALUES (4, 'cardio');
INSERT INTO gym.training_types (training_type_id, training_type_name) VALUES (3, 'dance');
INSERT INTO gym.training_types (training_type_id, training_type_name) VALUES (2, 'yoga');

INSERT INTO gym.trainers (trainer_id, specialization, user_id) VALUES (1, 1, 1);
INSERT INTO gym.trainers (trainer_id, specialization, user_id) VALUES (2, 2, 2);
INSERT INTO gym.trainers (trainer_id, specialization, user_id) VALUES (3, 4, 3);

INSERT INTO gym.trainees (trainee_id, address, date_of_birth, user_id) VALUES (1, 'kiu', '2004-09-20', 1);
INSERT INTO gym.trainees (trainee_id, address, date_of_birth, user_id) VALUES (3, 'Tbilisi', '2013-03-08', 2);
INSERT INTO gym.trainees (trainee_id, address, date_of_birth, user_id) VALUES (4, 'Batumi', '2007-07-18', 3);
INSERT INTO gym.trainees (trainee_id, address, date_of_birth, user_id) VALUES (5, 'Kutaisi', '2000-08-17', 5);

INSERT INTO gym.trainee_trainer (trainee_id, trainer_id) VALUES (1, 3);

