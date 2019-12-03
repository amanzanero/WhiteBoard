/*
       document : Logout.java
     created on : 2019 november 18, 16:13 pm (monday)
         author : audrey bongalon
      USC email : bongalon@usc.edu
    description : csci201 final project - java servelet for logging out. for whiteboard

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




@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

    // constructor
    public Logout() {
        super();
    }




	// upon HTML request
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String redirectToMe = "homepage.jsp";

		HttpSession session = request.getSession(false);
        String username = null;
        if (session != null) {
        	username = (String)session.getAttribute("username");
            System.out.println("logging out user: " + username);
    		System.out.println();
    		
    		// end the user session
    		session.invalidate();
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

