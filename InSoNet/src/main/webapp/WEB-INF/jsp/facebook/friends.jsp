<%@include file="/WEB-INF/jsp/header.jsp" %>

<%ResponseList<Friend> lista = (ResponseList<Friend>)request.getAttribute("friends");%>
<%for (Friend ff : lista) {%>
	<p><%=ff.getName()%></p>
<%} %>
	
<%@include file="/WEB-INF/jsp/footer.jsp" %>
</body>
</html>