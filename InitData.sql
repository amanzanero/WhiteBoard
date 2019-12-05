Use WhiteBoard;

INSERT INTO Visitors (userName, numQueuesIn, queuesWaitingIn)
VALUES
    ("ttrojan", 1, "CSCI201"),
    ("jbruin", 2, "CSCI201,CSCI270"),
    ("ttraveler", 1, "CSCI201");

INSERT INTO Queues (queueName, queueOrder)
VALUES
    ("CSCI201", "ttrojan,ttraveler,jbruin"),
    ("CSCI270", "jbruin");

INSERT INTO Users (userName, hashedPass)
VALUES
    ("testcp",  "-5d7d0b0e5d08ed1888eb327d07cff4de8d0baea547a0b1c"),    -- testcp,  password
    ("testcp1", "-49274143cd467aee95e96b5d0038c640b78c39cc5003e3b1"),   -- testcp1, password
    ("a",       "-22a6a0bfb897101927dd6ce231a26e7a1281c60d71c9ef69");	-- a,       a

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
