CREATE TABLE IF not EXISTS passenger(
 id bigint not null AUTO_INCREMENT,
 name varchar(255) not null,
 age smallint not null,
 PRIMARY KEY (id)
);


CREATE TABLE IF not EXISTS  ticket (
    id bigint not null AUTO_INCREMENT,
    departure_date date not null,
    departure_time time not null,
    arrival_date date not null,
    arrival_time time not null,
    src_city varchar(50),
    trg_city varchar(50),
    passenger_id bigint not null,
    PRIMARY KEY (id),
    CONSTRAINT fk_passenger FOREIGN KEY (passenger_id)
    REFERENCES passenger(id)
);