create table users
(
    id    bigint auto_increment primary key,
    email varchar(100) not null unique,
    name  varchar(50) not null
);
