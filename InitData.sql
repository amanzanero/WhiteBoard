Use WhiteBoard;

INSERT INTO Visitors (userName, numQueuesIn, queuesWaitingIn)
VALUES
    ("ttrojan", 1, "CSCI201"),
    ("jbruin", 2, "CSCI201, CSCI270"),
    ("ttraveler", 1, "CSCI201");

INSERT INTO Queues (queueName, queueOrder)
VALUES
    ("CSCI201", "ttrojan,ttraveler,jbruin"),
    ("CSCI270", "jbruin");

INSERT INTO Users (userName, hashedPass)
VALUES
    ("testcp", "password"),
    ("testcp1", "password");

INSERT INTO Admins (UserID, queueID)
VALUES 
(
	(SELECT UserID FROM Users WHERE userName='testcp'),
	(SELECT queueID FROM Queues WHERE queueName='CSCI270')
),
(
	(SELECT UserID FROM Users WHERE userName='testcp'),
	(SELECT queueID FROM Queues WHERE queueName='CSCI201')
),
(
	(SELECT UserID FROM Users WHERE userName='testcp1'),
	(SELECT queueID FROM Queues WHERE queueName='CSCI270')
);
