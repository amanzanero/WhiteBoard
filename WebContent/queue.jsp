<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="whiteboard.DatabaseConnect" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="assets/css/all.min.css">
    <title>WhiteBoard</title>
</head>

<%
    Boolean loggedIn = false;
    HttpSession sesh = request.getSession();
    Object u = sesh.getAttribute("username");
    String user = (u != null) ? u.toString() : "";
    if (!user.equals("")) loggedIn = true;
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
					rightHref = "register.jsp";
				}
				String course = request.getParameter("course");
				course = URLDecoder.decode(course, StandardCharsets.UTF_8.toString());
				Object n = request.getParameter("visitorname");
				String username = null;
				if (n != null) username = n.toString();
				/*
				 * Need to use database manager here to pull users in queue
				 */
				String data = DatabaseConnect.getQueueString(course);
				Vector<String> data_vector;
				if(data == null){
					data_vector = new Vector<String>(); //creates empty vector so empty message shows
				}
				else{
					data_vector = new Vector<String>(Arrays.asList(data.split(",")));//splits into filled vector
				}
			%>
			<ul class="navbar-nav text-right">
				<li class="nav-item active">
					<a class="nav-link" href="<%=leftHref %>"><%=leftLink %>
					</a>
				</li>
				<li class="nav-item active">
					<a class="nav-link" href="<%=rightHref %>"><%=rightLink %>
					</a>
				</li>
			</ul>
		</div>
	</nav>
	<div class="page-content container-fluid d-flex flex-column align-items-center">
		<div class="card w-100 d-flex flex-row justify-content-between align-items-center m-1" style="padding: 1em;">
			<h3 class="text-center">
				Queue for: <%= course %>
			</h3>
			<button class="btn btn-primary my-sm-2" onclick="location.reload()">Refresh <i class="fas fa-sync-alt"></i>
			</button>
		</div>

		<div class="card w-100 d-flex align-items-start m-1" style="padding: 1em;">
			<%
				if (username != null && data_vector.contains(username)) {
					int location = data_vector.indexOf(username) + 1;
					String message = location == 1 ? "it is your turn to see a cp!" : "your place in line: " + Integer.toString(location);
			%>
					<h3>
						<%=username %>, <%=message %>
					</h3>
			<%
				}
				else {
					Object addErr = request.getAttribute("addError");
					Boolean addError = addErr != null;
			%>
					<h3>
						Join this queue!
					</h3>
					<div style="width: 100%">
						<form action="JoinQueue" method="GET" class="form-inline my-lg-0 d-flex justify-content-start w-100">
							<input name="visitorAdd" class="form-control mr-sm-1 col-8" type="search"
								   placeholder="usc username (i.e. ttrojan for ttrojan@usc.edu)">
							<input name="course" type="hidden" value="<%=course %>">
							<button class="btn btn-outline-primary my-sm-2" type="submit">Join &nbsp; <i
									class="fas fa-plus-square"></i></button>
							<p><%=addError ? addErr.toString() : "" %>
							</p>
						</form>
					</div>
			<%
				}
			%>


		</div>
		<div class="card w-100 d-flex flex-column align-tems-center m-1" style="padding: 1em;">
			<%
				String qText = null;
				if (data_vector.size() == 0) qText = "Queue is empty!";
				else qText = "Current students in line:";
			%>
			<h3>
				<%= qText %>
			</h3>
			<ul class="list-group">
				<%
					int counter = 1;
					for (String q : data_vector) {
				%>
						<li class="list-group-item d-flex justify-content-start align-items-center <%=(username != null && username.equals(q)) ? "active" :"" %>">
							<%=counter + ". " + q %>
						</li>
				<%
						counter++;
					}
				%>
			</ul>
		</div>
	</div>
	<script type="text/javascript" src="assets/js/jquery-3.4.1.min.js"></script>
	<script type="text/javascript" src="assets/js/popper.min.js"></script>
	<script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
</body>
</html>
