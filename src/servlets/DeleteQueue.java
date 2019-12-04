/*
       document : DeleteQueue.java
     created on : 2019 december 3, 19:50 pm (tuesday)
         author : audrey bongalon
      USC email : bongalon@usc.edu
    description : csci201 final project - java servelet for deleting queues

                               ___
                              /\  \
      ______    ___  ___      \_\  \    ______    ______   ___  ___
     /  __  \  /\  \/\  \    /  __  \  /\   __\  /  __  \ /\  \/\  \
    /\  \L\  \ \ \  \_\  \  /\  \L\  \ \ \  \_/ /\   ___/ \ \  \_\  \
    \ \___/\__\ \ \___/\__\ \ \___/\__\ \ \__\  \ \_____\  \ \____   \
     \/__/\/__/  \/___/\__/  \/___/\__/  \/__/   \/_____/   \/___/_\  \
                                                               /\_____/
                                                               \/____/
*/

package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;




@WebServlet("/DeleteQueue")
public class DeleteQueue extends HttpServlet {
	private static final long serialVersionUID = 1L;

    // constructor
    public DeleteQueue() {
        super();
    }




	// upon HTML request
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String redirectToMe = "dashboard.jsp";

        HttpSession session = request.getSession(false);
        String adminUsername = null;
        if (session != null) {
             adminUsername = (String)session.getAttribute("username");
        }
        if (adminUsername == null) {	// not "else" because getAttribute() might return null
            System.out.println("attempted to remove user without logging in");
        }

		// dispatch the results
		try {
			response.sendRedirect(redirectToMe);
		}
		catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
}

