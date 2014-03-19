USE [master]
GO
/****** Object:  Database [FlappyBird]    Script Date: 19/03/2014 15.07.05 ******/
CREATE DATABASE [FlappyBird]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'FlappyBird', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.SQLSERVER2012\MSSQL\DATA\FlappyBird.mdf' , SIZE = 5120KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'FlappyBird_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.SQLSERVER2012\MSSQL\DATA\FlappyBird_log.ldf' , SIZE = 2048KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [FlappyBird] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [FlappyBird].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [FlappyBird] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [FlappyBird] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [FlappyBird] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [FlappyBird] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [FlappyBird] SET ARITHABORT OFF 
GO
ALTER DATABASE [FlappyBird] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [FlappyBird] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [FlappyBird] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [FlappyBird] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [FlappyBird] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [FlappyBird] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [FlappyBird] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [FlappyBird] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [FlappyBird] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [FlappyBird] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [FlappyBird] SET  DISABLE_BROKER 
GO
ALTER DATABASE [FlappyBird] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [FlappyBird] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [FlappyBird] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [FlappyBird] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [FlappyBird] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [FlappyBird] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [FlappyBird] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [FlappyBird] SET RECOVERY FULL 
GO
ALTER DATABASE [FlappyBird] SET  MULTI_USER 
GO
ALTER DATABASE [FlappyBird] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [FlappyBird] SET DB_CHAINING OFF 
GO
ALTER DATABASE [FlappyBird] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [FlappyBird] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
EXEC sys.sp_db_vardecimal_storage_format N'FlappyBird', N'ON'
GO
USE [FlappyBird]
GO
/****** Object:  User [flappybird]    Script Date: 19/03/2014 15.07.05 ******/
CREATE USER [flappybird] FOR LOGIN [flappybird] WITH DEFAULT_SCHEMA=[db_datareader]
GO
ALTER ROLE [db_datareader] ADD MEMBER [flappybird]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [flappybird]
GO
/****** Object:  Table [dbo].[Scores]    Script Date: 19/03/2014 15.07.05 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Scores](
	[ScoreId] [int] IDENTITY(1,1) NOT NULL,
	[Username] [nvarchar](16) NOT NULL,
	[Score] [int] NOT NULL,
	[Timestamp] [datetime] NOT NULL,
 CONSTRAINT [PK_Scores] PRIMARY KEY CLUSTERED 
(
	[ScoreId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  View [dbo].[topFive]    Script Date: 19/03/2014 15.07.05 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[topFive] as
select top 5 *
from dbo.Scores
order by score desc

GO
ALTER TABLE [dbo].[Scores] ADD  CONSTRAINT [DF_Scores_Timestamp]  DEFAULT (getdate()) FOR [Timestamp]
GO
USE [master]
GO
ALTER DATABASE [FlappyBird] SET  READ_WRITE 
GO
