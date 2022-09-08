create table image
(
    id          int          not null auto_increment,
    file_name   varchar(255) not null,
    create_time date         not null,
    bytes       blob         not null,
    primary key (id)
)