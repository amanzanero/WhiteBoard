package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JoinQueue
 */
@WebServlet("/JoinQueue")
public class JoinQueue extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinQueue() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String visitor_name = request.getParameter("visitorAdd");
		String course = request.getParameter("course");
		String forward = null;
		String qString = DatabaseConnect.getQueueString(course);
		Queue queue = new Queue(qString, course);
		if (visitor_name.isEmpty()) {
			forward = "/queue.jsp";
			request.setAttribute("addError", "enter a username!");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(forward);
			dispatcher.forward(request, response);
			return;
		}
		else if (queue.userInQueue(visitor_name)) {
			System.out.println(visitor_name+" is already in "+course);
			forward = "/queue.jsp";
			request.setAttribute("addError", "user is already in the queue!");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(forward);
			dispatcher.forward(request, response);
			return;
		}
		queue.insertUsertoQueue(visitor_name);
		System.out.println("String: "+queue.dumpString());
		DatabaseConnect.setQueueString(course, queue.dumpString());
		DatabaseConnect.addVisitor(visitor_name, course);
		forward = "queue.jsp?course="+course+"&visitorname="+visitor_name;
		response.sendRedirect(forward);
	}

}
