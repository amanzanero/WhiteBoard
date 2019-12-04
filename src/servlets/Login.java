/*
       document : Login.java
     created on : 2019 november 18, 16:15 pm (monday)
         author : audrey bongalon
      USC email : bongalon@usc.edu
    description : csci201 final project - java servelet for logging in. for whiteboard

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
import whiteboard.*;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    // constructor
    public Login() {
        super();
    }

    /* input validation */

	// valid names are only letters and spaces
    private boolean isValidUsername(String username) {
		if (username == null) return false;
    	return !username.isEmpty();
	}

	private boolean isValidPassword(String password) {
		if (password == null) return false;
		return !password.isEmpty();
	}




	// upon HTML request
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forwardMe = "/homepage.jsp";
		String sendBack = "/login.jsp";
		String username = null, password = null;

		username = request.getParameter("username");
		password = request.getParameter("password");
		if (username != null) {
		    username = username.trim();
		    if (username.isEmpty()) {
		    	username = null;
			}
		}
		if (password != null) {
			password = password.trim();
			if (password.isEmpty()) {
				password = null;
			}
		}



		System.out.println("attempting to log in with the following credentials:");
		System.out.println("------------------------------------------------------");
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		System.out.println();




		// if-else statements control flow for outputting the correct username
		// and password, but that's not the same flow as whether or not you can
		// register, and i don't want to call the functions twice, so i'm using
		// a boolean
		boolean canLogIn = true;

		if (!isValidUsername(username)) {
			System.out.println("username is empty!");
			request.setAttribute("usernameError", "Please enter a username.");
			forwardMe = sendBack;
			canLogIn = false;
		}
		if (!isValidPassword(password)) {
			System.out.println("password is empty!");
			request.setAttribute("passwordError", "Please enter your password.");
			forwardMe = sendBack;
			canLogIn = false;
		}
		// check "canLogIn" so we dont override the "no username" error
		else if (canLogIn && !DatabaseConnect.checkIfUserExists(username)) {
			System.out.println("user does not exist");
			request.setAttribute("usernameError", "The user does not exist.");
			forwardMe = sendBack;
			canLogIn = false;
		}

		if (canLogIn) {
			if (DatabaseConnect.logIn(username, password)) {
				// if there's an old session, end it
	            HttpSession oldSession = request.getSession(false);
	            if (oldSession != null) {
	                oldSession.invalidate();
	            }

	            // create a new session to store the user (5 min duration)
	            HttpSession newSession = request.getSession(true);
	            newSession.setMaxInactiveInterval(15 * 60);	// 15 mins
	            newSession.setAttribute("username", username);

	            System.out.println("logging in as " + username);
			}
			else {
				System.out.println("WRONG PASSWORD");
				request.setAttribute("passwordError", "Incorrect password.");
				forwardMe = sendBack;
			}
		}

		// dispatch the results
		try {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(forwardMe);
			dispatcher.forward(request, response);
		}
		catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
}

