create table T_ACCOUNT
(
    NAME     CHARACTER VARYING(15)  not null
        primary key,
    EMAIL    CHARACTER VARYING(128) not null,
    PASSWORD CHARACTER VARYING(32)  not null,
    SALT     CHARACTER VARYING(256) not null
);

create table T_MESSAGE
(
    USERNAME CHARACTER VARYING(15)  not null
        references T_ACCOUNT,
    MILLIS   BIGINT                 not null,
    BLABLA   CHARACTER VARYING(512) not null
);
