package servlets;

import java.util.ArrayList;

public class Queue {
	//Data Structure storing the String
	private ArrayList<String> queue;
	private ArrayList<String> admins;
	private String queueName;
	
	//Default Constructor
	public Queue(String stringFromDatabase, String queueName) {
		queue = parseQueueString(stringFromDatabase);
		admins = new ArrayList<String>(0);
		this.queueName = queueName;
	}

	//reads a string from the SQL database and converts it into a Queue object
	public static ArrayList<String> parseQueueString(String stringFromDatabase) {
		String[] names = stringFromDatabase.split(",");
		ArrayList<String> parsedQueue = new ArrayList<String>();
		for (int i=0; i<names.length;i++) {
			parsedQueue.add(names[i].trim());
		}
		return parsedQueue;
	}

	//parses queue into a String that will be stored in the SQL database
	public String dumpString() {
		String list = String.join(",", 	queue);
		return list;
	}

	//Gets the name of the Queue
	public String getQueueName() {
		return queueName;
	}

	//gets the classQueue
	public ArrayList<String> getQueue(){
		return queue;
	}

	//inserts a user into an existing queue
	public void insertUsertoQueue(String userID) {
		if (queue.indexOf(userID) == -1) {
			queue.add(userID);
		}
		
	}
	
	//returns true if a user exists in the queue
	public boolean userInQueue(String username) {
		if (queue.indexOf(username) != -1) {
			return true;
		}
		return false;
	}
	
	//returns the queue size
	public int getQueueSize() {
		return queue.size();
	}
	
	//changes the position of a single user in the queue
	public void changePosition(String username, int index, String adminName) {
		if (admins.indexOf(adminName) != -1) {
			int userPosition = queue.indexOf(username);
			if (userPosition != -1) {
				String tempUser = queue.get(userPosition);
				queue.remove(userPosition);
				queue.add(index, tempUser);
			}
		}
	}
	
	//returns the index of a user in the queue
	public int getUsersPlaceInQueue(String username) {
		return queue.indexOf(username);
	}
	
	//
	public void addAdminToQueue(String adminName) {
		if (admins.indexOf(adminName) == -1) {
			admins.add(adminName);
		}
	}
	
}
