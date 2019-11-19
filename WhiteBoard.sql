CREATE DATABASE WhiteBoard;

CREATE TABLE Users(
	UserID INT(11) NOT NULL Primary key AUTO_INCREMENT, 
	userName VARCHAR(255) NOT NULL,
	hashedPass VARCHAR(255) NOT NULL,
	numQueuesIn INT(11) not null,
	queuesWaitingIn Varchar(255) not null,	
	isAdminOf VARCHAR(1000) not null
);

CREATE TABLE Queues(
	queueID INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	queueName VARCHAR(255) NOT NULL,
	queueOrder VARCHAR(2500) Not null ,
	adminList VARCHAR(1000) not null
);
