drop table if exists team cascade;
create table team (
    id bigint not null primary key,
    name varchar(255)
);

drop table if exists member cascade;
create table member (
    id varchar(255) not null primary key,
    age integer not null,
    name varchar(255),
    password varchar(255),
    salary integer not null,
    team_id bigint,
    foreign key (team_id) references team
);