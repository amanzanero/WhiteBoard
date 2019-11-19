
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


//creates a new user given that all emails are unique
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
			PreparedStatement ps2 = null;
			ResultSet rs = null;
			try
			{
				conn = DriverManager.getConnection(SQL_Connection);
				ps= conn.prepareStatement("SELECT * FROM Users WHERE username=?");
				ps.setString(1,userName);
				rs = ps.executeQuery();
			
				//stills checks if username is already taken
				if(!rs.next())
				{
					ps2= conn.prepareStatement("INSERT INTO Users (userName, hashedPass, numQueuesIn, queuesWaitingIn, isAdminOf) VALUES (?,?,?,?,?)");
					ps2.setString(1, userName);
					String pswrd = "";
					pswrd = (hashedPass_==null?"":hashedPass_);
					ps2.setString(2, pswrd);
					ps2.setInt(3, 0);
					ps2.setString(4, "");
					ps2.setString(5, "");
					ps2.executeUpdate();
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

	public static boolean createAdmin(String userName, String hashedpass)
	{
		boolean success = true;
		
		if(userName.isEmpty() || hashedpass.isEmpty())
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
			
				//stills checks if username is already taken
				if(!rs.next())
				{
					ps2= conn.prepareStatement("INSERT INTO Users (userName, hashedPAss) VALUES (?,?)");
					ps2.setString(1, userName);
					ps2.setString(2, hashedpass);
					ps2.executeUpdate();
				
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

	public static void updateUser(String userName, String queueInsert)
	{

	}

//deletes user from database if it exists
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

// Returns an empty string if the queue does not exist
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
		return resultStr;
	}

	//updates the queue order in the database
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
			PreparedStatement ps2 = null;
			ResultSet rs = null;
			try
			{
				conn = DriverManager.getConnection(SQL_Connection);
				ps= conn.prepareStatement("SELECT * FROM Queues WHERE queueName=?");
				ps.setString(1,queueName);
				rs = ps.executeQuery();
			
				if(rs.next())
				{
					ps2 = conn.prepareStatement("Update Queues SET queueOrder=? WHERE queueName=?");
					ps2.setString(1, updatedQueue);
					ps2.setString(2, queueName);
					ps2.executeUpdate();
					result_ = true;
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
					 if (conn != null) 
					 {
						 conn.close();
					 }
					 if(ps2 != null)
					 {
						 ps2.close();
					 }
				 } 
				 catch (SQLException sqle) 
				 {
					 System.out.println(sqle.getMessage());
				 }
			 }
		}
		return result_;
	}


//if queue does not exist, it returns null
	public static String[] getQueueOrder(String queueName)
	{
		String[] queueOrder = null;




		return queueOrder;
	}



}
