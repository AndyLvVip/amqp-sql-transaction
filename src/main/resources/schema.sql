create database amqp_sql_tx;
use amqp_sql_tx;
drop table amqp_sql_tx;
create table amqp_sql_tx (
id char(36) not null,
primary key (id)
) engine = Innodb, charset = utf8mb4;

show full columns from amqp_sql_tx;