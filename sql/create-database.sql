CREATE DATABASE YOUTUBEDATADB;
GO

USE YOUTUBEDATADB;
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[video_details](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[title] [varchar](500) NOT NULL,
	[description] [varchar](1000) NULL,
	[thumbnail_url] [varchar](200) NULL,
	[publish_datetime] [datetime] NOT NULL,
 CONSTRAINT [PK_vedio_details] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [IX_publish_datetime] ON [dbo].[video_details]
(
	[publish_datetime] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO

CREATE NONCLUSTERED INDEX [IX_title] ON [dbo].[video_details]
(
	[title] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO