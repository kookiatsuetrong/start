create database start default character set 'UTF8';
create user me identified by 'password';
grant all on start.* to me;

use start;

create table users
(
	number        int unique not null auto_increment,
	email         varchar(200) unique not null,
	password      varchar(200) not null,
	first_name    varchar(200) not null,
	last_name     varchar(200) not null,
	type          varchar(200) not null default 'user',
	status        varchar(200) default '',
	detail        varchar(200) default '',
	signature     varchar(200) default '',
	account       varchar(200) default ''
);
alter table users auto_increment = 1048576;

insert into users(email, password, first_name, last_name, type)
values('user@email.com', sha2('User1234', 512),
'System', 'Administrator', 'administrator');

