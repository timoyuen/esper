CREATE TABLE IF NOT EXISTS person(
    id varchar(30) primary key,
    password char(50) not null,
    telephone char(15) not null,
    email char(30) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS stock_code(
    code char(10) not null,
    name varchar(20) not null,
    create_time timestamp
)ENGINE=InnoDB DEFAULT CHARSET=utf8;