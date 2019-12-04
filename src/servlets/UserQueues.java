package servlets;

import java.io.IOException;
import java.util.Vector;
import whiteboard.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserQueues
 */
@WebServlet("/UserQueues")
public class UserQueues extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserQueues() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String visitor = request.getParameter("visitorname");
		String forward = null;
		forward = "/homepage.jsp";
		if (!DatabaseConnect.checkIfVisitorExists(visitor)) {
			request.setAttribute("visitorError", true);
			request.removeAttribute("visitorQueues");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(forward);
			dispatcher.forward(request, response);
			return;
		}
		Vector<String> queues = DatabaseConnect.getVisitorQueues(visitor);
		request.setAttribute("visitorQueues", queues);
		request.setAttribute("visitorName", visitor);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(forward);
		dispatcher.forward(request, response);
}


}
