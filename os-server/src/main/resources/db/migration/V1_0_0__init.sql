USE `open-school-db`;

CREATE TABLE `test_table`
(
    `id`       int auto_increment,
    `name`     varchar(40) null,
    `surname`  varchar(40) null,
    `email`    varchar(40) null,
    `password` varchar(20) null,
    `contact`  varchar(40) null,
    constraint test_table_uindex
        unique (id)
);