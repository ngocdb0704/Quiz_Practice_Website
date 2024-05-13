use master;

drop database if exists QuizPractice;
create database QuizPractice;
go

use QuizPractice;
go

create table Users (
	id int identity(1, 1),
	email varchar(128),
	[password] varchar(99),
	fullName nvarchar(255),
	gender varchar(255),
	mobile varchar(25)
);