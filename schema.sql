create database market default charset='utf8';
create user market@'%' identified by 'm@rket';
grant all on market.* to market@'%';

use market;

create table post (
  code    serial,
  topic   varchar(800),
  detail  varchar(4000),
  member  bigint default 0,
  status  varchar(100) default 'active',
  updated timestamp
);
-- add photo
-- change time to utc

insert into post(topic, detail)
values('Books for sales', 'Many books for sale in Bangkok');

insert into post(topic, detail)
values('Round Coffee Table', 
'Glass table with wood legs. Ideal for living room.');

create table member(
  code     serial,
  email    varchar(200),
  password varchar(128),
  name     varchar(200)
);
insert into member(email, name, password)
values('user@market.com', 'The Administrator', sha2('user2017', 512));
