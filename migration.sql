use master;

drop database if exists QuizPractice;
create database QuizPractice;
go

use QuizPractice;
go

CREATE TABLE [Users] (
  [Id] INT PRIMARY KEY IDENTITY(1, 1),
  [Email] VARCHAR(255) UNIQUE,
  [Password] VARCHAR(99),
  [Role] VARCHAR(10) CHECK([Role] in ('Admin', 'Expert', 'Customer', 'Marketing', 'Sale')),
  [FullName] NVARCHAR(255),
  [Gender] VARCHAR(255) CHECK ([Gender] IN ('Male', 'Female')),
  [Mobile] VARCHAR(25),
  [IsActive] BIT
  --According to the requirements
  --The system allows the user to register courses without registering first! See function #12
  --So my solution is to create a 'temporary user', this type of user will have IsActive set to 0
  --Login system must not login this type of user, please only allow users with IsActive set to 1 to login
);

--Profile Pic depends on the user existing, so foreign key constraint is used
CREATE TABLE [ProfilePic] (
  [UserId] INT UNIQUE FOREIGN KEY REFERENCES Users(Id) ON DELETE CASCADE,
  [Image] VARCHAR(8000),
);

--Reset token depends on the user existing, so foreign key constraint is used
CREATE TABLE [ResetTokens] (
	[UserId] INT UNIQUE FOREIGN KEY REFERENCES Users(Id) ON DELETE CASCADE,
	[Token] VARCHAR(255),
	[ValidTo] DATETIME,
);

CREATE TABLE [Subjects] (
	[Id] INT PRIMARY KEY IDENTITY(1, 1),
	[Name] NVARCHAR(255) NOT NULL,
	[FeaturedSubject] BIT,
	[Owner] INT FOREIGN KEY REFERENCES Users(Id) ON DELETE SET NULL,
	[Status] VARCHAR(20) CHECK ([Status] IN ('Published', 'Unpublished')),
	[Description] NVARCHAR(2048) DEFAULT ''
);

CREATE TABLE [PricePackages] (
	[Id] INT PRIMARY KEY,
	[Name] NVARCHAR(255) NOT NULL,
	[IsActive] BIT NOT NULL,
	[ListPrice] INT CHECK([ListPrice] >= 0) NOT NULL,
	[SalePrice] INT CHECK([SalePrice] >= 0) NOT NULL,
	[Duration] INT CHECK([Duration] >= 1) NOT NULL,
	[SubjectId] INT FOREIGN KEY REFERENCES Subjects(Id) ON DELETE CASCADE,
	--We cannot have two packages with the exact same duration for a single subject
	--or it will be very weird. Please check this in your application code
);

--Registration cannot exist without the person who is registering (a user, temporary or not),
--and a subject that is being registered.
CREATE TABLE [Registration] (
	[UserId] INT FOREIGN KEY REFERENCES Users(Id) ON DELETE CASCADE,
	[PackageId] INT FOREIGN KEY REFERENCES PricePackages(Id) ON DELETE CASCADE,
	[RegistrationTime] DATE NOT NULL,
	[Status] VARCHAR(20) CHECK ([Status] IN ('Submitted', 'Cancelled', 'Paid')),
	PRIMARY KEY([UserId], [PackageId])
);

CREATE TABLE [BlogCategories] (
	[Id] INT PRIMARY KEY IDENTITY(1, 1),
	[CategoryName] NVARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE [Blogs] (
	[Title] NVARCHAR(512),
	[AuthorId] INT REFERENCES Users(Id) ON DELETE SET NULL,
	[UpdatedDate] DATETIME DEFAULT(CURRENT_TIMESTAMP),
	[CategoryId] INT FOREIGN KEY REFERENCES BlogCategories(Id) ON DELETE SET NULL,
	[PostText] NTEXT,
);