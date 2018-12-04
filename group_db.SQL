IF EXISTS(SELECT * FROM sys.sysdatabases where name = N'GroupWork')
	DROP DATABASE GroupWork;
  
CREATE DATABASE [GroupWork]
ON  PRIMARY
  ( NAME = N'GroupWork', FILENAME =
    N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\GroupWork.mdf',
    SIZE = 5120KB , FILEGROWTH = 1024KB )
LOG ON
  ( NAME = N'GroupWorkLOG', FILENAME =
  N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\GroupWorkLOG.ldf',
  SIZE = 2048KB , FILEGROWTH = 10%);
GO

USE GroupWork;

IF EXISTS (SELECT * FROM sys.tables WHERE name = N'Content') DROP TABLE Content;

CREATE TABLE dbo.Content (
	work_group nvarchar(2) NOT NULL PRIMARY KEY,
	work_text nvarchar(max)
);

