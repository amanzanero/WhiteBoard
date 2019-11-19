package whiteboard;

import java.sql.*;

public class DatabaseConnect {
		
	static String jdbcConnect = "jdbc:mysql://google/WhiteBoard?cloudSqlInstance=whiteboard-258101:us-central1:whiteboard&socke"+
	"tFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root"+
	"&password=Usc2019!";
	
	static Connection conn = null;
	
	
	private static Connection establishConnection() {
		try {
			conn = DriverManager.getConnection(jdbcConnect);
			
			
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		return conn;
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
		try {
			pst = establishConnection().prepareStatement("SELECT * FROM Users WHERE userName =?");
			pst.setString(1, userName);
			rs = pst.executeQuery();
			if(!rs.next()) {
				return false;
			}
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}finally {terminateConnection(conn,rs,pst);}
		return true;
	}
	
	public static String getQueueString(String queueName){
		PreparedStatement pst = null;
		ResultSet rs = null;
		String unparsedOrder=null;
		try {
			pst = establishConnection().prepareStatement("SELECT queueOrder FROM Queues WHERE queueName=?");
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
	
	public static void setQueueString(String queueName, String updatedQueue) {
		PreparedStatement pst = null;
		try {
			pst = establishConnection().prepareStatement("UPDATE Queues SET queueOrder=? WHERE queueName=?");
			pst.setString(1, updatedQueue);
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
	
	public static int createUser(String username, String password) {
		PreparedStatement pst = null;
		String insertPassword = password;
		if(password == null || password.equals("")) {
			insertPassword = "";
		}
		if(checkIfUserExists(username)) {
			
			try {
				pst = establishConnection().prepareStatement("INSERT INTO Users(userName, hashedPass,numQueuesIn,queuesWaitingIn,isAdminOf"+
			") VALUES(?,?,0,'','')");
				pst.setString(1,username);
				pst.setString(2, insertPassword);
				pst.executeUpdate();
				
			}catch(SQLException sqle) {
				System.out.println(sqle.getMessage());
			}finally {terminateConnection(conn,null,pst);}
			return 1;
		}
		else {return -1;}
	}
	
	public static boolean queueExists(String queueName) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = null;
		boolean success = true;
		try {
			conn = DriverManager.getConnection();
			pst = conn.prepareStatement("SELECT * FROM Queues WHERE queueName = ?;");
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
	
	
	
	
}




