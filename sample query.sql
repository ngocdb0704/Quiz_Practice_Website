DROP TABLE [dbo].[QuizQuestion];
DROP TABLE [dbo].[Quiz];
DROP TABLE [dbo].[Answer];
DROP TABLE [dbo].[Question];
DROP TABLE [dbo].[Blog];
DROP TABLE [dbo].[BlogCategory];
DROP TABLE [dbo].[Registration];
DROP TABLE [dbo].[RegistrationStatus];
DROP TABLE [dbo].[Package];
DROP TABLE [dbo].[Subject];
DROP TABLE [dbo].[SubjectCategory];
DROP TABLE [dbo].[SubjectLevel];
DROP TABLE [dbo].[ResetToken];
DROP TABLE [dbo].[ProfilePicture];
DROP TABLE [dbo].[User];
DROP TABLE [dbo].[Role];
DROP TABLE [dbo].[Gender];


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

CREATE TABLE [dbo].[SubjectLevel](
	[SubjectLevelId] [int] IDENTITY(1,1) primary key,
	[SubjectLevelName] [varchar](50))
GO

CREATE TABLE [dbo].[Subject](
	[SubjectId] [int] IDENTITY(1,1) primary key,
	[SubjectTitle] [varchar](50),
	[SubjectCategoryId] [int] foreign key references [dbo].[SubjectCategory](SubjectCategoryId),
	[SubjectStatus] [bit],
	[SubjectLevelId] [int] foreign key references [dbo].[SubjectLevel](SubjectLevelId),
	[IsFeaturedSubject] [bit],
	[SubjectCreatedDate] [date],
	[SubjectUpdatedDate] [date],
	[SubjectTagLine] [varchar](50),
	[SubjectBriefInfo] [varchar](300),
	[SubjectDescription] [ntext],
	[SubjectThumbnail] [varchar](255))
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
	[PostText] [ntext]
)
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
CREATE TABLE [dbo].[Quiz] (
	[QuizId] [int] IDENTITY(1, 1) primary key,
	[SubjectId] [int] not null foreign key references [dbo].[Subject](SubjectId),
	[QuizName] nvarchar(255) default(N''),
	[Level] char(10) check([Level] in (0, 1, 2)) default(0), --easy, medium, hard
	[DurationInMinutes] int check([DurationInMinutes] > 0) default(60),
	[PassRate] int check(0 <= [PassRate] and [PassRate] <= 100) default(50),
	[QuizType] char(10) check([QuizType] in (0, 1)) default(0), --simulation, lesson-quiz
	[IsPublished] bit,
	[UpdatedTime] [datetime] default(CURRENT_TIMESTAMP)
)
GO
CREATE TABLE [dbo].[QuizQuestion] (
	[QuizId] [int] foreign key references [dbo].[Quiz]([QuizId]) on delete cascade,
	[QuestionId] [int] foreign key references [dbo].[Question]([QuestionId])
	PRIMARY KEY([QuizId], [QuestionId])
)
GO

INSERT INTO [SubjectCategory] VALUES('Natural Science', 0);
INSERT INTO [SubjectCategory] VALUES('Social Science', 0);
INSERT INTO [SubjectCategory] VALUES('Technology & IT', 0);
INSERT INTO [SubjectCategory] VALUES('Mathematics', 1);
INSERT INTO [SubjectCategory] VALUES('Chemistry', 1);
INSERT INTO [SubjectCategory] VALUES('Physics', 1);
INSERT INTO [SubjectCategory] VALUES('Algebra', 4);
INSERT INTO [SubjectCategory] VALUES('Geometry', 4);
INSERT INTO [SubjectCategory] VALUES('Probability and Statistics', 4);
INSERT INTO [SubjectCategory] VALUES('Linear Algebra', 4);
INSERT INTO [SubjectCategory] VALUES('Organic Chemistry', 5);
INSERT INTO [SubjectCategory] VALUES('Inorganic Chemistry', 5);
INSERT INTO [SubjectCategory] VALUES('Biochemistry', 5);
INSERT INTO [SubjectCategory] VALUES('Physical Chemistry', 5);
INSERT INTO [SubjectCategory] VALUES('Quantum Mechanics', 6);
INSERT INTO [SubjectCategory] VALUES('Electromagnetism', 6);
INSERT INTO [SubjectCategory] VALUES('Nuclear Physics', 6);
INSERT INTO [SubjectCategory] VALUES('Optics', 6);
INSERT INTO [SubjectCategory] VALUES('Programming & Development', 3);
INSERT INTO [SubjectCategory] VALUES('Data Science & Analytics', 3);
INSERT INTO [SubjectCategory] VALUES('Cybersecurity', 3);
INSERT INTO [SubjectCategory] VALUES('Web Design & Development', 3);
INSERT INTO [SubjectCategory] VALUES('Front-End Development', 22);
INSERT INTO [SubjectCategory] VALUES('Back-End Development', 22);
INSERT INTO [SubjectCategory] VALUES('Full-Stack Development', 22);
INSERT INTO [SubjectCategory] VALUES('UX Design', 22);
INSERT INTO [SubjectCategory] VALUES('Java', 19);
INSERT INTO [SubjectCategory] VALUES('Python', 19);
INSERT INTO [SubjectCategory] VALUES('C', 19);
INSERT INTO [SubjectCategory] VALUES('C#', 19);
INSERT INTO [SubjectCategory] VALUES('History', 2);
INSERT INTO [SubjectCategory] VALUES('Geography', 2);
INSERT INTO [SubjectCategory] VALUES('World History', 31);
INSERT INTO [SubjectCategory] VALUES('Development Geography', 32);

INSERT INTO [SubjectLevel] VALUES('Beginner');
INSERT INTO [SubjectLevel] VALUES('Intermediate');
INSERT INTO [SubjectLevel] VALUES('Advanced');

INSERT INTO [Subject] VALUES('College Algebra with the Math Sorcerer', 7, 1, 1, 1, '2004-05-01','2004-05-01','nice', 'Mock brief info', '##Coursera style description
		Sample
		The subject description part of the subject details page will parse the description of a subject and display it similar to how Coursera display their subject info
		Note: This section will not be displayed
	##About
		<h3>What you''ll learn:</h3>
		<ul>
		<li>Understanding differential equations and solving first order and first degree differential equations.</li>
		<li>Apply the concept of trigonometry in real world problems.</li>
		<li>Understanding differentiation and integration with related applications.</li>
		<li>Solve a system of equations with the help of matrices.</li>
		</ul>
		<h3>Details to know</h3>
		<span>Assessments: 16 Assessments</span>
	##Outcome
		<h3>Advance your subject-matter expertise
		<ul>
		<li>learn in-demand skills from university and industry experts</li>
		<li>master a subject or tool with hands-on projects</li>
		<li>develop a deep understanding of key concepts</li>
		<li>earn a career certificate from imperial college london</li>
		</ul>
		<h3>Shareable certificate</h3>
	##Lessons
		<h3>There are 3 modules in this course</h3>

		<h4>Module 1</h4>
		<ul>
		<li>L1</li>
		<li>L2</li>
		<li>L3</li>
		</ul>

		<h4>Module 2</h4>
		<ul>
		<li>L1</li>
		<li>L2</li>
		<li>L3</li>
		</ul>

		<h4>Module 3</h4>
		<ul>
		<li>L1</li>
		<li>L2</li>
		<li>L3</li>
		</ul>
	##Duration
		<h3>27 hours to complete</h3>
		<h4>3 weeks at 9 hours a week</h4>
	##End', 'https://thumbs.comidoc.net/750/webp/2463616_13ef_3.webp');
INSERT INTO [Subject] VALUES('Become an Algebra Master', 7, 1, 1, 1, '2004-05-01','2004-05-01','nice', 'Mock brief info', 'Mock description','https://www.tangolearn.com/wp-content/uploads/2022/03/best-online-algebra-courses-1.jpg');
INSERT INTO [Subject] VALUES('US / United States History', 33, 1, 1, 0, '2004-05-01','2004-05-01','nice', 'Mock brief info', 'Mock description','https://higheredprofessor.com/wp-content/uploads/2015/05/How-many-courses-do-university-faculty-teach1.jpg');
INSERT INTO [Subject] VALUES('C Fundamental', 29, 1, 1, 1, '2004-05-01','2004-05-01','nice', 'Mock brief info', 'Mock description','https://www.bostontechmom.com/wp-content/uploads/2019/03/Computer-Science-Class.jpg');
INSERT INTO [Subject] VALUES('The Geography of Globalization', 34, 1, 1, 1,'2004-05-01','2004-05-01','nice', 'Mock brief info', 'Mock description','https://img77.uenicdn.com/image/upload/v1654265992/business/93f45720-1374-4925-8f1a-c50dd53034f4.jpg');
INSERT INTO [Subject] VALUES('Pointers & Advanced C Language',29,1, 3,1,'2004-05-01','2004-05-01','nice', 'Mock brief info', 'Mock description','https://i.ytimg.com/vi/0zuolvgpAaY/maxresdefault.jpg');
INSERT INTO [Subject] VALUES('Geometry Basics to Advanced',8,1, 3, 1,'2004-05-01','2004-05-01','nice', 'Mock brief info', 'Mock description','https://www.venturelessons.com/wp-content/uploads/2020/09/geometry-1128x635.jpg');
INSERT INTO [Subject] VALUES('Probability and Statistics',9,1, 1, 1,'2004-05-01','2004-05-01','nice', 'Mock brief info', 'Mock description','https://cdn01.alison-static.net/courses/1818/alison_courseware_intro_1818.jpg');
INSERT INTO [Subject] VALUES('Linear Algebra',10,1,1,1,'2004-05-01','2004-05-01','nice', 'Mock brief info', 'Mock description','https://i.ytimg.com/vi/bHwB0icYErw/maxresdefault.jpg');
INSERT INTO [Subject] VALUES('React 18 Course 2024 - Learn React JS',23,1,1,1,'2004-05-01','2004-05-01','nice', 'Mock brief info', 'Mock description','https://i.ytimg.com/vi/CXa0f4-dWi4/maxresdefault.jpg');
INSERT INTO [Subject] VALUES('Spring Boot 3, Spring 6 & Hibernate',24,1,1,0,'2004-05-01','2004-05-01','nice', 'Mock brief info', 'Mock description','https://crunchify.com/wp-content/uploads/2018/04/Spring-Boot-Tutorial-by-Crunchify-LLC.jpg');
INSERT INTO [Subject] VALUES('Learn JAVA Programming',27,1,1,0,'2004-05-01','2004-05-01','nice', 'Mock brief info', 'Mock description','https://i.ytimg.com/vi/ZYwHJ1LiKZY/maxresdefault.jpg');
INSERT INTO [Subject] VALUES('The Complete Python Bootcamp',28,1,2,0,'2004-05-01','2004-05-01','nice', 'Mock brief info', 'Mock description','https://i.ytimg.com/vi/wHXWMChMVds/maxresdefault.jpg');
INSERT INTO [Subject] VALUES('New Subject',28,1,2,0,'2024-06-7','2024-06-7','This is nice', 'Mock brief info', 'Mock description','https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('Somehow',29,1,3,0,'2024-06-6','2024-06-7','This is awsome', 'Mock brief info', 'Mock description','https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('Night Call',30,1,3,0,'2024-06-5','2024-06-7','This is incredible', 'Mock brief info', 'Mock description','https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('Inspiration',9,1,2,0,'2024-06-4','2024-06-7','This is amazing', 'Mock brief info', 'Mock description','https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');



INSERT INTO [Subject] VALUES('iWork', 24, 1,2, 0, '2023-08-21', '2024-04-24', 'target plug-and-play e-tailers', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('Customer Relationships', 24, 1,2, 0, '2024-06-07', '2024-06-07', 'visualize robust niches', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('iSCSI', 23, 1,2, 0, '2023-07-10', '2024-05-29', 'redefine e-markets', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('EOI', 24, 1,3, 0, '2023-09-03', '2024-04-02', 'utilize killer web services', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('Sports Marketing', 25, 1,1, 0, '2023-06-26', '2023-06-23', 'redefine sticky vortals', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('SAP EWM', 28, 1,3, 0, '2023-06-18', '2023-07-06', 'aggregate communities', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('FPGA prototyping', 33, 1,2, 0, '2024-02-15', '2023-11-08', 'matrix action-items', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('DNS Administration', 8, 1,2, 0, '2023-08-30', '2023-08-20', 'incubate wireless markets', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('BMI', 9, 1,3, 0, '2024-04-27', '2024-04-07', 'empower e-services', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('DDI Certified', 30, 1,3, 0, '2023-06-24', '2023-12-23', 'envisioneer 24/7 niches', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('GIS Modeling', 27, 1,3, 0, '2024-05-03', '2024-05-31', 'incubate granular solutions', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('XSS', 28, 1,1, 0, '2024-04-09', '2023-09-21', 'target experiences', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('VAT', 27, 1,1, 0, '2023-09-20', '2024-05-15', 'grow infomediaries', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('QNXT', 28, 1,1, 0, '2023-09-30', '2023-09-15', 'productize action-items', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('EEOC', 23, 1,1, 0, '2023-08-01', '2024-04-22', 'reinvent e-tailers', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('Packaging', 30, 1,1, 0, '2024-03-12', '2024-04-20', 'monetize interfaces', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('Medical Equipment', 33, 1,3, 0, '2023-06-21', '2024-04-05', 'embrace infrastructures', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('CTIOS', 29, 1,2, 0, '2023-09-03', '2024-05-26', 'optimize metrics', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('Therapists', 26, 1,2, 0, '2023-09-05', '2024-01-12', 'whiteboard architectures', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('PVM', 24, 1,2, 0, '2024-05-10', '2023-06-21', 'innovatevconvergence', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('ECDL Certification', 23, 1,2, 0, '2024-01-09', '2023-11-19', 'integrate viral e-markets', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('Evidence', 25, 1,2, 0, '2023-11-08', '2024-04-18', 'grow infomediaries', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('HBase', 23, 1,2, 0, '2023-08-29', '2023-10-08', 'matrix ynergies', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('Subversion', 24, 1,2, 0, '2024-01-15', '2023-07-02', 'repurpose eyeballs', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('FCE', 25, 1,2, 0, '2024-03-05', '2024-05-21', 'cultivate supply-chains', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('Design Patterns', 26, 1,1, 0, '2024-01-19', '2024-04-20', 'innovate scalable content', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('AutoCAD Civil 3D', 27, 1,1, 0, '2023-11-22', '2024-06-05', 'morph world-class systems', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('Front-end', 28, 1,2, 0, '2023-06-17', '2023-12-19', 'synthesize models', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('HP Blade', 29, 1,3, 0, '2023-11-24', '2024-04-04', 'reintermediate web-readiness', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('NCover', 30, 1,2, 0, '2024-05-24', '2024-05-17', 'utilize robust convergence', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('FMA', 29, 1,1, 0, '2024-02-21', '2023-12-20', 'utilize user-centric e-markets', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('CQ5', 30, 1,1, 0, '2024-04-21', '2024-03-25', 'repurpose e-markets', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');
INSERT INTO [Subject] VALUES('Euphonium', 33, 1,2, 0, '2024-2-10', '2024-02-10', 'transform interactive deliverables', 'Mock brief info', 'Mock description', 'https://leverageedu.com/blog/wp-content/uploads/2019/11/Science-Stream-Subjects.png');


INSERT INTO [Package] VALUES(1, '6 Month Premium', 6, 20, 5, 1);
INSERT INTO [Package] VALUES(1, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(1, '3 Month Premium', 3, 10, 7, 1);
INSERT INTO [Package] VALUES(2, '6 Month Premium', 6, 20, 5, 1);
INSERT INTO [Package] VALUES(2, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(2, '3 Month Premium', 3, 10, 7, 1);
INSERT INTO [Package] VALUES(3, '6 Month Premium', 6, 20, 5, 1);
INSERT INTO [Package] VALUES(3, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(3, '3 Month Premium', 3, 10, 7, 1);
INSERT INTO [Package] VALUES(4, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(4, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(4, '3 Month Premium', 3, 10, 7, 1);
INSERT INTO [Package] VALUES(5, '6 Month Premium', 6, 20, 5, 1);
INSERT INTO [Package] VALUES(5, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(5, '3 Month Premium', 3, 10, 7, 1);
INSERT INTO [Package] VALUES(6, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(6, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(6, '3 Month Premium', 3, 10, 7, 1);
INSERT INTO [Package] VALUES(7, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(7, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(7, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(8, '6 Month Premium', 6, 20, 5, 1);
INSERT INTO [Package] VALUES(8, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(8, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(9, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(9, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(9, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(10, '6 Month Premium', 6, 20, 5, 1);
INSERT INTO [Package] VALUES(10, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(10, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(11, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(11, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(11, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(12, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(12, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(12, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(13, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(13, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(13, '3 Month Premium', 3, 7, 4, 1);
INSERT INTO [Package] VALUES(14, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(14, '9 Month Premium', 9, 30, 15, 1);
INSERT INTO [Package] VALUES(14, '3 Month Premium', 3, 7, 4, 1);
INSERT INTO [Package] VALUES(15, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(15, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(15, '3 Month Premium', 3, 7, 4, 1);
INSERT INTO [Package] VALUES(16, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(16, '9 Month Premium', 9, 30, 15, 1);
INSERT INTO [Package] VALUES(16, '3 Month Premium', 3, 7, 4, 1);
INSERT INTO [Package] VALUES(17, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(17, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(17, '3 Month Premium', 3, 7, 4, 1);
INSERT INTO [Package] VALUES(18, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(18, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(18, '3 Month Premium', 3, 7, 4, 1);
INSERT INTO [Package] VALUES(19, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(19, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(19, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(20, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(20, '9 Month Premium', 9, 30, 15, 1);
INSERT INTO [Package] VALUES(20, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(21, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(21, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(21, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(22, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(22, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(22, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(23, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(23, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(23, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(24, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(24, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(24, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(25, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(25, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(25, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(26, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(26, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(26, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(27, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(27, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(27, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(28, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(28, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(28, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(29, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(29, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(29, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(30, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(30, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(30, '3 Month Premium', 3, 15, 14, 1);
INSERT INTO [Package] VALUES(31, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(31, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(31, '3 Month Premium', 3, 15, 14, 1);
INSERT INTO [Package] VALUES(32, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(32, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(32, '3 Month Premium', 3, 15, 14, 1);
INSERT INTO [Package] VALUES(33, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(33, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(33, '3 Month Premium', 3, 15, 14, 1);
INSERT INTO [Package] VALUES(34, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(34, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(34, '3 Month Premium', 3, 15, 14, 1);
INSERT INTO [Package] VALUES(35, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(35, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(35, '3 Month Premium', 3, 15, 14, 1);
INSERT INTO [Package] VALUES(36, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(36, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(36, '3 Month Premium', 3, 15, 14, 1);
INSERT INTO [Package] VALUES(37, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(37, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(37, '3 Month Premium', 3, 15, 14, 1);
INSERT INTO [Package] VALUES(38, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(38, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(38, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(39, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(39, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(39, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(40, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(40, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(40, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(41, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(41, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(41, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(42, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(42, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(42, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(43, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(43, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(43, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(44, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(44, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(44, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(45, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(45, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(45, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(46, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(46, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(46, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(47, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(47, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(47, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(48, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(48, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(48, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(49, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(49, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(49, '3 Month Premium', 3, 5, 3, 1);
INSERT INTO [Package] VALUES(50, '6 Month Premium', 6, 20, 18, 1);
INSERT INTO [Package] VALUES(50, '9 Month Premium', 9, 30, 27, 1);
INSERT INTO [Package] VALUES(50, '3 Month Premium', 3, 5, 3, 1);

insert into [Gender] values ('Male');
INSERT INTO [Gender] values ('Female');
INSERT INTO [Gender] values ('Prefer not to say');

INSERT INTO [Role] values ('Customer');
INSERT INTO [Role] values ('Admin');

insert into [User] values ('ngocdbhe182383@fpt.edu.vn', '123', 1, 'ngoc', 1, '123', 1);
insert into [User] values ('dunglhhe181276@fpt.edu.vn','12345',1,'lehungdung',1,'0963634669',1)
insert into [User] values ('quannm@fpt.edu.vn','1234',1,'nguyenminhquan',1,'0916712381',1)
insert into [User] values ('anlt@fpt.edu.vn','1234u505',1,'lethanhan',1,'0902532029',0)
insert into [User] values ('huynq@fpt.edu.vn','1234',1,'nguyenquochuy',1,'0906166329',0)

INSERT INTO [User] values ('fauvism@gmail.com', '123', 1, 'hoactzines bowdlerizer', 1, '09346016649', 1);
INSERT INTO [User] values ('fishponds@gmail.com', '123', 1, 'imp kookiest', 1, '09058897252', 1);
INSERT INTO [User] values ('rifest@gmail.com', '123', 1, 'shalom teleman', 1, '09316548064', 1);
INSERT INTO [User] values ('exporter@gmail.com', '123', 2, 'punditic glancer', 1, '09988543903', 1);
INSERT INTO [User] values ('putamen@gmail.com', '123', 1, 'unquieter chitty', 1, '09108092750', 1);
INSERT INTO [User] values ('gators@gmail.com', '123', 1, 'tetany marketed', 1, '09957535977', 1);
INSERT INTO [User] values ('girdled@gmail.com', '123', 2, 'regeneration adepts', 1, '09015868067', 1);
INSERT INTO [User] values ('amenders@gmail.com', '123', 2, 'talkiest corniche', 1, '09697409053', 1);
INSERT INTO [User] values ('refine@gmail.com', '123', 2, 'overurging spiritually', 1, '09384481294', 1);
INSERT INTO [User] values ('synfuels@gmail.com', '123', 1, 'baggers ridded', 1, '09484481926', 1);

INSERT INTO [User] values ('day@gmail.com', '123', 2, 'glacier cosmogonists', 2, '09051261813', 1);
INSERT INTO [User] values ('dendrologist@gmail.com', '123', 2, 'actorly metrorrhagia', 1, '09415105791', 1);
INSERT INTO [User] values ('cheerful@gmail.com', '123', 2, 'schmoose handling', 1, '09250500126', 1);

insert into [RegistrationStatus] values('Submitted');
insert into [RegistrationStatus] values('Pending Approval');
insert into [RegistrationStatus] values('Active');
insert into [RegistrationStatus] values('Withdrawn');
insert into [RegistrationStatus] values('Inactive');

INSERT INTO [Registration] VALUES(1, '2024-05-01', 3, 3, '2024-05-10', '2024-08-10', 'abc', 'abc', 'abc');
INSERT INTO [Registration] VALUES(1, null, 7, 1, null, null, 'abc', 'abc', 'abc');
INSERT INTO [Registration] VALUES(1, '2024-05-01', 5, 2, null, null, 'abc', 'abc', 'abc');
INSERT INTO [Registration] VALUES(1, null, 10, 1, null, null, 'abc', 'abc', 'abc');
INSERT INTO [Registration] VALUES(1, '2004-09-01', 15, 4, '2004-09-10', '2004-12-10', 'abc', 'abc', 'abc');
INSERT INTO [Registration] VALUES(1, '2004-09-01', 17, 5, '2004-09-10', '2004-12-10', 'abc', 'abc', 'abc');
INSERT INTO [Registration] VALUES(1, null, 20, 1, null, null, 'abc', 'abc', 'abc');
INSERT INTO [Registration] VALUES(1, '2024-05-01', 22, 2, null, null, 'abc', 'abc', 'abc');
INSERT INTO [Registration] VALUES(1, '2004-09-01', 25, 5, '2004-09-10', '2004-12-10', 'abc', 'abc', 'abc');
INSERT INTO [Registration] VALUES(1, '2024-05-01', 29, 2, null, null, 'abc', 'abc', 'abc');
INSERT INTO [Registration] VALUES(1, '2024-05-01', 33, 2, null, null, 'abc', 'abc', 'abc');
INSERT INTO [Registration] VALUES(1, null, 35, 1, null, null, null, null, null);
INSERT INTO [Registration] VALUES(1, '2024-05-01', 37, 3, '2024-05-10', '2024-08-10', 'abc', 'abc', 'abc');


INSERT INTO [dbo].[BlogCategory] ([BlogCategoryName])
VALUES ('Quiz Strategies'),
       ('Test Preparation Tips'),
       ('Exam Anxiety Management'),
       ('Popular Quiz Categories'),
       ('Success Stories'),
       ('General Tips & Tricks'),
       ('New Feature Announcements'),
       ('Product Updates');

-- Insert blog posts into the Blog table with specified dates as strings
INSERT INTO [dbo].[Blog] (UserId, BlogCategoryId, BlogTitle, UpdatedTime, PostBrief, PostText)
VALUES 
-- Quiz Strategies
(1, 1, 'Top 5 Strategies for Acing Your Quizzes', '2024-02-15', 'Learn the top 5 strategies to improve your quiz scores and ace every test.', ''),
(1, 1, 'How to Effectively Manage Your Time During Quizzes', '2024-02-20', 'Discover time management techniques to help you finish quizzes on time without rushing.', ''),
(1, 1, 'The Power of Practice Quizzes', '2024-03-01', 'Understand the benefits of taking practice quizzes and how they can enhance your learning.', ''),
(1, 1, 'Analyzing Quiz Questions: What to Look For', '2024-03-05', 'Learn how to break down quiz questions to understand what is being asked and how to answer effectively.', ''),
(1, 1, 'Using Elimination Methods to Improve Quiz Performance', '2024-03-10', 'Explore the process of eliminating incorrect answers to increase your chances of choosing the right one.', ''),
(1, 1, 'Quiz Strategies for Different Learning Styles', '2024-03-15', 'Find out how to tailor quiz strategies to match your learning style for better results.', ''),
(1, 1, 'Creating a Study Schedule for Quiz Preparation', '2024-03-20', 'Learn how to create a study schedule that optimizes your quiz preparation time.', ''),

-- Test Preparation Tips
(1, 2, 'Effective Test Preparation Techniques', '2024-03-25', 'Master the most effective techniques for preparing for tests.', ''),
(1, 2, 'The Importance of Sleep Before a Test', '2024-03-30', 'Discover why a good nights sleep is crucial before taking a test.', ''),
(1, 2, 'Balancing Study Sessions with Breaks', '2024-04-05', 'Learn how to balance study sessions with breaks to maximize retention.', ''),
(1, 2, 'Using Flashcards for Test Prep', '2024-04-10', 'Explore the benefits of using flashcards as a study tool for test preparation.', ''),
(1, 2, 'Group Study vs. Solo Study: Pros and Cons', '2024-04-15', 'Weigh the pros and cons of group study versus solo study for test preparation.', ''),
(1, 2, 'How to Create a Test Prep Plan', '2024-04-20', 'Learn how to create a comprehensive test preparation plan.', ''),

-- Exam Anxiety Management
(1, 3, 'Techniques to Reduce Exam Anxiety', '2024-04-25', 'Discover effective techniques to manage and reduce exam anxiety.', ''),
(1, 3, 'How to Stay Calm During an Exam', '2024-04-30', 'Learn strategies to stay calm and focused during exams.', ''),
(1, 3, 'The Role of Mindfulness in Exam Preparation', '2024-05-05', 'Understand how mindfulness practices can help in exam preparation.', ''),
(1, 3, 'Breathing Exercises to Manage Exam Stress', '2024-05-10', 'Explore breathing exercises that can help reduce stress during exams.', ''),
(1, 3, 'How to Overcome Negative Thoughts Before an Exam', '2024-05-15', 'Learn techniques to overcome negative thoughts and boost confidence before exams.', ''),
(1, 3, 'Building a Positive Mindset for Exam Success', '2024-05-20', 'Discover ways to build a positive mindset for better exam performance.', ''),
(1, 3, 'Nutrition Tips to Reduce Exam Stress', '2024-05-25', 'Learn how proper nutrition can help reduce stress and improve exam performance.', ''),

-- Popular Quiz Categories
(1, 4, 'Top 10 Popular Quiz Categories in 2024', '2024-02-01', 'Explore the top 10 quiz categories that are trending in 2024.', ''),
(1, 4, 'Why General Knowledge Quizzes Are So Popular', '2024-02-05', 'Understand why general knowledge quizzes attract so many participants.', ''),
(1, 4, 'The Rise of Pop Culture Quizzes', '2024-02-10', 'Discover the reasons behind the popularity of pop culture quizzes.', ''),
(1, 4, 'History Quizzes: Fun and Educational', '2024-02-15', 'Learn why history quizzes are both fun and educational.', ''),
(1, 4, 'Science Quizzes: Test Your Knowledge', '2024-02-20', 'Challenge your knowledge with engaging science quizzes.', ''),
(1, 4, 'The Popularity of Literature Quizzes', '2024-02-25', 'Explore the reasons why literature quizzes are a hit among readers.', ''),
(1, 4, 'Sports Quizzes: Are You a True Fan?', '2024-03-01', 'Test your sports knowledge with exciting sports quizzes.', ''),

-- Success Stories
(1, 5, 'How I Aced My Exams Using These Tips', '2024-01-05', 'Read the success story of a student who aced their exams using our tips.', ''),
(1, 5, 'From Failing to Passing: A Students Journey', '2024-01-10', 'Discover how a student turned their grades around with effective study strategies.', ''),
(1, 5, 'Overcoming Exam Anxiety: A Personal Story', '2024-01-15', 'Read a personal story of overcoming exam anxiety and achieving success.', ''),
(1, 5, 'Achieving Top Scores with Consistent Effort', '2024-01-20', 'Learn how consistent effort helped a student achieve top scores.', ''),
(1, 5, 'Success Story: Using Quizzes for Better Learning', '2024-01-25', 'Find out how using quizzes as a learning tool led to academic success.', ''),
(1, 5, 'The Power of a Positive Mindset in Exam Success', '2024-01-30', 'Discover the impact of a positive mindset on exam success through this story.', ''),
(1, 5, 'From Struggling to Thriving: A Students Tale', '2024-02-01', 'Read about a students journey from struggling to thriving in their studies.', ''),

-- General Tips & Tricks
(1, 6, '5 Study Habits of Successful Students', '2024-02-05', 'Discover the study habits that successful students swear by.', ''),
(1, 6, 'How to Stay Organized During the School Year', '2024-02-10', 'Learn tips and tricks to stay organized throughout the school year.', ''),
(1, 6, 'Effective Note-Taking Strategies', '2024-02-15', 'Explore different note-taking strategies to enhance your learning.', ''),
(1, 6, 'Maximizing Productivity: Tips for Students', '2024-02-20', 'Discover ways to maximize productivity and make the most of your study time.', ''),
(1, 6, 'How to Avoid Procrastination', '2024-02-25', 'Learn techniques to avoid procrastination and stay on track with your studies.', ''),
(1, 6, 'Balancing School and Extracurricular Activities', '2024-03-01', 'Find out how to balance school work with extracurricular activities effectively.', ''),
(1, 6, 'Tips for Effective Group Study Sessions', '2024-03-05', 'Learn tips for making the most out of group study sessions.', ''),

-- New Feature Announcements
(1, 7, 'Introducing Our New Quiz Feature!', '2024-03-10', 'Discover the exciting new quiz feature weve just launched.', ''),
(1, 7, 'New User Interface: A Fresh Look', '2024-03-15', 'Explore the changes in our new user interface designed for a better experience.', ''),
(1, 7, 'Enhanced Performance Metrics for Your Quizzes', '2024-03-20', 'Learn about the enhanced performance metrics available for your quizzes.', ''),
(1, 7, 'Customizable Quiz Themes Now Available', '2024-03-25', 'Discover how you can now customize quiz themes to match your preferences.', ''),
(1, 7, 'New Language Support: Quiz in Your Language', '2024-03-30', 'Find out about the new languages weve added support for in our quizzes.', ''),
(1, 7, 'Improved Accessibility Features', '2024-04-05', 'Learn about the improved accessibility features weve implemented.', ''),
(1, 7, 'Introducing Quiz Leaderboards', '2024-04-10', 'Get competitive with our new quiz leaderboards feature.', ''),

-- Product Updates
(1, 8, 'Latest Product Update: Whats New?', '2024-04-15', 'Find out whats new in our latest product update.', ''),
(1, 8, 'Bug Fixes and Improvements', '2024-04-20', 'Learn about the latest bug fixes and performance improvements.', ''),
(1, 8, 'New Features Added in the Latest Release', '2024-04-25', 'Discover the new features added in our latest product release.', ''),
(1, 8, 'Upcoming Changes You Should Know About', '2024-04-30', 'Stay informed about the upcoming changes in our product.', ''),
(1, 8, 'User Feedback: What Weve Improved', '2024-05-05', 'See how user feedback has shaped our recent improvements.', ''),
(1, 8, 'Enhanced Security Features', '2024-05-10', 'Learn about the enhanced security features weve implemented.', ''),
(1, 8, 'Faster Loading Times with Our Latest Update', '2024-05-15', 'Experience faster loading times with our latest update.', '');

-- Insert 30 sample quizzes for subject IDs 1 to 13
INSERT INTO [dbo].[Quiz] (SubjectId, QuizName, Level, DurationInMinutes, PassRate, QuizType, IsPublished) VALUES 
(1, N'Math Basics', '0', 30, 70, '0', 1),
(2, N'Advanced Chemistry', '2', 45, 75, '1', 0),
(3, N'Physics Fundamentals', '1', 40, 65, '0', 1),
(4, N'Biology Basics', '0', 50, 80, '1', 1),
(5, N'World History', '1', 35, 60, '0', 1),
(6, N'Geography Quiz', '2', 60, 85, '1', 0),
(7, N'Computer Science', '1', 55, 70, '0', 1),
(8, N'English Literature', '0', 25, 50, '1', 1),
(9, N'Art History', '2', 65, 90, '0', 0),
(10, N'Music Theory', '1', 30, 75, '1', 1),
(11, N'Philosophy Quiz', '0', 40, 60, '0', 1),
(12, N'Economics Basics', '2', 50, 85, '1', 0),
(13, N'Political Science', '1', 45, 70, '0', 1),
(1, N'Advanced Algebra', '2', 60, 80, '1', 1),
(2, N'Organic Chemistry', '1', 35, 65, '0', 1),
(3, N'Thermodynamics', '0', 55, 75, '1', 0),
(4, N'Molecular Biology', '2', 50, 85, '0', 1),
(5, N'Medieval History', '1', 45, 60, '1', 1),
(6, N'Human Geography', '0', 40, 70, '0', 1),
(7, N'Algorithms', '2', 50, 80, '1', 0),
(8, N'Shakespeare Studies', '1', 30, 65, '0', 1),
(9, N'Renaissance Art', '0', 60, 90, '1', 1),
(10, N'Jazz Music', '2', 55, 75, '0', 0),
(11, N'Logic and Reasoning', '1', 35, 70, '1', 1),
(12, N'Macroeconomics', '0', 50, 85, '0', 1),
(13, N'International Relations', '2', 45, 75, '1', 0),
(1, N'Geometry Quiz', '1', 30, 65, '0', 1),
(2, N'Inorganic Chemistry', '0', 50, 80, '1', 1),
(3, N'Quantum Mechanics', '2', 60, 85, '0', 1);

INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N' 1+1=?', N'plus', 1, 1, 1, 2)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'thu do VN', N'country', 1, 1, 2, 2)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'MAD (Mean Absolute Deviation) Question 1', N'Explanation for MAD (Mean Absolute Deviation) question 1', 2, 1, 1, 2)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'MAD (Mean Absolute Deviation) Question 2', N'Explanation for MAD (Mean Absolute Deviation) question 2', 1, 1, 1, 2)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'MAD (Mean Absolute Deviation) Question 3', N'Explanation for MAD (Mean Absolute Deviation) question 3', 3, 1, 2, 2)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'MAD (Mean Absolute Deviation) Question 4', N'Explanation for MAD (Mean Absolute Deviation) question 4', 3, 1, 2, 2)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'MAD (Mean Absolute Deviation) Question 5', N'Explanation for MAD (Mean Absolute Deviation) question 5', 2, 1, 3, 2)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'MAD (Mean Absolute Deviation) Question 6', N'Explanation for MAD (Mean Absolute Deviation) question 6', 2, 1, 3, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'MAD (Mean Absolute Deviation) Question 7', N'Explanation for MAD (Mean Absolute Deviation) question 7', 3, 1, 4, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'MAD (Mean Absolute Deviation) Question 8', N'Explanation for MAD (Mean Absolute Deviation) question 8', 3, 1, 4, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'MAD (Mean Absolute Deviation) Question 9', N'Explanation for MAD (Mean Absolute Deviation) question 9', 2, 1, 5, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'MAD (Mean Absolute Deviation) Question 10', N'Explanation for MAD (Mean Absolute Deviation) question 10', 2, 1, 5, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'CSI (Customer Satisfaction Index) Question 1', N'Explanation for CSI (Customer Satisfaction Index) question 1', 1, 2, 6, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'CSI (Customer Satisfaction Index) Question 2', N'Explanation for CSI (Customer Satisfaction Index) question 2', 3, 2, 6, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'CSI (Customer Satisfaction Index) Question 3', N'Explanation for CSI (Customer Satisfaction Index) question 3', 1, 2, 7, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'CSI (Customer Satisfaction Index) Question 4', N'Explanation for CSI (Customer Satisfaction Index) question 4', 1, 2, 7, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'CSI (Customer Satisfaction Index) Question 5', N'Explanation for CSI (Customer Satisfaction Index) question 5', 1, 2, 8, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'CSI (Customer Satisfaction Index) Question 6', N'Explanation for CSI (Customer Satisfaction Index) question 6', 3, 2, 8, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'CSI (Customer Satisfaction Index) Question 7', N'Explanation for CSI (Customer Satisfaction Index) question 7', 3, 2, 9, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'CSI (Customer Satisfaction Index) Question 8', N'Explanation for CSI (Customer Satisfaction Index) question 8', 3, 2, 9, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'CSI (Customer Satisfaction Index) Question 9', N'Explanation for CSI (Customer Satisfaction Index) question 9', 2, 2, 10, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'CSI (Customer Satisfaction Index) Question 10', N'Explanation for CSI (Customer Satisfaction Index) question 10', 3, 2, 10, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'WED (Weekly Earnings Distribution) Question 1', N'Explanation for WED (Weekly Earnings Distribution) question 1', 2, 3, 11, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'WED (Weekly Earnings Distribution) Question 2', N'Explanation for WED (Weekly Earnings Distribution) question 2', 3, 3, 11, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'WED (Weekly Earnings Distribution) Question 3', N'Explanation for WED (Weekly Earnings Distribution) question 3', 3, 3, 12, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'WED (Weekly Earnings Distribution) Question 4', N'Explanation for WED (Weekly Earnings Distribution) question 4', 1, 3, 12, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'WED (Weekly Earnings Distribution) Question 5', N'Explanation for WED (Weekly Earnings Distribution) question 5', 3, 3, 13, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'WED (Weekly Earnings Distribution) Question 6', N'Explanation for WED (Weekly Earnings Distribution) question 6', 3, 3, 13, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'WED (Weekly Earnings Distribution) Question 7', N'Explanation for WED (Weekly Earnings Distribution) question 7', 3, 3, 14, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'WED (Weekly Earnings Distribution) Question 8', N'Explanation for WED (Weekly Earnings Distribution) question 8', 2, 3, 14, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'WED (Weekly Earnings Distribution) Question 9', N'Explanation for WED (Weekly Earnings Distribution) question 9', 2, 3, 15, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'WED (Weekly Earnings Distribution) Question 10', N'Explanation for WED (Weekly Earnings Distribution) question 10', 1, 3, 15, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'OSG (Open Study Group) Question 1', N'Explanation for OSG (Open Study Group) question 1', 3, 4, 16, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'OSG (Open Study Group) Question 2', N'Explanation for OSG (Open Study Group) question 2', 3, 4, 16, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'OSG (Open Study Group) Question 3', N'Explanation for OSG (Open Study Group) question 3', 1, 4, 17, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'OSG (Open Study Group) Question 4', N'Explanation for OSG (Open Study Group) question 4', 2, 4, 17, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'OSG (Open Study Group) Question 5', N'Explanation for OSG (Open Study Group) question 5', 1, 4, 18, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'OSG (Open Study Group) Question 6', N'Explanation for OSG (Open Study Group) question 6', 3, 4, 18, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'OSG (Open Study Group) Question 7', N'Explanation for OSG (Open Study Group) question 7', 3, 4, 19, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'OSG (Open Study Group) Question 8', N'Explanation for OSG (Open Study Group) question 8', 1, 4, 19, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'OSG (Open Study Group) Question 9', N'Explanation for OSG (Open Study Group) question 9', 1, 4, 20, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'OSG (Open Study Group) Question 10', N'Explanation for OSG (Open Study Group) question 10', 2, 4, 20, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N' 1+1=?', N'plus', 1, 1, 1, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N'thu do VN', N'country', 1, 1, 2, 1)
INSERT [dbo].[Question] ([QuestionText], [Explanation], [Level], [SubjectID], [LessonID], [Status]) VALUES (N' 1+1=?', N'plus', 1, 1, 1, 1)

INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (1, N'2.0', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (1, N'3.0', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (1, N'4.0', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (2, N'da1', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (2, N'da2', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (2, N'HN', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (2, N'dap3', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (3, N'Answer 1 for MAD (Mean Absolute Deviation) question 1', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (3, N'Answer 2 for MAD (Mean Absolute Deviation) question 1', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (3, N'Answer 3 for MAD (Mean Absolute Deviation) question 1', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (3, N'Answer 4 for MAD (Mean Absolute Deviation) question 1', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (3, N'Answer 5 for MAD (Mean Absolute Deviation) question 1', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (4, N'Answer 1 for MAD (Mean Absolute Deviation) question 2', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (4, N'Answer 2 for MAD (Mean Absolute Deviation) question 2', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (4, N'Answer 3 for MAD (Mean Absolute Deviation) question 2', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (4, N'Answer 4 for MAD (Mean Absolute Deviation) question 2', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (4, N'Answer 5 for MAD (Mean Absolute Deviation) question 2', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (5, N'Answer 1 for MAD (Mean Absolute Deviation) question 3', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (5, N'Answer 2 for MAD (Mean Absolute Deviation) question 3', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (5, N'Answer 3 for MAD (Mean Absolute Deviation) question 3', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (5, N'Answer 4 for MAD (Mean Absolute Deviation) question 3', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (5, N'Answer 5 for MAD (Mean Absolute Deviation) question 3', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (6, N'Answer 1 for MAD (Mean Absolute Deviation) question 4', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (6, N'Answer 2 for MAD (Mean Absolute Deviation) question 4', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (6, N'Answer 3 for MAD (Mean Absolute Deviation) question 4', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (6, N'Answer 4 for MAD (Mean Absolute Deviation) question 4', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (6, N'Answer 5 for MAD (Mean Absolute Deviation) question 4', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (7, N'Answer 1 for MAD (Mean Absolute Deviation) question 5', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (7, N'Answer 2 for MAD (Mean Absolute Deviation) question 5', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (7, N'Answer 3 for MAD (Mean Absolute Deviation) question 5', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (7, N'Answer 4 for MAD (Mean Absolute Deviation) question 5', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (7, N'Answer 5 for MAD (Mean Absolute Deviation) question 5', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (8, N'Answer 1 for MAD (Mean Absolute Deviation) question 6', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (8, N'Answer 2 for MAD (Mean Absolute Deviation) question 6', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (8, N'Answer 3 for MAD (Mean Absolute Deviation) question 6', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (8, N'Answer 4 for MAD (Mean Absolute Deviation) question 6', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (8, N'Answer 5 for MAD (Mean Absolute Deviation) question 6', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (9, N'Answer 1 for MAD (Mean Absolute Deviation) question 7', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (9, N'Answer 2 for MAD (Mean Absolute Deviation) question 7', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (9, N'Answer 3 for MAD (Mean Absolute Deviation) question 7', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (9, N'Answer 4 for MAD (Mean Absolute Deviation) question 7', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (9, N'Answer 5 for MAD (Mean Absolute Deviation) question 7', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (10, N'Answer 1 for MAD (Mean Absolute Deviation) question 8', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (10, N'Answer 2 for MAD (Mean Absolute Deviation) question 8', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (10, N'Answer 3 for MAD (Mean Absolute Deviation) question 8', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (10, N'Answer 4 for MAD (Mean Absolute Deviation) question 8', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (10, N'Answer 5 for MAD (Mean Absolute Deviation) question 8', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (11, N'Answer 1 for MAD (Mean Absolute Deviation) question 9', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (11, N'Answer 2 for MAD (Mean Absolute Deviation) question 9', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (11, N'Answer 3 for MAD (Mean Absolute Deviation) question 9', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (11, N'Answer 4 for MAD (Mean Absolute Deviation) question 9', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (11, N'Answer 5 for MAD (Mean Absolute Deviation) question 9', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (12, N'Answer 1 for MAD (Mean Absolute Deviation) question 10', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (12, N'Answer 2 for MAD (Mean Absolute Deviation) question 10', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (12, N'Answer 3 for MAD (Mean Absolute Deviation) question 10', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (12, N'Answer 4 for MAD (Mean Absolute Deviation) question 10', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (12, N'Answer 5 for MAD (Mean Absolute Deviation) question 10', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (13, N'Answer 1 for CSI (Customer Satisfaction Index) question 1', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (13, N'Answer 2 for CSI (Customer Satisfaction Index) question 1', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (13, N'Answer 3 for CSI (Customer Satisfaction Index) question 1', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (13, N'Answer 4 for CSI (Customer Satisfaction Index) question 1', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (13, N'Answer 5 for CSI (Customer Satisfaction Index) question 1', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (14, N'Answer 1 for CSI (Customer Satisfaction Index) question 2', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (14, N'Answer 2 for CSI (Customer Satisfaction Index) question 2', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (14, N'Answer 3 for CSI (Customer Satisfaction Index) question 2', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (14, N'Answer 4 for CSI (Customer Satisfaction Index) question 2', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (14, N'Answer 5 for CSI (Customer Satisfaction Index) question 2', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (15, N'Answer 1 for CSI (Customer Satisfaction Index) question 3', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (15, N'Answer 2 for CSI (Customer Satisfaction Index) question 3', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (15, N'Answer 3 for CSI (Customer Satisfaction Index) question 3', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (15, N'Answer 4 for CSI (Customer Satisfaction Index) question 3', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (15, N'Answer 5 for CSI (Customer Satisfaction Index) question 3', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (16, N'Answer 1 for CSI (Customer Satisfaction Index) question 4', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (16, N'Answer 2 for CSI (Customer Satisfaction Index) question 4', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (16, N'Answer 3 for CSI (Customer Satisfaction Index) question 4', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (16, N'Answer 4 for CSI (Customer Satisfaction Index) question 4', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (16, N'Answer 5 for CSI (Customer Satisfaction Index) question 4', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (17, N'Answer 1 for CSI (Customer Satisfaction Index) question 5', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (17, N'Answer 2 for CSI (Customer Satisfaction Index) question 5', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (17, N'Answer 3 for CSI (Customer Satisfaction Index) question 5', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (17, N'Answer 4 for CSI (Customer Satisfaction Index) question 5', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (17, N'Answer 5 for CSI (Customer Satisfaction Index) question 5', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (18, N'Answer 1 for CSI (Customer Satisfaction Index) question 6', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (18, N'Answer 2 for CSI (Customer Satisfaction Index) question 6', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (18, N'Answer 3 for CSI (Customer Satisfaction Index) question 6', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (18, N'Answer 4 for CSI (Customer Satisfaction Index) question 6', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (18, N'Answer 5 for CSI (Customer Satisfaction Index) question 6', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (19, N'Answer 1 for CSI (Customer Satisfaction Index) question 7', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (19, N'Answer 2 for CSI (Customer Satisfaction Index) question 7', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (19, N'Answer 3 for CSI (Customer Satisfaction Index) question 7', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (19, N'Answer 4 for CSI (Customer Satisfaction Index) question 7', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (19, N'Answer 5 for CSI (Customer Satisfaction Index) question 7', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (20, N'Answer 1 for CSI (Customer Satisfaction Index) question 8', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (20, N'Answer 2 for CSI (Customer Satisfaction Index) question 8', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (20, N'Answer 3 for CSI (Customer Satisfaction Index) question 8', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (20, N'Answer 4 for CSI (Customer Satisfaction Index) question 8', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (20, N'Answer 5 for CSI (Customer Satisfaction Index) question 8', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (21, N'Answer 1 for CSI (Customer Satisfaction Index) question 9', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (21, N'Answer 2 for CSI (Customer Satisfaction Index) question 9', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (21, N'Answer 3 for CSI (Customer Satisfaction Index) question 9', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (21, N'Answer 4 for CSI (Customer Satisfaction Index) question 9', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (21, N'Answer 5 for CSI (Customer Satisfaction Index) question 9', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (22, N'Answer 1 for CSI (Customer Satisfaction Index) question 10', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (22, N'Answer 2 for CSI (Customer Satisfaction Index) question 10', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (22, N'Answer 3 for CSI (Customer Satisfaction Index) question 10', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (22, N'Answer 4 for CSI (Customer Satisfaction Index) question 10', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (22, N'Answer 5 for CSI (Customer Satisfaction Index) question 10', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (23, N'Answer 1 for WED (Weekly Earnings Distribution) question 1', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (23, N'Answer 2 for WED (Weekly Earnings Distribution) question 1', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (23, N'Answer 3 for WED (Weekly Earnings Distribution) question 1', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (23, N'Answer 4 for WED (Weekly Earnings Distribution) question 1', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (23, N'Answer 5 for WED (Weekly Earnings Distribution) question 1', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (24, N'Answer 1 for WED (Weekly Earnings Distribution) question 2', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (24, N'Answer 2 for WED (Weekly Earnings Distribution) question 2', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (24, N'Answer 3 for WED (Weekly Earnings Distribution) question 2', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (24, N'Answer 4 for WED (Weekly Earnings Distribution) question 2', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (24, N'Answer 5 for WED (Weekly Earnings Distribution) question 2', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (25, N'Answer 1 for WED (Weekly Earnings Distribution) question 3', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (25, N'Answer 2 for WED (Weekly Earnings Distribution) question 3', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (25, N'Answer 3 for WED (Weekly Earnings Distribution) question 3', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (25, N'Answer 4 for WED (Weekly Earnings Distribution) question 3', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (25, N'Answer 5 for WED (Weekly Earnings Distribution) question 3', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (26, N'Answer 1 for WED (Weekly Earnings Distribution) question 4', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (26, N'Answer 2 for WED (Weekly Earnings Distribution) question 4', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (26, N'Answer 3 for WED (Weekly Earnings Distribution) question 4', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (26, N'Answer 4 for WED (Weekly Earnings Distribution) question 4', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (26, N'Answer 5 for WED (Weekly Earnings Distribution) question 4', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (27, N'Answer 1 for WED (Weekly Earnings Distribution) question 5', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (27, N'Answer 2 for WED (Weekly Earnings Distribution) question 5', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (27, N'Answer 3 for WED (Weekly Earnings Distribution) question 5', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (27, N'Answer 4 for WED (Weekly Earnings Distribution) question 5', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (27, N'Answer 5 for WED (Weekly Earnings Distribution) question 5', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (28, N'Answer 1 for WED (Weekly Earnings Distribution) question 6', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (28, N'Answer 2 for WED (Weekly Earnings Distribution) question 6', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (28, N'Answer 3 for WED (Weekly Earnings Distribution) question 6', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (28, N'Answer 4 for WED (Weekly Earnings Distribution) question 6', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (28, N'Answer 5 for WED (Weekly Earnings Distribution) question 6', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (29, N'Answer 1 for WED (Weekly Earnings Distribution) question 7', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (29, N'Answer 2 for WED (Weekly Earnings Distribution) question 7', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (29, N'Answer 3 for WED (Weekly Earnings Distribution) question 7', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (29, N'Answer 4 for WED (Weekly Earnings Distribution) question 7', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (29, N'Answer 5 for WED (Weekly Earnings Distribution) question 7', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (30, N'Answer 1 for WED (Weekly Earnings Distribution) question 8', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (30, N'Answer 2 for WED (Weekly Earnings Distribution) question 8', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (30, N'Answer 3 for WED (Weekly Earnings Distribution) question 8', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (30, N'Answer 4 for WED (Weekly Earnings Distribution) question 8', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (30, N'Answer 5 for WED (Weekly Earnings Distribution) question 8', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (31, N'Answer 1 for WED (Weekly Earnings Distribution) question 9', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (31, N'Answer 2 for WED (Weekly Earnings Distribution) question 9', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (31, N'Answer 3 for WED (Weekly Earnings Distribution) question 9', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (31, N'Answer 4 for WED (Weekly Earnings Distribution) question 9', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (31, N'Answer 5 for WED (Weekly Earnings Distribution) question 9', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (32, N'Answer 1 for WED (Weekly Earnings Distribution) question 10', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (32, N'Answer 2 for WED (Weekly Earnings Distribution) question 10', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (32, N'Answer 3 for WED (Weekly Earnings Distribution) question 10', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (32, N'Answer 4 for WED (Weekly Earnings Distribution) question 10', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (32, N'Answer 5 for WED (Weekly Earnings Distribution) question 10', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (33, N'Answer 1 for OSG (Open Study Group) question 1', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (33, N'Answer 2 for OSG (Open Study Group) question 1', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (33, N'Answer 3 for OSG (Open Study Group) question 1', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (33, N'Answer 4 for OSG (Open Study Group) question 1', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (33, N'Answer 5 for OSG (Open Study Group) question 1', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (34, N'Answer 1 for OSG (Open Study Group) question 2', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (34, N'Answer 2 for OSG (Open Study Group) question 2', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (34, N'Answer 3 for OSG (Open Study Group) question 2', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (34, N'Answer 4 for OSG (Open Study Group) question 2', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (34, N'Answer 5 for OSG (Open Study Group) question 2', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (35, N'Answer 1 for OSG (Open Study Group) question 3', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (35, N'Answer 2 for OSG (Open Study Group) question 3', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (35, N'Answer 3 for OSG (Open Study Group) question 3', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (35, N'Answer 4 for OSG (Open Study Group) question 3', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (35, N'Answer 5 for OSG (Open Study Group) question 3', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (36, N'Answer 1 for OSG (Open Study Group) question 4', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (36, N'Answer 2 for OSG (Open Study Group) question 4', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (36, N'Answer 3 for OSG (Open Study Group) question 4', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (36, N'Answer 4 for OSG (Open Study Group) question 4', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (36, N'Answer 5 for OSG (Open Study Group) question 4', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (37, N'Answer 1 for OSG (Open Study Group) question 5', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (37, N'Answer 2 for OSG (Open Study Group) question 5', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (37, N'Answer 3 for OSG (Open Study Group) question 5', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (37, N'Answer 4 for OSG (Open Study Group) question 5', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (37, N'Answer 5 for OSG (Open Study Group) question 5', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (38, N'Answer 1 for OSG (Open Study Group) question 6', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (38, N'Answer 2 for OSG (Open Study Group) question 6', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (38, N'Answer 3 for OSG (Open Study Group) question 6', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (38, N'Answer 4 for OSG (Open Study Group) question 6', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (38, N'Answer 5 for OSG (Open Study Group) question 6', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (39, N'Answer 1 for OSG (Open Study Group) question 7', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (39, N'Answer 2 for OSG (Open Study Group) question 7', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (39, N'Answer 3 for OSG (Open Study Group) question 7', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (39, N'Answer 4 for OSG (Open Study Group) question 7', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (39, N'Answer 5 for OSG (Open Study Group) question 7', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (40, N'Answer 1 for OSG (Open Study Group) question 8', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (40, N'Answer 2 for OSG (Open Study Group) question 8', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (40, N'Answer 3 for OSG (Open Study Group) question 8', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (40, N'Answer 4 for OSG (Open Study Group) question 8', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (40, N'Answer 5 for OSG (Open Study Group) question 8', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (41, N'Answer 1 for OSG (Open Study Group) question 9', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (41, N'Answer 2 for OSG (Open Study Group) question 9', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (41, N'Answer 3 for OSG (Open Study Group) question 9', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (41, N'Answer 4 for OSG (Open Study Group) question 9', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (41, N'Answer 5 for OSG (Open Study Group) question 9', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (42, N'Answer 1 for OSG (Open Study Group) question 10', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (42, N'Answer 2 for OSG (Open Study Group) question 10', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (42, N'Answer 3 for OSG (Open Study Group) question 10', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (42, N'Answer 4 for OSG (Open Study Group) question 10', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (42, N'Answer 5 for OSG (Open Study Group) question 10', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (43, N'2.0', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (43, N'3.0', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (43, N'4.0', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (44, N'da1', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (44, N'da2', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (44, N'HN', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (44, N'dap3', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (45, N'2.0', 1)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (45, N'3.0', 0)
INSERT [dbo].[Answer] ([QuestionID], [AnswerName], [IsCorrect]) VALUES (45, N'4.0', 0)