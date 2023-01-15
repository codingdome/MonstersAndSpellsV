create table users (
                       username varchar(255) not null,
                       password varchar(255) not null,
                       name varchar(255),
                       bio varchar(255),
                       img varchar(255),
                       token varchar(255)
);

create table stats (
                       username varchar(255) not null,
                       name varchar(255),
                       elo int,
                       coins int,
                       won int,
                       lost int
);

create table packages (
                          id int not null,
                          card_id_1 varchar(255),
                          card_id_2 varchar(255),
                          card_id_3 varchar(255),
                          card_id_4 varchar(255),
                          card_id_5 varchar(255)
);

create table cards (
                       id varchar(255) not null,
                       name varchar(255),
                       username varchar(255),
                       damage numeric(5,1),
                       deck int,
                       trade int
);

create table battles (
                         username varchar(255),
                         token varchar(255)
);

create table trades (
                        username varchar(255),
                        id varchar(255),
                        card_to_trade varchar(255),
                        type varchar(255),
                        minimum_damage numeric(5,1)
);