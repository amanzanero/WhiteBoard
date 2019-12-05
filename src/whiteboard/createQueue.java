package whiteboard;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class createQueue
 */
@WebServlet("/createQueue")
public class createQueue extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public createQueue() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String redirectToMe = "dashboard.jsp";
		String queueName = null;
		String adminName = null;
		Object currUser = null;
		HttpSession sesh = request.getSession();

		currUser = sesh.getAttribute("username");

		if(currUser!=null) {
			adminName = currUser.toString();
		}
		queueName = request.getParameter("newName");
		if(queueName!=null) {
			queueName = queueName.trim();
			if(queueName.isEmpty()) {
				queueName = null;
			}
		}

		String goBack = "/dashboard.jsp";

		System.out.println(queueName);
		if(DatabaseConnect.queueExists(queueName)) {
			request.setAttribute("alreadyExists", "That queue already exists.");
		}
		else {
			DatabaseConnect.addNewQueue(queueName);
			if(!DatabaseConnect.isAdmin(adminName, queueName)) {
				DatabaseConnect.addAdmintoQueue(adminName, queueName);
			}
		}

//		else if(queueName.equals("") ||queueName==null) {
//			request.setAttribute("", o);
//		}
		try {
//			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(goBack);
//			dispatcher.forward(request, response);
			response.sendRedirect(redirectToMe);
		}
		catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
