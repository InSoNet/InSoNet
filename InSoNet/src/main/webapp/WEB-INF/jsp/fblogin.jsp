<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value='/resources/css/bootstrap.min.css' />"
	rel="stylesheet" type="text/css" />
<script src="<c:url value='/resources/js/jquery.js'/>"></script>
<script>
	$(document).ready(function() {
		$('#post').load("/InSoNet/facebook/posts");
		$('#friendsList').load("/InSoNet/facebook/friends");
	});
</script>
<title>Sign in with Facebook</title>
</head>
<body>
	<tag:notloggedin>
		<a href="signin">Sign in with Facebook</a>
	</tag:notloggedin>
	<tag:loggedin>
		<div class=".col-md-12">
			<h1>Welcome ${facebook.name} (${facebook.id})</h1>
			<div id="status">
				<form action="./facebook/posts" method="post">
					<textarea cols="80" rows="2" name="message"
						title="Escribe un mensaje"></textarea>
					<input type="submit" name="post" value="statuses" />
				</form>
			</div>
			<div class="clear"></div>
			<div id="post" class=".col-md-6"></div>
			<div id="friends" class=".col-md-6">
				<h1>Amigos</h1>
				<div id="friendsList"></div>
			</div>
		</div>
		<a href="./facebook/logout">logout</a>
	</tag:loggedin>
</body>
</html>