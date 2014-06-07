<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/facebook"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="es">
<head>
<meta charset="utf-8"/>
<title></title>
</head>
<body>
    
	<c:url value="/j_spring_security_logout" var="logoutUrl" />
    <a href="${logoutUrl}">Log Out</a>
	<tag:notloggedin>
		<a href="./signin">Sign in with Facebook</a>
	</tag:notloggedin>
	<tag:loggedin>
		<a href="logout">logout</a>
		<h1>Welcome ${facebook.name} (${facebook.id})</h1>
		<form action="posts" method="post">
			<textarea cols="80" rows="2" name="message"></textarea>
			<input type="submit" name="post" value="statuses" />
		</form>
		
	</tag:loggedin>
</body>
</html>