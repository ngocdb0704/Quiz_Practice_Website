use master;

drop database if exists QuizPractice;
create database QuizPractice;
go

use QuizPractice;
go

create table Users (
	id int identity(1, 1) primary key,
	email varchar(128) unique,
	[password] varchar(99),
	fullName nvarchar(255),
	gender varchar(255),
	mobile varchar(25)
);

create table ResetTokens (
	userId int foreign key references Users(id),
	token varchar(255),
	validTo datetime
);

insert into Users (email) values ('me@gmail.com');

select * from ResetTokens;