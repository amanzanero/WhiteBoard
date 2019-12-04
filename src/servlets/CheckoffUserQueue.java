package servlets;
import whiteboard.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CheckoffUserQueue
 */
@WebServlet("/CheckoffUserQueue")
public class CheckoffUserQueue extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public CheckoffUserQueue() 
    {
        super();
       
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
       //gauranteed all people sent exist
        String personToRemove = request.getParameter("userToRemove");
        String queueToUpdate = request.getParameter("queueName");
        
        Vector<String> personQueues = DatabaseConnect.getVisitorQueues(personToRemove);
        //visitor is only in one queue 
        if(personQueues.size() == 1)
        {          
            removePersonFromQueue(personQueues, queueToUpdate,personToRemove);
            DatabaseConnect.deleteVisitor(personToRemove);
        }
        else
        {
            removePersonFromQueue(personQueues, queueToUpdate, personToRemove);
            DatabaseConnect.updateVisitorQueueInfo(personToRemove, queueToUpdate, 1);
        }

        
        
        
        try {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dashboard.jsp");
			dispatcher.forward(request, response);
		}
		catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		}

    }
    
    private void removePersonFromQueue(Vector<String> personQueues, String queueName, String personToRemove)
    {
        int index1 = personQueues.indexOf(queueName);
        String queueString = DatabaseConnect.getQueueString(personQueues.get(index1));
        System.out.println("Before Remove: "+queueString);
        Vector<String> queue_vector = new Vector<String>(Arrays.asList(queueString.split(",")));
        int index2 = queue_vector.indexOf(personToRemove);
        queue_vector.remove(index2);
        queueString = buildQueueString(queue_vector);
        System.out.println("After Remove: "+queueString);
        DatabaseConnect.setQueueString(personQueues.get(index1), queueString);
    }

    private String buildQueueString(Vector<String> in)
	{
		StringBuilder str = new StringBuilder();
        for(int i=0;i<in.size();i++)
        {
            str.append(in.get(i).trim());
            if(i != in.size() -1 )str.append(",");
        }
		return str.toString();
    }
    
}
