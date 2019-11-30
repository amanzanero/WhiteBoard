<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!--
       document : registration.jsp
     created on : 2019 november 18, 16:02 pm (monday)
         author : audrey bongalon
      USC email : bongalon@usc.edu
         USC ID : 9152272619
    description : csci201 final project - registration page for whiteboard (queuing program)

                               ___
                              /\  \
      ______    ___  ___      \_\  \    ______    ______   ___  ___
     /  __  \  /\  \/\  \    /  __  \  /\   __\  /  __  \ /\  \/\  \
    /\  \L\  \ \ \  \_\  \  /\  \L\  \ \ \  \_/ /\   ___/ \ \  \_\  \
    \ \___/\__\ \ \___/\__\ \ \___/\__\ \ \__\  \ \_____\  \ \____   \
     \/__/\/__/  \/___/\__/  \/___/\__/  \/__/   \/_____/   \/___/_\  \
                                                               /\_____/
                                                               \/____/
-->

<html lang="en">
<head>
     <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/styles.css">
    <title>WhiteBoard - Registration</title>
    <link href="https://fonts.googleapis.com/css?family=Raleway|Open+Sans" rel="stylesheet" type="text/css">
</head>
<body>
    <div id="everything">
        <div class="bg-image"></div>
        <nav class="navbar navbar-expand-lg bg-dark navbar-dark justify-content-end">
		    <a class="navbar-brand ml-1 mr-auto" href="homepage.jsp">WhiteBoard</a>
		    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent">
		        <span class="navbar-toggler-icon"></span>
		    </button>
		</nav>
        <div id="main-wrapper"><div id="main" style="display: flex; flex-direction: column; align-items: center;">
        <div class="card w-50 d-flex flex-column align-items-center m-5" style="padding: 1em;">
            <%
                /* data will get sent back in case of error */
                String username = request.getParameter("username");
                if (username == null) username = "";

                String password = request.getParameter("password");
                if (password == null) password = "";

                /* error messages will be sent if data is invalid */
                Object usernameError = request.getAttribute("usernameError");
                Object passwordError = request.getAttribute("passwordError");
                Object confirmError =  request.getAttribute("confirmError");

                if (usernameError != null)  usernameError = (String)usernameError;
                if (passwordError != null)  passwordError = (String)passwordError;
                if (confirmError != null)   confirmError  = (String)confirmError;
            %>
            <form id="registration" action="Register" method="post" class="form-inline my-lg-0 d-flex flex-column align-items-start w-100">
                	<label for="username-box" class="h5 m-1">Username</label>
                    <input id="username-box" class="form-control w-100" type="text" name="username" value="<%= username %>">
                    <% if (usernameError != null) { %>
                        <p id="username-error" class="error-message"><%= usernameError %></p>
                    <% } %>

                    <label for="password-box" class="h5 m-1">Password</label>
                    <input id="password-box" class="form-control w-100" type="text" name="password" value="<%= password %>">
                    <% if (passwordError != null) { %>
                        <p id="password-error" class="error-message"><%= passwordError %></p>
                    <% } %>

                    <label for="pw-confirm-box" class="h5 m-1">Confirm Password</label>
                    <input id="pw-confirm-box" class="form-control w-100" type="text" name="pw-confirm">
                    <% if (confirmError != null) { %>
                        <p id="pw-confirm-error" class="error-message"><%= confirmError %></p>
                    <% } %>

                <button class="btn btn-outline-success my-sm-2 w-100" id="register-button" type="submit">Register</button>
            </form>
         </div>
        </div></div>
    </div>
</body>
</html>

