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
("testcp", "lJE8LUgEgNwffkqNInjxfvgkmqw6l7PzJ3C6nV0E9yo="),		-- testcp,  password
("testcp1", "LSEqy58yvP+KssY9jraz+b7b0YUfv4GZfsV9UnAFOJg="),	-- testcp1, password
("a", "lhtt0+3jy47LqsvWjeBAzXjrLtWIkTDM60xJJo6k1QY=");			-- a,       a

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
