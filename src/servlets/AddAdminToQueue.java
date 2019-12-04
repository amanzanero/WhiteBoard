package servlets;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import whiteboard.DatabaseConnect;

/**
 * Servlet implementation class addAdminToQueue
 */
@WebServlet("/AddAdminToQueue")
public class AddAdminToQueue extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public AddAdminToQueue() {
        super();
     
    }
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
      String queueToUpdate = request.getParameter("queueName");
      String userToAdd = request.getParameter("userToAdd");
      
      DatabaseConnect.addAdmintoQueue(userToAdd, queueToUpdate);
      
      try {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dashboard.jsp");
			dispatcher.forward(request, response);
		}
		catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
      
    }
}