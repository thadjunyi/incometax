drop table TAX if exists;
drop table DONATION if exists;
drop table SRS if exists;

create table TAX (ID integer identity primary key, FROM varchar(10) not null, TO varchar(10) not null, RATE varchar(10) not null, unique(FROM, TO));
create table DONATION (ID integer identity primary key, RATE varchar(10) not null);
create table SRS (ID integer identity primary key, RATE varchar(10) not null);
