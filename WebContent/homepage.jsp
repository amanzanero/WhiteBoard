<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="servlets.DatabaseConnect"%>
<%@ page import ="java.util.Vector"%>

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
HttpSession sesh = request.getSession();
Object u = sesh.getAttribute("username");
String user = (u != null) ? u.toString() : "";

if (!user.equals("")) loggedIn = true;

// FAKE DATA FOR QUEUE POPUP
Vector<String> queues = DatabaseConnect.getAllQueues();

%>

  <body>
	<div class="bg-image"></div>
	<nav class="navbar navbar-expand-lg bg-dark navbar-dark justify-content-end">
	    <a class="navbar-brand ml-1 mr-auto" href="homepage.jsp">WhiteBoard</a>
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
	rightHref = "Logout";
}
else {
	leftLink = "Login";
	leftHref = "login.jsp";
	rightLink = "Register";
	rightHref = "registration.jsp";
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
<%
Object vErr = request.getAttribute("visitorError");
%>
	<div class="page-content container-fluid d-flex flex-column align-items-center">
		<div class="card w-100 d-flex flex-column align-tems-center m-1" style="padding: 1em;">
			<h3>
				Already in a Queue?
			</h3>
			<form action="UserQueues" class="form-inline my-lg-0 d-flex justify-content-center w-100">
		      <input name="visitorname" class="form-control mr-sm-1 col-sm-8" type="search" placeholder="tommytrojan">
		      <button class="btn btn-outline-dark my-sm-2" type="submit">Find Queues!</button>
		      <p id="vistorFindErr"><%=(vErr==null) ? "" : "User does not exist in any queues" %></p>
		    </form>
		</div>
<%
Object vqs = request.getAttribute("visitorQueues");
Object v = request.getAttribute("visitorName");

if (vqs != null) {
	Vector<String> visitorQueues = (Vector<String>) vqs;
	String visitorName = (String)v;
%>
		<div class="card w-100 d-flex flex-column align-tems-center m-1" style="padding: 1em;">
			<h4>
				<%=visitorName %>'s queues
			</h4>
			<ul class="list-group">
<%
	for (String q : visitorQueues) {
		String link = "queue.jsp?course="+q+"&visitorname="+visitorName;
%>
		        <li class="list-group-item d-flex justify-content-between align-items-center">
		            <%=q %>
		            <a href=<%=link %>>
		                <button type="button" class="btn btn-dark">View</button>
		            </a>
		        </li>
<%
	}
%>
            </ul>
       	</div>
<%
}
Object qErr = request.getAttribute("queueError");
%>		
		<div class="card w-100 d-flex flex-column align-tems-center m-1" style="padding: 1em;">
			<h3>
				Go directly to the Queue you want!
			</h3>
			<form action="ToQueue" class="form-inline my-1 my-lg-0 d-flex justify-content-center w-100">
		      <input name="queueName" class="form-control mr-sm-1 col-sm-8" type="search" placeholder="CSCI 201">
		      <button class="btn btn-outline-dark my-sm-2" type="submit">Go to Queue</button>
		    </form>
		   	<p><%=(qErr == null) ? "": "Queue does not exist!"%></p>
		</div>
        <div class="card w-100 d-flex flex-column align-tems-center m-1" style="padding: 1em;">
<%
	String qText = null;
	if (queues.size() == 0 ) qText = "No queues available";
	else qText = "Available queues!";
%>
			<h3>
				<%=qText %>
			</h3>
            <ul class="list-group">
<%
for (String q : queues) {
	String link = "queue.jsp?course="+q;
%>
		        <li class="list-group-item d-flex justify-content-between align-items-center">
		            <%=q %>
		            <a href=<%=link %>>
		                <button type="button" class="btn btn-dark">View</button>
		            </a>
		        </li>
<%
}
%>
            </ul>
		</div>
	</div>
  </body>
  <footer>
    <script src="assets/js/jquery-3.3.1.slim.min.js"></script>
    <script src="assets/js/popper.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
  </footer>
</html>