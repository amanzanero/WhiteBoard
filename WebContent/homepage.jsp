<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/styles.css">
    <title>WhiteBoard</title>
  </head>

<% 
Boolean loggedIn = false;
%>
  
  <body>
	<div class="bg-image"></div>
	<nav class="navbar navbar-expand-lg bg-dark navbar-dark justify-content-end">
	    <a class="navbar-brand ml-1 mr-auto" href="#">WhiteBoard</a>
	    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent">
	        <span class="navbar-toggler-icon"></span>
	    </button>
	    <div class="collapse navbar-collapse flex-grow-0" id="navbarSupportedContent">

<%
String leftLink = "";
String rightLink = "";
String leftHref = "";
String rightHref = "";
if (loggedIn) {
	leftLink = "Dashboard";
	leftHref = "dashboard.jsp";
	rightLink = "Log Out";
	rightHref = "logout";
}
else {
	leftLink = "Login";
	leftHref = "login.jsp";
	rightLink = "Register";
	rightHref = "register.jsp";
}
%>
			<ul class="navbar-nav text-right">
	            <li class="nav-item active">
	                <a class="nav-link" href="<%=leftHref %>"><%=leftLink %></a>
	            </li>
	            <li class="nav-item active">
	                <a class="nav-link"  href="<%=rightHref %>"><%=rightLink %></a>
	            </li>
	        </ul>  
        
	    </div>
	</nav>
	<div class="page-content container-fluid d-flex flex-column align-items-center">
		<div class="card w-50 d-flex flex-column align-tems-center m-1" style="padding: 1em;">
			<h3>
				Already in a Queue?
			</h3>
			<form action="homepage.jsp" class="form-inline my-lg-0 d-flex justify-content-center w-100">
		      <input name="username" class="form-control mr-sm-1 col-sm-8" type="search" placeholder="tommytrojan">
		      <button class="btn btn-outline-dark my-sm-2" type="submit">Find Queues!</button>
		    </form>
		</div>
		<div class="card w-50 d-flex flex-column align-tems-center m-1" style="padding: 1em;">
			<h3>
				Go directly to the Queue you want!
			</h3>
			<form action="homepage.jsp" class="form-inline my-1 my-lg-0 d-flex justify-content-center w-100">
		      <input name="username" class="form-control mr-sm-1 col-sm-8" type="search" placeholder="CSCI 201">
		      <button class="btn btn-outline-dark my-sm-2" type="submit">Go to Queue</button>
		    </form>
		</div>
	</div>
  </body>
  <footer>
  	<!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="assets/js/jquery-3.3.1.slim.min.js"></script>
    <script src="assets/js/popper.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
  </footer>
</html>