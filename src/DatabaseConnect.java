package whiteboard;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;


public class DatabaseConnect {
	private DatabaseConnect() {}
    private static String SQL_database_name = "WhiteBoard";
	private static String SQL_instance_name = "whiteboard-258101:us-central1:whiteboard";
	private static String SQL_user = "root";
	private static String SQL_user_password = "Usc2019!";
	private static String SQL_Connection = "jdbc:mysql://google/";
	
	private static Connection createConn() {
		try {
			if (SQL_Connection.contains("google")) {
				return DriverManager.getConnection(SQL_Connection+SQL_database_name+"?cloudSqlInstance="
						+ SQL_instance_name+"&socketFactory=com.google.cloud.sql.mysql.SocketFactory"
						+ "&useSSL=false&user="+SQL_user+"&password="+SQL_user_password
						);
			}
			return DriverManager.getConnection(SQL_Connection + SQL_database_name, SQL_user, SQL_user_password);
		}
		catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		return null;
	}

    private static void terminateConnection(Connection conn, ResultSet rs, PreparedStatement pst) {
		try {
			if(conn!=null) {
				conn.close();
			}
			if(rs!=null) {
				rs.close();
			}
			if(pst!=null) {
				pst.close();
			}
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
    }
    
    public static boolean checkIfUserExists(String userName) {
		PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = null;
        boolean success = true;
		try {
            conn = createConn();
            pst = conn.prepareStatement("SELECT * FROM Users WHERE userName =?");
			pst.setString(1, userName);
			rs = pst.executeQuery();
			if(!rs.next()) {
				success = false;
			}
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}finally {terminateConnection(conn,rs,pst);}
		return success;
    }
    
    public static boolean checkIfVisitorExists(String userName) {
		PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = null;
        boolean success = true;
        System.out.print("Checking if "+userName+" exists... ");
		try {
            conn = createConn();
            pst = conn.prepareStatement("SELECT * FROM Visitors WHERE userName =?");
			pst.setString(1, userName);
			rs = pst.executeQuery();
			if(!rs.next()) {
				success = false;
				System.out.println("no!");
			}
			else {
				System.out.println("yes!");
			}
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}finally {terminateConnection(conn,rs,pst);}
		return success;
    }
    
    public static boolean queueExists(String queueName) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = null;
		boolean success = false;
		try {
			conn = createConn();
			pst = conn.prepareStatement("SELECT * FROM Queues WHERE queueName = ?");
			pst.setString(1, queueName);
			rs = pst.executeQuery();
			if(rs.next()) {
				success= true;
			}
			
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}finally {terminateConnection(conn,rs,pst);}
		return success;
    }
    
    public static String getQueueString(String queueName)
	{
		
		String resultStr = "";
		if(queueName == null || queueName.isEmpty())
		{
			return resultStr;
		}
		else {
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{
				conn = createConn();
				ps= conn.prepareStatement("SELECT * FROM Queues WHERE queueName=?");
				ps.setString(1,queueName);
				rs = ps.executeQuery();
			
				if(rs.next())
				{
					resultStr = rs.getString("queueOrder");
				}
			}
			catch(SQLException e)
			 {
				 System.out.println(e.getMessage());
			 } 
			 finally {terminateConnection(conn, rs, ps);}
		}
		return resultStr;
	}

    public static boolean setQueueString(String queueName, String updatedQueue)
	{
		
		boolean result_ = false;
		if(queueName == null || queueName.isEmpty())
		{
			return false;
		}
		else {
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{   
                if(queueExists(queueName))
				{
                    conn = createConn();
                    ps = conn.prepareStatement("Update Queues SET queueOrder=? WHERE queueName=?");
					ps.setString(1, updatedQueue);
					ps.setString(2, queueName);
					ps.executeUpdate();
					result_ = true;
				}
			}
			catch(SQLException e)
			 {
				 System.out.println(e.getMessage());
			 } 
			 finally {terminateConnection(conn, rs, ps); } 
				
			 }
		return result_;
	}

	public static boolean createUser(String userName, String hashedPass_)
	{
		boolean success = true;
		
		if(userName.isEmpty()) {
			success=false;
		}
		else {
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{
				if(!checkIfUserExists(userName))
				{
                    conn = createConn();
                    ps= conn.prepareStatement("INSERT INTO Users (userName, hashedPass) VALUES (?,?)");
					ps.setString(1, userName);
					String pswrd = "";
					pswrd = (hashedPass_==null?"":hashedPass_);
					ps.setString(2, pswrd);
					ps.executeUpdate();
				}
				else
				{
					success= false;
				}
			}
			catch(SQLException e)
			 {
				 System.out.println(e.getMessage());
			 } 
			 finally {terminateConnection(conn, rs, ps);}
		}
		
		return success;
	} 

    public static boolean deleteUser(String userName)
	{
		boolean success = false;
		
		if(userName == null || userName.isEmpty())
		{
			success=false;
		}
		else {
			Connection conn = null;
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			ResultSet rs = null;
			try
			{
				conn = createConn();
				ps= conn.prepareStatement("SELECT * FROM Users WHERE username=?");
				ps.setString(1,userName);
				rs = ps.executeQuery();
			
				if(rs.next())
				{
					if(rs.getString("hashedPass") == "")
					{
						int queuesInCheck = rs.getInt("numQueuesIn");
						if(queuesInCheck == 0)
						{
							ps2= conn.prepareStatement("DELETE FROM Users WHERE userName=?");
							ps2.setString(1,userName);
							ps2.executeUpdate();
							success = true;
						}
					}
				}
				else
				{
					success= false;
				}
			}
			catch(SQLException e)
			 {
				 System.out.println(e.getMessage());
			 } 
			 finally 
			 {
				try 
				{
					 if (rs != null) 
					 {
						 rs.close();
					 }
					 if (ps != null) 
					 {
						 ps.close();
					 }
					 if (ps2 != null) 
					 {
						 ps2.close();
					 }
					 if (conn != null) 
					 {
						 conn.close();
					 }
				 } 
				 catch (SQLException sqle) 
				 {
					 System.out.println(sqle.getMessage());
				 }
			 }
		}
		
		return success==true?true:false;
	
	}
    
    public static void addNewQueue(String queueName) {
    	if(queueName == null || queueName.isEmpty()) {
    		return;
    	}
    	else {
    		Connection conn = null;
    		PreparedStatement ps = null;
    		try {
    			conn = createConn();
    			ps = conn.prepareStatement("INSERT INTO Queues(queueName,queueOrder) VALUES(?,'')");
    			ps.setString(1, queueName);;
    			ps.executeUpdate();
    		}catch(SQLException sqle) {
    			System.out.println(sqle.getMessage());
    		}finally{terminateConnection(conn,null,ps);}
    	}
    }
    
    public static void addAdmintoQueue(String adminName, String queueName) {
    	if(queueName == null || queueName.isEmpty() || adminName == null ||adminName.isEmpty()) {
    		return ;
    	}
    	else {
    		Connection conn = null;
        	ResultSet rs = null;
        	ResultSet rs2 = null;
        	PreparedStatement ps = null;
        	PreparedStatement ps2 = null;
        	PreparedStatement ps3 = null;
        	Integer userID = null;
        	Integer queueID = null;
        	
        	try {
        		conn = createConn();
        		ps = conn.prepareStatement("SELECT u.UserID FROM Users u WHERE userName = ? ");
        		ps.setString(1, adminName);
        		rs = ps.executeQuery();
        		rs.first(); // should only have one because usernames are unique
        		userID = rs.getInt("UserID");
        		ps2 = conn.prepareStatement("SELECT q.queueID FROM Queues q WHERE queueName = ?");
        		ps2.setString(1, queueName);
        		rs2 = ps2.executeQuery();
        		rs2.first();//should only return one since queueNames are unique
        		queueID = rs2.getInt("queueID");
        		ps3 = conn.prepareStatement("INSERT INTO Admins(UserID, queueID) VALUES(?,?)");
        		ps3.setInt(1, userID);
        		ps3.setInt(2,queueID);
        		ps3.executeUpdate();
        	}catch(SQLException sqle) {
        		System.out.println(sqle.getMessage());
        	}finally {
        		terminateConnection(conn,rs,ps);
        		try {
        			if(rs2!=null) {
        				rs2.close();
        			}
        			if(ps2!=null) {
        				ps2.close();
        			}
        			if(ps3!=null) {
        				ps3.close();
        			}
        		}catch(SQLException sqle) {
        			System.out.println(sqle.getMessage());
        		}
        	}
    	}
    }
    
    public static boolean alreadyAdmin(String adminName, String queueName) {
    	boolean success = false;
    	if(queueName == null || queueName.isEmpty() || adminName == null ||adminName.isEmpty()) {
    		return false;
    	}
    	else {
    		Connection conn = null;
    		ResultSet rs = null;
    		ResultSet rs2 = null;
    		ResultSet rs3 = null;
    		PreparedStatement ps = null;
    		PreparedStatement ps2 = null;
    		PreparedStatement ps3 = null;
    		
    		Integer userID = null;
    		Integer queueID = null;
    		
    		
    		try {
    			conn = createConn();
        		ps = conn.prepareStatement("SELECT u.UserID FROM Users u WHERE userName = ? ");
        		ps.setString(1, adminName);
        		rs = ps.executeQuery();
        		rs.first(); // should only have one because usernames are unique
        		userID = rs.getInt("UserID");
        		ps2 = conn.prepareStatement("SELECT q.queueID FROM Queues q WHERE queueName = ?");
        		ps2.setString(1, queueName);
        		rs2 = ps2.executeQuery();
        		rs2.first();//should only return one since queueNames are unique
        		queueID = rs2.getInt("queueID");
        		
        		ps3 = conn.prepareStatement("SELECT * FROM Admins WHERE UserID = ? AND queueID = ?");
        		ps3.setInt(1, userID);
        		ps3.setInt(2,queueID);
        		rs3 = ps3.executeQuery();
        		if(rs3.next()) {
        			success = false;
        		}
        		
    		}catch(SQLException sqle) {
    			System.out.println(sqle.getMessage());
    		}finally{terminateConnection(conn,rs,ps);
    			try {
    				if(rs2!=null) {
    					rs2.close();
    				}
    				if(rs3!=null) {
    					rs3.close();
    				}
    				if(ps2!=null) {
    					ps2.close();
    				}
    				if(ps3!=null) {
    					ps3.close();
    				}
    			}catch(SQLException sqle) {
    				System.out.println(sqle.getMessage());
    			}
    		}
    	}
    	return success;
    	
    }

    public static void updateUserQueueInfo(String userName, String queueName)
	{
		if(userName == null || userName.isEmpty())
		{
			return;
		}
		else {
			Connection conn = null;
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			PreparedStatement ps3 = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			try
			{
				conn = createConn();
				ps= conn.prepareStatement("SELECT * FROM Users WHERE username=?");
				ps.setString(1,userName);
				rs = ps.executeQuery();
			
				if(rs.next())
				{
					ps2 = conn.prepareStatement("Select * from Queues WHERE queueName=?");
					ps2.setString(1, queueName);
					rs2 = ps2.executeQuery();
					//checks if the queue given exists
					if(rs2.next())
					{
						String queuesIn = rs.getString("queuesWaitingIn");
						int numQueues = rs.getInt("numQueuesIn");
						numQueues += 1;
						queuesIn += queueName +",";
						ps3 = conn.prepareStatement("UPDATE Users SET queuesWaitingIn=? , numQueuesIn=? WHERE userName=?");
						ps3.setString(1, queuesIn);
						ps3.setInt(2, numQueues);
						ps3.setString(3, userName);
						ps3.executeUpdate();
					}
				}
			}
			catch(SQLException e)
			 {
				 System.out.println(e.getMessage());
			 } 
			 finally 
			 {
				try 
				{
					 if(rs != null) rs.close();
					 if(rs2 != null) rs2.close();
					 if(ps != null) ps.close();
					 if(ps2 != null) ps2.close();
					 if(ps3 != null) ps3.close();
					 if (conn != null) conn.close();
				 } 
				 catch (SQLException sqle) 
				 {
					 System.out.println(sqle.getMessage());
				 }
			 }
		}
	}

    public static void setAdminString(String queueName, String newString) {
		PreparedStatement pst = null;
		Connection conn = null;
		try {
			conn = createConn();
			pst = conn.prepareStatement("UPDATE Queues SET adminList=? WHERE queueName=?");
			pst.setString(1, newString);
			pst.setString(2, queueName);
			pst.executeUpdate();
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}finally {
			terminateConnection(conn,null,pst);
		}
	}
 
    //unused
	//returns null if not found, admin string otherwise
    public static String getAdminString(String queueName) {
		String unparsedOrder=null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = createConn();
			pst = conn.prepareStatement("SELECT* FROM Queues WHERE queueName=?");
			pst.setString(1, queueName);
			rs = pst.executeQuery();
			if(rs.next()) {
				unparsedOrder = rs.getString("adminList");
			}
			
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}finally {
			terminateConnection(conn,rs,pst);
		}
		return unparsedOrder;
	}
    
   
    public static Boolean logIn(String username, String password) {
    	PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = null;
		Boolean result = false;
		try {
			conn = createConn();
			pst = conn.prepareStatement("SELECT * FROM Users WHERE userName=?");
			pst.setString(1, username);
			rs = pst.executeQuery();
			if(rs.next()) {
				result = true;
				String db_pass = rs.getString("hashedPass");
				if (!password.equals(db_pass)) result = false;
			}
			else {
				result = false;
			}
			
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}finally {
			terminateConnection(conn,rs,pst);
		}
		return result;
    }
    
    public static Vector<String> getAllQueues() {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Vector<String> results = new Vector<>();
		try {
			conn = createConn();
			pst = conn.prepareStatement("SELECT * FROM Queues");
			rs = pst.executeQuery();
			while (rs.next()) {
				results.add(rs.getString("queueName"));
			}
			
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}finally {
			terminateConnection(conn,rs,pst);
		}
		return results;
	}
    
    public static Vector<String> getVisitorQueues(String username) {
    	Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Vector<String> results = new Vector<String>();
		try {
			conn = createConn();
			pst = conn.prepareStatement("SELECT * FROM Visitors WHERE userName=? LIMIT 1");
			pst.setString(1, username);
			rs = pst.executeQuery();
			while (rs.next()) {
				String qs = rs.getString("queuesWaitingIn");
				String[] queues = qs.split(",");
				results = new Vector<String>(Arrays.asList(queues));
			}
			
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}finally {
			terminateConnection(conn,rs,pst);
		}
		return results;
    }
    
    public static void addVisitor(String visitor_name, String queue_name) {
    	Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = createConn();
			Boolean firstQ = false;
			System.out.println("Adding "+visitor_name+" to "+queue_name+".");
			if (!checkIfVisitorExists(visitor_name)) {
				PreparedStatement insert = conn.prepareStatement(
						"INSERT INTO Visitors(userName, numQueuesIn, queuesWaitingIn) "+
					    "VALUES (?,?,?)"
						);
				insert.setString(1, visitor_name);
				insert.setInt(2, 0);
				insert.setString(3, queue_name);
				insert.execute();
				firstQ = true;
			}
			pst = conn.prepareStatement(
					"UPDATE Visitors SET numQueuesIn=numQueuesIn+1, queuesWaitingIn=? "+
					"WHERE userName=?"
					);
			Vector<String> queues = getVisitorQueues(visitor_name);
			if (!firstQ) queues.add(queue_name);
			pst.setString(1, String.join(",", queues));
			pst.setString(2, visitor_name);
			pst.execute();
	
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}finally {
			terminateConnection(conn,null,pst);
		}
    }
    
    public static Vector<String> getUserQueues(String username) {
    	Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Vector<String> results = new Vector<>();
		try {
			conn = createConn();
			pst = conn.prepareStatement("SELECT q.queueName FROM Queues q WHERE q.queueID IN (SELECT a.queueID FROM Admins a WHERE a.UserID in (SELECT u.UserID FROM Users u where userName=?))");
			pst.setString(1, username);
			rs = pst.executeQuery();
			while (rs.next()) {
				results.add(rs.getString("queueName"));
			}
			
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}finally {
			terminateConnection(conn,rs,pst);
		}
		return results;
	}
	

	public static boolean deleteVisitor(String userName)
	{
		boolean success = false;
		
		if(userName == null || userName.isEmpty())
		{
			success=false;
		}
		else {
			Connection conn = null;
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			ResultSet rs = null;
			try
			{
				conn = createConn();
				ps= conn.prepareStatement("SELECT * FROM Visitors WHERE userName=?");
				ps.setString(1,userName);
				rs = ps.executeQuery();
				if(rs.next())
				{	
					int queuesInCheck = rs.getInt("numQueuesIn");
					if(queuesInCheck == 0)
					{
						ps2= conn.prepareStatement("DELETE FROM Visitors WHERE userName=?");
						ps2.setString(1,userName);
						ps2.executeUpdate();
						success = true;
					}
					else success = false;
				}
				else
				{
					success= false;
				}
			}
			catch(SQLException e)
			 {
				 System.out.println(e.getMessage());
			 } 
			 finally 
			 {
				try 
				{
					 if (rs != null) 
					 {
						 rs.close();
					 }
					 if (ps != null) 
					 {
						 ps.close();
					 }
					 if (ps2 != null) 
					 {
						 ps2.close();
					 }
					 if (conn != null) 
					 {
						 conn.close();
					 }
				 } 
				 catch (SQLException sqle) 
				 {
					 System.out.println(sqle.getMessage());
				 }
			 }
		}
		
		return success==true?true:false;
	
	}

	/*
		int Option is used to determine if we're adding or removing queues for a visitor
		Only two options 1 & 2
		1: Remove queue
		2: Add queue
	*/
	public static void updateVisitorQueueInfo(String userName, String queueName, int option)
	{
		if(userName == null || userName.isEmpty())
		{
			return;
		}
		else {
			Connection conn = null;
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			PreparedStatement ps3 = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			try
			{
				conn = createConn();
				//remove queue
				if(option == 1)
				{
					ps= conn.prepareStatement("SELECT * FROM Visitors WHERE userName=?");
					ps.setString(1,userName); 
					rs = ps.executeQuery();
					if(rs.next())
					{
						String queuesIn = rs.getString("queuesWaitingIn");
						int numQueues = rs.getInt("numQueuesIn");
						numQueues -= 1;
						Vector<String> queue_vector = new Vector<String>(Arrays.asList(queuesIn.split(",")));
						int index = queue_vector.indexOf(queueName);
						queue_vector.remove(index);
						StringBuilder str = new StringBuilder();
						for(int i=0;i<queue_vector.size();i++)
						{
							str.append(queue_vector.get(i).trim());
							if(i < (queue_vector.size() -1)) str.append(",");
						}
						queuesIn = str.length()==0?"":str.toString();
						ps3 = conn.prepareStatement("UPDATE Visitors SET queuesWaitingIn=? , numQueuesIn=? WHERE userName=?");
						ps3.setString(1, queuesIn);
						ps3.setInt(2, numQueues);
						ps3.setString(3, userName);
						ps3.executeUpdate();	
					}
				}
				//add queue
				else
				{
					ps= conn.prepareStatement("SELECT * FROM Visitors WHERE userName=?");
					ps.setString(1,userName); 
					rs = ps.executeQuery();
					if(rs.next())
					{
						String queuesIn = rs.getString("queuesWaitingIn");
						int numQueues = rs.getInt("numQueuesIn");
						numQueues += 1;
						Vector<String> queue_vector = new Vector<String>(Arrays.asList(queuesIn.split(",")));
						queue_vector.add(queueName.trim());
						StringBuilder str = new StringBuilder();
						for(int i=0;i<queue_vector.size();i++)
						{
							if(!queue_vector.get(i).trim().isEmpty())
							{
								str.append(queue_vector.get(i).trim());
								if(i < (queue_vector.size() -1)) str.append(",");
							}
						}
						queuesIn = str.toString();
						ps3 = conn.prepareStatement("UPDATE Visitors SET queuesWaitingIn=? , numQueuesIn=? WHERE userName=?");
						ps3.setString(1, queuesIn);
						ps3.setInt(2, numQueues);
						ps3.setString(3, userName);
						ps3.executeUpdate();	
					}
				}
				
			}
			catch(SQLException e)
			 {
				 System.out.println(e.getMessage());
			 } 
			 finally 
			 {
				try 
				{
					 if(rs != null) rs.close();
					 if(rs2 != null) rs2.close();
					 if(ps != null) ps.close();
					 if(ps2 != null) ps2.close();
					 if(ps3 != null) ps3.close();
					 if (conn != null) conn.close();
				 } 
				 catch (SQLException sqle) 
				 {
					 System.out.println(sqle.getMessage());
				 }
			 }
		}
	}

	/*
		int Option is used to determine if we're adding or removing queues for a visitor
		Only two options 1 & 2
		1: Remove queue
		2: Add queue
	*/
	public static void updateUserQueueInfo(String userName, String queueName, int option)
	{
		if(userName == null || userName.isEmpty())
		{
			return;
		}
		else {
			Connection conn = null;
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			PreparedStatement ps3 = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			try
			{
				conn = createConn();
				//remove queue
				if(option == 1)
				{
					ps= conn.prepareStatement("SELECT * FROM Users WHERE userName=?");
					ps.setString(1,userName); 
					rs = ps.executeQuery();
					if(rs.next())
					{
						String queuesIn = rs.getString("queuesWaitingIn");
						int numQueues = rs.getInt("numQueuesIn");
						numQueues -= 1;
						Vector<String> queue_vector = new Vector<String>(Arrays.asList(queuesIn.split(",")));
						int index = queue_vector.indexOf(queueName);
						queue_vector.remove(index);
						StringBuilder str = new StringBuilder();
						for(int i=0;i<queue_vector.size();i++)
						{
							str.append(queue_vector.get(i).trim());
							if(i < (queue_vector.size() -1)) str.append(",");
						}
						queuesIn = str.length()==0?"":str.toString();
						
						ps3 = conn.prepareStatement("UPDATE Users SET queuesWaitingIn=? , numQueuesIn=? WHERE userName=?");
						ps3.setString(1, queuesIn);
						ps3.setInt(2, numQueues);
						ps3.setString(3, userName);
						ps3.executeUpdate();	
					}
				}
				//add queue
				else
				{
					ps= conn.prepareStatement("SELECT * FROM Users WHERE userName=?");
					ps.setString(1,userName); 
					rs = ps.executeQuery();
					if(rs.next())
					{
						String queuesIn = rs.getString("queuesWaitingIn");
						int numQueues = rs.getInt("numQueuesIn");
						numQueues += 1;
						Vector<String> queue_vector = new Vector<String>(Arrays.asList(queuesIn.split(",")));
						queue_vector.add(queueName.trim());
						StringBuilder str = new StringBuilder();
						for(int i=0;i<queue_vector.size();i++)
						{
							if(!queue_vector.get(i).trim().isEmpty())
							{
								str.append(queue_vector.get(i).trim());
								if(i < (queue_vector.size() -1)) str.append(",");
							}
						}
						queuesIn = str.toString();
						ps3 = conn.prepareStatement("UPDATE Users SET queuesWaitingIn=? , numQueuesIn=? WHERE userName=?");
						ps3.setString(1, queuesIn);
						ps3.setInt(2, numQueues);
						ps3.setString(3, userName);
						ps3.executeUpdate();	
					}
				}
				
			}
			catch(SQLException e)
			 {
				 System.out.println(e.getMessage());
			 } 
			 finally 
			 {
				try 
				{
					 if(rs != null) rs.close();
					 if(rs2 != null) rs2.close();
					 if(ps != null) ps.close();
					 if(ps2 != null) ps2.close();
					 if(ps3 != null) ps3.close();
					 if (conn != null) conn.close();
				 } 
				 catch (SQLException sqle) 
				 {
					 System.out.println(sqle.getMessage());
				 }
			 }
		}
	}
	
	public static Vector<String> getUsersQueuesIn(String username) {
    	Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Vector<String> results = new Vector<String>();
		try {
			conn = createConn();
			pst = conn.prepareStatement("SELECT * FROM Users WHERE userName=? LIMIT 1");
			pst.setString(1, username);
			rs = pst.executeQuery();
			while (rs.next()) {
				String qs = rs.getString("queuesWaitingIn");
				String[] queues = qs.split(",");
				results = new Vector<String>(Arrays.asList(queues));
			}
			
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}finally {
			terminateConnection(conn,rs,pst);
		}
		return results;
    }


}
