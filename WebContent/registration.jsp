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
    <meta charset="utf-8">
    <title>WhiteBoard - Registration</title>
    <link rel="icon" type="image/png" sizes="96x96"
          href="https://raw.githubusercontent.com/audreybongalon/documents/gh-pages/audreyfavicons/favicon-96x96.png">
    <link href="https://fonts.googleapis.com/css?family=Raleway|Open+Sans" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="global.css">
    <link rel="stylesheet" type="text/css" href="login-register.css">
    <meta name="theme-color" content="#4c2b15">
</head>
<body>
    <div id="everything">
        <div id="header-wrapper"><div id="header">
            <a href="homepage.jsp">
                <img id="whiteboard-logo" src="whiteboard.png" alt="whiteboard logo">
            </a>
            <h1>WhiteBoard (header text here)</h1>
        </div></div>

        <div id="main-wrapper"><div id="main">
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
            <form id="registration" action="Register" method="post">
                <div>
                	<label for="username-box">Username</label>
                    <input id="username-box" type="text" name="username" value="<%= username %>">
                    <% if (usernameError != null) { %>
                        <p id="username-error" class="error-message"><%= usernameError %></p>
                    <% } %>
                </div>

                <div>
                    <label for="password-box">Password</label>
                    <input id="password-box" type="text" name="password" value="<%= password %>">
                    <% if (passwordError != null) { %>
                        <p id="password-error" class="error-message"><%= passwordError %></p>
                    <% } %>
                </div>

                <div>
                    <label for="pw-confirm-box">Confirm Password</label>
                    <input id="pw-confirm-box" type="text" name="pw-confirm">
                    <% if (confirmError != null) { %>
                        <p id="pw-confirm-error" class="error-message"><%= confirmError %></p>
                    <% } %>
                </div>

                <input id="register-button" type="submit" value="Register">
            </form>
        </div></div>
    </div>
</body>
</html>

