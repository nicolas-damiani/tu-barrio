USE [master]
GO
/****** Object:  Database [TuBarrioDataBase]    Script Date: 6/18/2019 4:24:30 PM ******/
CREATE DATABASE [TuBarrioDataBase]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'TuBarrioDataBase', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\TuBarrioDataBase.mdf' , SIZE = 4288KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'TuBarrioDataBase_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\TuBarrioDataBase_log.ldf' , SIZE = 3904KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [TuBarrioDataBase] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [TuBarrioDataBase].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [TuBarrioDataBase] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [TuBarrioDataBase] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [TuBarrioDataBase] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [TuBarrioDataBase] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [TuBarrioDataBase] SET ARITHABORT OFF 
GO
ALTER DATABASE [TuBarrioDataBase] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [TuBarrioDataBase] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [TuBarrioDataBase] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [TuBarrioDataBase] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [TuBarrioDataBase] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [TuBarrioDataBase] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [TuBarrioDataBase] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [TuBarrioDataBase] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [TuBarrioDataBase] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [TuBarrioDataBase] SET  ENABLE_BROKER 
GO
ALTER DATABASE [TuBarrioDataBase] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [TuBarrioDataBase] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [TuBarrioDataBase] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [TuBarrioDataBase] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [TuBarrioDataBase] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [TuBarrioDataBase] SET READ_COMMITTED_SNAPSHOT ON 
GO
ALTER DATABASE [TuBarrioDataBase] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [TuBarrioDataBase] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [TuBarrioDataBase] SET  MULTI_USER 
GO
ALTER DATABASE [TuBarrioDataBase] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [TuBarrioDataBase] SET DB_CHAINING OFF 
GO
ALTER DATABASE [TuBarrioDataBase] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [TuBarrioDataBase] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [TuBarrioDataBase] SET DELAYED_DURABILITY = DISABLED 
GO
USE [TuBarrioDataBase]
GO
/****** Object:  Table [dbo].[__MigrationHistory]    Script Date: 6/18/2019 4:24:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[__MigrationHistory](
	[MigrationId] [nvarchar](150) NOT NULL,
	[ContextKey] [nvarchar](300) NOT NULL,
	[Model] [varbinary](max) NOT NULL,
	[ProductVersion] [nvarchar](32) NOT NULL,
 CONSTRAINT [PK_dbo.__MigrationHistory] PRIMARY KEY CLUSTERED 
(
	[MigrationId] ASC,
	[ContextKey] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Comments]    Script Date: 6/18/2019 4:24:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Comments](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Text] [nvarchar](max) NULL,
	[CreatedOn] [datetime] NOT NULL,
	[Creator_Email] [nvarchar](128) NULL,
 CONSTRAINT [PK_dbo.Comments] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[PublicationComments]    Script Date: 6/18/2019 4:24:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PublicationComments](
	[Publication_Id] [int] NOT NULL,
	[Comment_Id] [int] NOT NULL,
 CONSTRAINT [PK_dbo.PublicationComments] PRIMARY KEY CLUSTERED 
(
	[Publication_Id] ASC,
	[Comment_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Publications]    Script Date: 6/18/2019 4:24:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Publications](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Title] [nvarchar](max) NULL,
	[Description] [nvarchar](max) NULL,
	[Longitude] [float] NOT NULL,
	[Latitude] [float] NOT NULL,
	[PublicationType] [int] NOT NULL,
	[UpdatedOn] [datetime] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[Deleted] [int] NOT NULL,
	[PublicationImage] [nvarchar](max) NULL,
	[Author_Email] [nvarchar](128) NULL,
 CONSTRAINT [PK_dbo.Publications] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[PublicationUsers]    Script Date: 6/18/2019 4:24:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PublicationUsers](
	[Publication_Id] [int] NOT NULL,
	[User_Email] [nvarchar](128) NOT NULL,
 CONSTRAINT [PK_dbo.PublicationUsers] PRIMARY KEY CLUSTERED 
(
	[Publication_Id] ASC,
	[User_Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Users]    Script Date: 6/18/2019 4:24:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[Email] [nvarchar](128) NOT NULL,
	[Id] [int] NOT NULL,
	[Surname] [nvarchar](max) NULL,
	[Name] [nvarchar](max) NULL,
	[Phone] [nvarchar](max) NULL,
	[Token] [nvarchar](max) NULL,
	[DeviceNotificationToken] [nvarchar](max) NULL,
	[CreatedOn] [datetime] NOT NULL,
	[ProfileImage] [nvarchar](max) NULL,
 CONSTRAINT [PK_dbo.Users] PRIMARY KEY CLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_Creator_Email]    Script Date: 6/18/2019 4:24:30 PM ******/
CREATE NONCLUSTERED INDEX [IX_Creator_Email] ON [dbo].[Comments]
(
	[Creator_Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_Comment_Id]    Script Date: 6/18/2019 4:24:30 PM ******/
CREATE NONCLUSTERED INDEX [IX_Comment_Id] ON [dbo].[PublicationComments]
(
	[Comment_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_Publication_Id]    Script Date: 6/18/2019 4:24:30 PM ******/
CREATE NONCLUSTERED INDEX [IX_Publication_Id] ON [dbo].[PublicationComments]
(
	[Publication_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_Author_Email]    Script Date: 6/18/2019 4:24:30 PM ******/
CREATE NONCLUSTERED INDEX [IX_Author_Email] ON [dbo].[Publications]
(
	[Author_Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_Publication_Id]    Script Date: 6/18/2019 4:24:30 PM ******/
CREATE NONCLUSTERED INDEX [IX_Publication_Id] ON [dbo].[PublicationUsers]
(
	[Publication_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_User_Email]    Script Date: 6/18/2019 4:24:30 PM ******/
CREATE NONCLUSTERED INDEX [IX_User_Email] ON [dbo].[PublicationUsers]
(
	[User_Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Comments]  WITH CHECK ADD  CONSTRAINT [FK_dbo.Comments_dbo.Users_Creator_Email] FOREIGN KEY([Creator_Email])
REFERENCES [dbo].[Users] ([Email])
GO
ALTER TABLE [dbo].[Comments] CHECK CONSTRAINT [FK_dbo.Comments_dbo.Users_Creator_Email]
GO
ALTER TABLE [dbo].[PublicationComments]  WITH CHECK ADD  CONSTRAINT [FK_dbo.PublicationComments_dbo.Comments_Comment_Id] FOREIGN KEY([Comment_Id])
REFERENCES [dbo].[Comments] ([Id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[PublicationComments] CHECK CONSTRAINT [FK_dbo.PublicationComments_dbo.Comments_Comment_Id]
GO
ALTER TABLE [dbo].[PublicationComments]  WITH CHECK ADD  CONSTRAINT [FK_dbo.PublicationComments_dbo.Publications_Publication_Id] FOREIGN KEY([Publication_Id])
REFERENCES [dbo].[Publications] ([Id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[PublicationComments] CHECK CONSTRAINT [FK_dbo.PublicationComments_dbo.Publications_Publication_Id]
GO
ALTER TABLE [dbo].[Publications]  WITH CHECK ADD  CONSTRAINT [FK_dbo.Publications_dbo.Users_Author_Email] FOREIGN KEY([Author_Email])
REFERENCES [dbo].[Users] ([Email])
GO
ALTER TABLE [dbo].[Publications] CHECK CONSTRAINT [FK_dbo.Publications_dbo.Users_Author_Email]
GO
ALTER TABLE [dbo].[PublicationUsers]  WITH CHECK ADD  CONSTRAINT [FK_dbo.PublicationUsers_dbo.Publications_Publication_Id] FOREIGN KEY([Publication_Id])
REFERENCES [dbo].[Publications] ([Id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[PublicationUsers] CHECK CONSTRAINT [FK_dbo.PublicationUsers_dbo.Publications_Publication_Id]
GO
ALTER TABLE [dbo].[PublicationUsers]  WITH CHECK ADD  CONSTRAINT [FK_dbo.PublicationUsers_dbo.Users_User_Email] FOREIGN KEY([User_Email])
REFERENCES [dbo].[Users] ([Email])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[PublicationUsers] CHECK CONSTRAINT [FK_dbo.PublicationUsers_dbo.Users_User_Email]
GO
USE [master]
GO
ALTER DATABASE [TuBarrioDataBase] SET  READ_WRITE 
GO
