<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign in with Facebook</title>
</head>
<body>
<tag:notloggedin>
        <a href="signin">Sign in with Facebook</a>
    </tag:notloggedin>
    <tag:loggedin>
        <h1>Welcome ${facebook.name} (${facebook.id})</h1>
        <form action="./facebook/posts" method="post">
            <textarea cols="80" rows="2" name="message"></textarea>
            <input type="submit" name="post" value="statuses" />
        </form>
        <a href="./facebook/logout">logout</a>
    </tag:loggedin>
</body>
</html>