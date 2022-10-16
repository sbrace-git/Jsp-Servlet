create table image
(
    id          int          not null auto_increment,
    file_name   varchar(255) not null,
    create_time date         not null,
    bytes       blob         not null,
    primary key (id)
);
create table T_ACCOUNT
(
    NAME     CHARACTER VARYING(15) not null
        primary key,
    PASSWORD CHARACTER VARYING(32) not null
);
create table t_account_role
(
    name varchar(15) not null,
    role varchar(15) not null,
    primary key (name, role)
);