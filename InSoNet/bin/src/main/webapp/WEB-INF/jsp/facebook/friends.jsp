<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="facebook4j.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		facebook4j.ResponseList<Friend> lista = (facebook4j.ResponseList<Friend>)request.getAttribute("friends");

		for (Friend ff : lista) {
	%>
		<p><%=ff.getName()%></p>
	<% } %>
</body>
</html>