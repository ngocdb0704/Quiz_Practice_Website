USE [master]
GO

/****** Object:  Database [Quiz_Practice]    Script Date: 18/05/2024 12:37:26 CH ******/
DROP DATABASE IF EXISTS [Quiz_Practice]
GO

/****** Object:  Database [Quiz_Practice]    Script Date: 18/05/2024 12:37:26 CH ******/
CREATE DATABASE [Quiz_Practice]
GO

ALTER DATABASE [Quiz_Practice] SET ANSI_NULL_DEFAULT OFF 
GO

ALTER DATABASE [Quiz_Practice] SET ANSI_NULLS OFF 
GO

ALTER DATABASE [Quiz_Practice] SET ANSI_PADDING OFF 
GO

ALTER DATABASE [Quiz_Practice] SET ANSI_WARNINGS OFF 
GO

ALTER DATABASE [Quiz_Practice] SET ARITHABORT OFF 
GO

ALTER DATABASE [Quiz_Practice] SET AUTO_CLOSE OFF 
GO

ALTER DATABASE [Quiz_Practice] SET AUTO_SHRINK OFF 
GO

ALTER DATABASE [Quiz_Practice] SET AUTO_UPDATE_STATISTICS ON 
GO

ALTER DATABASE [Quiz_Practice] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO

ALTER DATABASE [Quiz_Practice] SET CURSOR_DEFAULT  GLOBAL 
GO

ALTER DATABASE [Quiz_Practice] SET CONCAT_NULL_YIELDS_NULL OFF 
GO

ALTER DATABASE [Quiz_Practice] SET NUMERIC_ROUNDABORT OFF 
GO

ALTER DATABASE [Quiz_Practice] SET QUOTED_IDENTIFIER OFF 
GO

ALTER DATABASE [Quiz_Practice] SET RECURSIVE_TRIGGERS OFF 
GO

ALTER DATABASE [Quiz_Practice] SET  DISABLE_BROKER 
GO

ALTER DATABASE [Quiz_Practice] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO

ALTER DATABASE [Quiz_Practice] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO

ALTER DATABASE [Quiz_Practice] SET TRUSTWORTHY OFF 
GO

ALTER DATABASE [Quiz_Practice] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO

ALTER DATABASE [Quiz_Practice] SET PARAMETERIZATION SIMPLE 
GO

ALTER DATABASE [Quiz_Practice] SET READ_COMMITTED_SNAPSHOT OFF 
GO

ALTER DATABASE [Quiz_Practice] SET HONOR_BROKER_PRIORITY OFF 
GO

ALTER DATABASE [Quiz_Practice] SET RECOVERY FULL 
GO

ALTER DATABASE [Quiz_Practice] SET  MULTI_USER 
GO

ALTER DATABASE [Quiz_Practice] SET PAGE_VERIFY CHECKSUM  
GO

ALTER DATABASE [Quiz_Practice] SET DB_CHAINING OFF 
GO

ALTER DATABASE [Quiz_Practice] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO

ALTER DATABASE [Quiz_Practice] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO

ALTER DATABASE [Quiz_Practice] SET DELAYED_DURABILITY = DISABLED 
GO

ALTER DATABASE [Quiz_Practice] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO

ALTER DATABASE [Quiz_Practice] SET QUERY_STORE = ON
GO

ALTER DATABASE [Quiz_Practice] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO

ALTER DATABASE [Quiz_Practice] SET  READ_WRITE 
GO

USE [Quiz_Practice]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON

Go

CREATE TABLE [dbo].[Gender](
	[GenderId] [int] IDENTITY(1,1) primary key ,
	[GenderName] [varchar](50))
GO

 

CREATE TABLE [dbo].[Role](
	[RoleId] [int] IDENTITY(1,1) primary key,
	[RoleName] [varchar](50))
GO



CREATE TABLE [dbo].[User](
	[UserId] [int] IDENTITY(1,1) primary key,
	[Email] [varchar](50) UNIQUE,
	[Password] [varchar](50),
	[RoleId] [int]  foreign key references [dbo].[Role](RoleId),
	[FullName] [nvarchar](50),
	[GenderId] [int] foreign key references [dbo].[Gender](GenderId),
	[Mobile] [varchar](50) UNIQUE,
	[IsActive] [bit])
GO



CREATE TABLE [dbo].[ProfilePicture](
	[UserId] [int] primary key foreign key references [dbo].[User](UserId),
	[Image] [varbinary](max))
GO

CREATE TABLE [dbo].[ResetToken](
	[UserId] [int] primary key foreign key references [dbo].[User](UserId),
	[Token] [varchar](255),
	[ValidTo] [datetime])
GO



CREATE TABLE [dbo].[SubjectCategory](
	[SubjectCategoryId] [int] IDENTITY(1,1) primary key,
	[SubjectCategoryName] [varchar](50),
	[SubjectParentCategory] int
	)
GO


CREATE TABLE [dbo].[SubjectLevel](
	[SubjectLevelId] [int] IDENTITY(1,1) primary key,
	[SubjectLevelName] [varchar](50))
GO

CREATE TABLE [dbo].[Organization](
	[OrganizationId] [int] IDENTITY(1,1) primary key,
	[OrganizationName] [varchar](50),
	[OrganizationEmail] [varchar](50) UNIQUE,
	[OrganizationSize] [int],
	[OrganizationCountry] [varchar] (50),
	[IsNonprofit] [bit])
GO

CREATE TABLE [dbo].[OrganizationMember](
	[Id] [int] IDENTITY(1,1) primary key,
	[OrganizationId] [int]  foreign key references [dbo].[Organization](OrganizationId),
	[MemberId] [int] foreign key references  [dbo].[User](UserId),
	[JobTitle] [varchar](50),
	[IsActive] [bit])
GO


CREATE TABLE [dbo].[Subject](
	[SubjectId] [int] IDENTITY(1,1) primary key,
	[SubjectTitle] [varchar](50),
	[SubjectProviderId] [int] foreign key references [dbo].[Organization](OrganizationId),
	[SubjectCategoryId] [int] foreign key references [dbo].[SubjectCategory](SubjectCategoryId),
	[SubjectStatus] [bit],
	[SubjectLevelId] [int] foreign key references [dbo].[SubjectLevel](SubjectLevelId),
	[IsFeaturedSubject] [bit],
	[SubjectCreatedDate] [date],
	[SubjectUpdatedDate] [date],
	[SubjectTagLine] [varchar](50),
	[SubjectBriefInfo] [varchar](300),
	[SubjectDescription] [ntext],
	[SubjectThumbnail] [varchar](255),
	[SubjectOwnerId] [int] foreign key references [dbo].[User](UserId),
	[MarkedForPublication] [bit])
GO



CREATE TABLE [dbo].[Package](
	[PackageId] [int] IDENTITY(1,1) primary key,
	[SubjectId] [int] foreign key references [dbo].[Subject](SubjectId),
	[PackageName] [nvarchar](50),
	[PackageDuration] [int],
	[ListPrice] [float],
	[SalePrice] [float],
	[Status] [bit])
GO




--To prevent breaking old code, i added a new table instead
CREATE TABLE [dbo].[PricePackageDesc] (
	[PackageId] [int] foreign key references [dbo].[Package]([PackageId]) on delete cascade,
	[Desc] [nvarchar](2048)
)



GO
CREATE TABLE [dbo].[Dimension](
	[DimensionId] [int] IDENTITY(1,1) primary key,
	[SubjectId] [int] foreign key references [dbo].[Subject](SubjectId),
	[DimensionType] [nvarchar](50),
	[DimensionName] [nvarchar](50),
	[DimensionDescription] [nvarchar](100))






GO



CREATE TABLE [dbo].[OrganizationPackage](
	[OrganizationPackageId] [int] IDENTITY(1,1) primary key,
	[PackageName] [nvarchar](50),
	[PackageDuration] [int],
	[RetailPriceEach] [float],
	[WholesalePriceEach] [float],
	[NonprofitPriceEach] [float],
	[Status] [bit])
GO

CREATE TABLE [dbo].[License](
	[LicenseId] [int] IDENTITY(1,1) primary key,
	[OrganizationId] [int] foreign key references [dbo].[Organization](OrganizationId),
	[OrganizationPackageId] [int] foreign key references [dbo].[OrganizationPackage] (OrganizationPackageId),
	[SalePrice] [float],
	[ValidFrom] [date],
	[ValidTo] [date],
	[Size] [int],
	[Status] [bit])
GO

CREATE TABLE [dbo].[OrganizationPackageSubject](
	[Id] [int] IDENTITY(1,1) primary key,
	[OrganizationPackageId] [int] foreign key references [dbo].[OrganizationPackage] (OrganizationPackageId),
	[SubjectId] [int] foreign key references [dbo].[Subject](SubjectId)
)
GO



CREATE TABLE [dbo].[RegistrationStatus](
	[RegistrationStatusId] [int] IDENTITY(1,1) primary key,
	[RegistrationStatusName] [varchar](50))
GO

CREATE TABLE [dbo].[Registration](
	[RegistrationId] [int] IDENTITY(1,1) primary key,
	[UserId] [int] foreign key references [dbo].[User](UserId),
	[RegistrationTime] [date],
	[PackageId] [int] foreign key references [dbo].[Package](PackageId),
	[RegistrationStatusId] [int] foreign key references [dbo].[RegistrationStatus](RegistrationStatusId),
	[ValidFrom] [date],
	[ValidTo] [date],
	[TransactionContent] [varchar](255),
	[TransactionCode] [varchar](255),
	[TransactionAccount] [varchar](255))
Go

CREATE TABLE [dbo].[BlogCategory](
	[BlogCategoryId] [int] IDENTITY(1,1) primary key,
	[BlogCategoryName] [varchar](50))
Go

CREATE TABLE [dbo].[Blog](
	[BlogId] [int] IDENTITY(1,1) primary key,
	[UserId] [int] foreign key references [dbo].[User](UserId),
	[BlogCategoryId] [int] foreign key references [dbo].[BlogCategory](BlogCategoryId),
	[BlogTitle] [nvarchar](512),
	[UpdatedTime] [datetime],
	[PostBrief] [nvarchar](2048),
	[PostText] [ntext])
GO

CREATE TABLE [dbo].[Question](
	[QuestionID] [int] IDENTITY(1,1) primary key,
	[QuestionText] [text],
	[Explanation] [text],
	[Level] [int],
	[SubjectID] [int] foreign key references [dbo].[Subject](SubjectId),
	[LessonID] [int],
	[Status] bit
)
GO

CREATE TABLE [dbo].[Answer](
	[AnswerID] [int] IDENTITY(1,1) primary key,
	[QuestionID] [int] foreign key references [dbo].[Question](QuestionID),
	[AnswerName] [text],
	[IsCorrect] [bit])

	GO
CREATE TABLE [dbo].[slide](
	[slide_id] [int] IDENTITY(1,1) NOT NULL,
	[title] [nvarchar](50) NULL,
	[img] [ntext] NULL,
	[backlink] [ntext] NULL,
	[author_id] [int] NOT NULL,
	[description] [ntext] NULL,
	[active] [bit] NULL)

	GO

CREATE TABLE [dbo].[Quiz] (
	[QuizId] [int] IDENTITY(1, 1) primary key,
	[SubjectId] [int] not null foreign key references [dbo].[Subject](SubjectId),
	[QuizName] nvarchar(255) default(N''),
	[Level] char(10) check([Level] in (0, 1, 2)) default(0), --easy, medium, hard
	[DurationInMinutes] int check([DurationInMinutes] > 0) default(60),
	[PassRate] int check(0 <= [PassRate] and [PassRate] <= 100) default(50),
	[QuizType] char(10) check([QuizType] in (0, 1)) default(0), --simulation, lesson-quiz
	[IsPublished] bit,
	[UpdatedTime] [datetime] default(CURRENT_TIMESTAMP),
	[NumberOfAttempts] [int] NULL,
	[Description] [nvarchar](max),
	[TotalQuestion] int
)
GO

CREATE TABLE QuestionQuiz (
    QuizId INT NOT NULL,
    QuestionId INT NOT NULL,
    PRIMARY KEY (QuizId, QuestionId),
    FOREIGN KEY (QuizId) REFERENCES Quiz(QuizId),
    FOREIGN KEY (QuestionId) REFERENCES Question(QuestionId)
);

CREATE TABLE QuizLessonQuestionCount (
    QuizId INT NOT NULL,
    LessonId INT NOT NULL,
    QuestionCount INT NOT NULL,
    PRIMARY KEY (QuizId, LessonId),
    FOREIGN KEY (QuizId) REFERENCES Quiz(QuizId)
);

CREATE TABLE [dbo].[QuizQuestion] (
	[QuizId] [int] foreign key references [dbo].[Quiz]([QuizId]) on delete cascade,
	[QuestionId] [int] foreign key references [dbo].[Question]([QuestionId])
	PRIMARY KEY([QuizId], [QuestionId])
)

CREATE TABLE [dbo].[Attempt] (
	[AttemptId] int primary key identity(1, 1),
	[QuizId] int not null foreign key references [dbo].[Quiz]([QuizId]),
	[UserId] int not null foreign key references [dbo].[User]([UserId]),
	[CorrectCount] int check([CorrectCount] >= 0) default(0),
	[DueDate] datetime not null
);

CREATE UNIQUE INDEX ans_uniq on [dbo].[Answer]([QuestionID], [AnswerID])

CREATE TABLE [dbo].[AttemptQuestionAnswer] (
	[AttemptId] int foreign key references [dbo].[Attempt]([AttemptId]) on delete cascade,
	[QuestionId] int not null,
	[AnswerId] int,
	[Marked] bit default(0),
	FOREIGN KEY ([QuestionId], [AnswerId]) REFERENCES [dbo].[Answer]([QuestionID], [AnswerID])
);
