/*
       document : Register.java
     created on : 2019 november 18, 16:16 pm (monday)
         author : audrey bongalon
      USC email : bongalon@usc.edu
    description : csci201 final project - java servelet for user registration. for whiteboard

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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import java.io.IOException;




@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

    // constructor
    public Register() {
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
		String sendBack = "/registration.jsp";
		String username = null, password = null, pwConfirm = null;

		username = request.getParameter("username");
		password = request.getParameter("password");
		pwConfirm = request.getParameter("pw-confirm");
		if (username != null && username.isEmpty()) username = null;
		if (password != null && password.isEmpty()) password = null;
		if (pwConfirm != null && pwConfirm.isEmpty()) pwConfirm = null;

		System.out.println("attempting to register with the following credentials:");
		System.out.println("------------------------------------------------------");
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		System.out.println("confirm: " + pwConfirm);
		System.out.println();




		// if-else statements control flow for outputting the correct password
		// errors (don't want a "password does not match" if password is blank),
		// but that's not the same flow as whether or not you can register, and
		// i don't want to call the functions twice, so i'm using a boolean
		boolean canRegister = true;

		if (!isValidUsername(username)) {
			System.out.println("username is empty!");
			request.setAttribute("usernameError", "Please enter a username.");
			forwardMe = sendBack;
			canRegister = false;
		}
		if (!isValidPassword(password)) {
			System.out.println("password is empty!");
			request.setAttribute("passwordError", "Please enter a password.");
			forwardMe = sendBack;
			canRegister = false;
		}
		else if (!password.equals(pwConfirm)) {
			System.out.println("passwords do not match!");
			request.setAttribute("confirmError", "Passwords do not match.");
			forwardMe = sendBack;
			canRegister = false;
		}
		else if (DatabaseConnect.checkIfUserExists(username)) {
			System.out.println("username already taken");
			request.setAttribute("usernameError", "This username is already taken.");
			forwardMe = sendBack;
			canRegister = false;
		}

		if (canRegister) {
			// username as salt
			DatabaseConnect.createUser(username, SHA256.hash(username + password));

			// if there's an old session, end it
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }

            // create a new session to store the user (5 min duration)
            HttpSession newSession = request.getSession(true);
            newSession.setMaxInactiveInterval(5 * 60);
            newSession.setAttribute("username", username);
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

