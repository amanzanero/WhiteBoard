package servlets;
import whiteboard.*;
import whiteboard.*;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ToQueue
 */
@WebServlet("/ToQueue")
public class ToQueue extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToQueue() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward = null;
		String qName = request.getParameter("queueName");
		if (qName.isEmpty()) {
			forward = "/homepage.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(forward);
			request.setAttribute("queueError", "Queue does not exist");
			dispatcher.forward(request, response);
		}
		else if (!DatabaseConnect.queueExists(qName)) {
			forward = "/homepage.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(forward);
			request.setAttribute("queueError", "Queue does not exist");
			dispatcher.forward(request, response);
		}
		else {
			response.sendRedirect("queue.jsp?course="+qName);
		}
	}
}
