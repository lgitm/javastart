<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Eportal</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" type="text/css" rel="stylesheet">
  </head>

  <body>
  
	<nav class = "navbar navbar-inverse navbar-fixed-top">
		<div class="container">
        <a href="#" class="navbar-brand">Eportal</a>
        
        <button class="navbar-toggle" data-toggle="collapse" data-target=".navHeaderCollapse">
          <span class="glyphicon glyphicon-list"></span>
        </button>
        
        <div class="collapse navbar-collapse navHeaderCollapse">
          <ul class="nav navbar-nav navbar-right">
            <li class="active"><a href="#">Main</a></li>
            <li ><a href="#">Add</a></li>
            <li><a href="#">Log in</a></li>
            <c:choose>
  				<c:when test="${not empty sessionScope.user}">
   					<li><a href="logout">Logout</a></li>
				</c:when>
				<c:otherwise>
 					<li><a href="login">Login</a></li>
 				</c:otherwise>
			</c:choose>
          </ul>
        </div>
		</div>
    </nav>
    
    <div class="container">
    <div class="col-md-8 col-md-offset-2">
      <form class="form-signin" method="post" action="add">
        <h2 class="form-signin-heading">Add new post</h2>
        <input name="inputName" type="text" class="form-control" placeholder="Title" required autofocus>
        <input name="inputUrl" type="url" class="form-control" placeholder="URL" required autofocus>
        <textarea name="inputDecription" rows="5" name="inputUsername" class="form-control" placeholder="Description" required autofocus></textarea>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Add</button>
      </form>
    </div>
    </div>
    
	<footer class="footer">
      <div class="container">
        <p class="navbar-text">Eportal - developed by lm</p>
      </div>
    </footer>
  
    <script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
    <script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
    <script src="resources/js/bootstrap.js"></script>
  </body>
</html>