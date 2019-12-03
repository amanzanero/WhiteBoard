<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="whiteboard.DatabaseConnect"%>
<%@ page import ="java.util.Vector"%>
<%@ page import ="java.util.Arrays"%>
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
else response.sendRedirect("homepage.jsp");  // redirect to homepage if not logged in
/* Vector<String> adminQueues = DatabaseConnect.getAdminQueues(user);
 */
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
String leftLink = "Homepage";
String rightLink = "Log Out";
String leftHref = "homepage.jsp";
String rightHref = "Logout";
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
Vector<String> queues = DatabaseConnect.getUserQueues(user);
%>
	<div class="page-content container-fluid d-flex flex-column align-items-center">
		<div class="card w-100 d-flex flex-column align-tems-center m-1" style="padding: 1em;">
			<ul class="list-group">
			  <li class="list-group-item d-flex justify-content-center"><h3><%=user %>'s Dashboard</h3></li>
			  <li class="list-group-item">
			  	<button class="btn btn-outline-primary my-sm-2 w-100" type="submit" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo">Create a Queue&nbsp;<i class="fas fa-plus-square"></i></button>
			  </li>
			  <li class="list-group-item">
			  	<h4>Queues:</h4>
			  	<div id="accordion">
<%
int counter = 0;
int counter2 = 0;
for (String queue : queues) {
%>
					<div class="card">
				    <div class="card-header" id="heading<%=counter %>">
				      <h5 class="mb-0">
				        <button class="btn btn-link" data-toggle="collapse" data-target="#collapse<%=counter %>" aria-expanded="true" aria-controls="collapse<%=counter %>">
				          <%=queue %>
				        </button>
				      </h5>
				    </div>
				
				    <div id="collapse<%=counter %>" class="collapse" aria-labelledby="heading<%=counter %>" data-parent="#accordion">
				      <div class="card-body">
				      	<ul class="list-group">
				      	<%String data = DatabaseConnect.getQueueString(queue);%>
				      	<%Vector<String> data_vector = new Vector<String>(Arrays.asList(data.split(",")));%>
				      	<%for (String visitor : data_vector) { %>
				      		<li class="list-group-item"><%=visitor %></li>
				      	<%counter2++;} %>
				      	</ul>
				      </div>
				    </div>
				  </div>
<%
	counter++;
}
%>
				</div>
			  </li>
			</ul>
			<%
				Object alreadyExists = request.getAttribute("alreadyExists");
				String existsError = null;
				if(alreadyExists!=null){existsError = alreadyExists.toString();}
			%>
			<%
				if(existsError!=null){
			%>
					<span style="color:red; font:Times"><%=existsError %></span>
		    
		    <%} %>
		</div>
	
	</div>
	
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">New Queue</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <form id="newQueue" action="createQueue" method="post">
	          <div class="form-group">
	            <label for="recipient-name" class="col-form-label">Queue Name</label>
	            <input type="text" class="form-control" id="recipient-name" name="newName">
	          </div>
	          <div class="form-group">
	            <div class="modal-footer">
	        		<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	    
	      	 		<button type="submit" class="btn btn-primary" >Create</button>
	      		</div>
	          </div>
	        </form>
	      </div>
	    </div>
	  </div>
	</div>

  		<script type="text/javascript" src="assets/js/jquery-3.3.1.slim.min.js"></script>
		<script type="text/javascript" src="assets/js/popper.min.js"></script>
		<script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="assets/js/queue-checkoff.js"></script>
	</body>
</html>
