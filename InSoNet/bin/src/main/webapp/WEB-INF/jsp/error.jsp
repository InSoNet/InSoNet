<%@ page contentType="text/html;charset=UTF-8" language="java"
	isErrorPage="true"%>
<html>
<head>
<title>Error</title>
</head>
<body>
	<p>
		Failed to connect to the Facebook Graph API <br>
		${exception.message}
	</p>
	<p>
		<a href="./logout">logout</a>
	</p>
</body>
</html>