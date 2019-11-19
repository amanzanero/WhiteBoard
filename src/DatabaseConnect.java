
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnect {
	private DatabaseConnect() {}
    private static String SQL_database_name = "";
	private static String SQL_instance_name = "";
	private static String SQL_user = "";
	private static String SQL_user_password = "";
	private static String SQL_Connection = "jdbc:mysql://google/"+SQL_database_name+"?cloudSqlInstance="
			+ SQL_instance_name+"&socketFactory=com.google.cloud.sql.mysql.SocketFactory"
			+ "&useSSL=false&user="+SQL_user+"&password="+SQL_user_password;

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
            conn = DriverManager.getConnection(SQL_Connection);
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
    
    public static boolean queueExists(String queueName) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = null;
		boolean success = true;
		try {
			conn = DriverManager.getConnection(SQL_Connection);
			pst = conn.prepareStatement("SELECT * FROM Queues WHERE queueName = ?");
			pst.setString(1, queueName);
			rs = pst.executeQuery();
			if(!rs.next()) {
				success= false;
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
				conn = DriverManager.getConnection(SQL_Connection);
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
                    conn = DriverManager.getConnection(SQL_Connection);
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
		
		if(userName.isEmpty())
		{
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
                    conn = DriverManager.getConnection(SQL_Connection);
                    ps= conn.prepareStatement("INSERT INTO Users (userName, hashedPass, numQueuesIn, queuesWaitingIn, isAdminOf) VALUES (?,?,?,?,?)");
					ps.setString(1, userName);
					String pswrd = "";
					pswrd = (hashedPass_==null?"":hashedPass_);
					ps.setString(2, pswrd);
					ps.setInt(3, 0);
					ps.setString(4, "");
					ps.setString(5, "");
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
		
		return success==true?true:false;
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
				conn = DriverManager.getConnection(SQL_Connection);
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
				conn = DriverManager.getConnection(SQL_Connection);
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
		try {
			pst = establishConnection().prepareStatement("UPDATE Queues SET adminList=? WHERE queueName=?");
			pst.setString(1, newString);
			pst.setString(2, queueName);
			pst.executeUpdate();
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}finally {
			terminateConnection(conn,null,pst);
		}
	}

    public static String getAdminString(String queueName) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		String unparsedOrder=null;
		try {
			pst = establishConnection().prepareStatement("SELECT  FROM Queues WHERE queueName=?");
			pst.setString(1, queueName);
			rs = pst.executeQuery();
			if(!rs.next()) {
				return null;//if nothing found return null
			}
			unparsedOrder = rs.toString();
			
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}finally {
			terminateConnection(conn,rs,pst);
		}
		return unparsedOrder;
	}


}
