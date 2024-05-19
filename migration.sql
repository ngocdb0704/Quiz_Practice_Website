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
GO
CREATE TABLE [dbo].[User](
	[UserId] [int] NOT NULL primary key,
	[Email] [varchar](255) NOT NULL,
	[Password] [varchar](99) NOT NULL,
	[Role] [varchar](10) NOT NULL,
	[FullName] [nvarchar](255) NOT NULL,
	[Gender] [varchar](255),
	[Mobile] [varchar](25) NOT NULL,
	[IsActive] [bit] NOT NULL)

GO
CREATE TABLE [dbo].[ProfilePicture](
	[UserId] [int] NOT NULL primary key,
	[Image] [varbinary] (MAX),
	constraint [UserId] foreign key ([UserId]) references [dbo].[User](UserId))

GO
CREATE TABLE [dbo].[ResetToken](
	[UserId] [int] primary key foreign key references [dbo].[User](UserId),
	[Token] [varchar] (255),
	[ValidTo] [datetime])

GO
CREATE TABLE [dbo].[Subject](
	[SubjectId] [int] NOT NULL primary key,
	[SubjectName] [varchar] (25)NOT NULL,
	[SubjectCategory] [varchar] (25) NOT NULL
)

GO
CREATE TABLE [dbo].[Package](
	[PackageId] [int] NOT NULL primary key,
	[PackageName] [varchar] (25)NOT NULL,
	[PackagePrice] [float] NOT NULL
)
GO
CREATE TABLE [dbo].[Registration](
	[RegistrationId] [int] NOT NULL primary key,
	[UserId] [int] NOT NULL foreign key references [dbo].[User](UserId),
	[SubjectId] [int] NOT NULL foreign key references [dbo].[Subject](SubjectId),
	[RegistrationTime] [date] NOT NULL,
	[PackageId] [int] NOT NULL foreign key references [dbo].[Package](PackageId),
	[TotalCost] [float] NOT NULL,
	[Status] [varchar](25),
	[ValidFrom] [date],
	[ValidTo] [date]
)