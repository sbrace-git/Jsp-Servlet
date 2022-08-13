create table t_message
(
    id    int          not null auto_increment primary key,
    name  char(20)     not null,
    email char(40),
    msg   varchar(256) not null
);