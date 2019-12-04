CREATE DATABASE WhiteBoard;

USE WhiteBoard;

CREATE TABLE Visitors(
	visitorID INT(11) NOT NULL Primary key AUTO_INCREMENT, 
	userName VARCHAR(255) NOT NULL,
	numQueuesIn INT not null,
	queuesWaitingIn Varchar(255) not null	
);

CREATE TABLE Users(
	UserID INT(11) NOT NULL Primary key AUTO_INCREMENT, 
	userName VARCHAR(255) NOT NULL,
	hashedPass VARCHAR(255) NOT NULL
);

CREATE TABLE Queues(
	queueID INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	queueName VARCHAR(255) NOT NULL,
	queueOrder VARCHAR(2500) Not null
);

CREATE TABLE Admins(
	AdminID INT(11) NOT NULL Primary key AUTO_INCREMENT, 
	UserID INT(11) NOT NULL, 
	queueID INT(11) NOT NULL,
	FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (queueID) REFERENCES Queues(queueID)
);
