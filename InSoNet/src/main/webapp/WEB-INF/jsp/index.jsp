<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URL" %>
<%@ page import="ar.com.insonet.model.InsonetUser" %>
<%@ page import="ar.com.insonet.model.SocialNetwork" %>
<%@ page import="ar.com.insonet.service.FacebookServiceImpl" %>
<%@ page import="facebook4j.Facebook" %>
<%@ page import="facebook4j.Post" %>
<%@ page import="facebook4j.ResponseList" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html">
<html lang="es">
<head>
<meta charset="UTF-8">
<title>InSoNet</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet" type="text/css" />
<script src="<c:url value='/resources/js/holder.js' />"></script>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="<c:url value='/resources/js/jquery.js'/>"></script>
<script src="<c:url value='/resources/js/insonetCore.js'/>" type="text/javascript"></script>
<!-- conditional comments -->
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="<c:url value='/resources/js/html5shiv.js'/>"></script>
  <script src="<c:url value='/resources/js/respond.min.js'/>"></script>
<![endif]-->
<!--[if IE 9]>
    <script type='text/javascript' src="<c:url value='/resources/js/jquery.html5-placeholder-shim.js'/>"></script>
<![endif]-->
</head>
<body>
<div class="container">
    <div class="row">
<c:choose>
  <c:when test="${domainUser.isEnabled() == true}">
        <nav class="navbar navbar-default" role="navigation">
            <div class="col-lg-6">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Escribir mensaje...">
                    <div class="input-group-btn">
                        <button type="button" class="btn btn-default" name="privacidad" title="Privacidad de mensaje"><span class="glyphicon glyphicon-lock"></span></button>
                        <button class="btn btn-default" type="button" title="Adjuntar foto">Adjuntar Foto</button>
                        
                    </div>
                </div>
                <div class="checkbox-block input-sm">
                    <label>Publicar en:</label>
                    <label>
                        <input type="checkbox" value="Facebook" title="Publicar en Facebook" <c:if test="${domainUser.getSocialNetwork().isEmpty()}">disabled</c:if> />
                        Facebook
                    </label>
                    <label>
                        <input type="checkbox" value="Twitter" title="Publicar en Twitter" <c:if test="${domainUser.getSocialNetwork().isEmpty()}">disabled</c:if>>
                        Twitter
                    </label>
                    <label>
                        <input type="checkbox" value="Todos" title="Publicar en todos" <c:if test="${domainUser.getSocialNetwork().isEmpty()}">disabled</c:if>>
                        Todos
                    </label>
                </div>
                <div>
                    <button type="button" class="btn btn-default btn-xs <c:if test="${domainUser.getSocialNetwork().isEmpty()}">disabled</c:if>" title="Agregar Columna">
                      <span class="glyphicon glyphicon-plus"></span> Agregar Columna
                    </button>
                    <button type="button" onclick="location.href='${pageContext.request.contextPath}/addnet';" class="btn btn-default btn-xs" title="Agregar red social">
                      <span class="glyphicon glyphicon-plus"></span> Agregar Red Social
                    </button>
                    
                </div>
            </div>
            <div class="col-lg-6">
                <div>
                    <form  style="margin-top:0px;padding-left:0px;" role="search">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="Buscar Personas, Páginas, etc.">
                             <div class="input-group-btn">
                                <button type="submit" class="btn btn-default" title="Buscar" onclick='location.href="resultadoDeBusqueda.html"; return false;'>Buscar</button>
                            </div>
                        </div>                        
                    </form>
                </div>
                <div>
                    <div class="input-sm">                    
                    </div>
                    <div class=" btn-group-sm">
                        <button type="button" class="btn" name="sistema" title="Configurar sistema">
                           <span class="glyphicon glyphicon-cog"></span>
                        </button>
                        <button type="button" class="btn" name="usuario" title="Configurar cuenta de usuario" onclick="location.href='${pageContext.request.contextPath}/profile';">
                           <span class="glyphicon glyphicon-user"></span>
                        </button>
                        <button type="button" class="btn" name="mensajes" title="Mensajes">
                           <span class="glyphicon glyphicon-envelope"></span>
                        </button>
                        <button type="button" class="btn" name="notificaciones" title="Notificaciones">
                           <span class="glyphicon glyphicon-globe"></span>
                        </button>
                        <button type="button" class="btn-sm btn-default pull-right" onclick="location.href='<c:url value="/j_spring_security_logout" />';" name="logout" title="Cerrar sesión">
                            <span class="glyphicon glyphicon-off"></span>
                        </button>
                        
                    </div>
                </div>                
            </div>
        </nav>
    </c:when>
    <c:when test="${domainUser.isEnabled() == false}">
        <nav class="navbar navbar-default" role="navigation">
            <div class="col-lg-6">
                <img src="<c:url value='/resources/images/insonet_logo.png'/> " class="img-responsive" alt="InSoNet" title="InSoNet">
            </div>
            <div class="col-lg-6">
                <div class=" btn-group-sm">
                    <button type="button" class="btn" name="usuario" title="Configurar cuenta de usuario" onclick="location.href='${pageContext.request.contextPath}/profile';">
                       <span class="glyphicon glyphicon-user"></span>
                    </button>
                    
                    <button type="button" class="btn-sm btn-default pull-right" onclick="location.href='<c:url value="/j_spring_security_logout" />';" name="logout" title="Cerrar sesión">
                        <span class="glyphicon glyphicon-off"></span>
                    </button>
                </div>
            </div>
        </nav>
    </c:when>
</c:choose>  
    
    </div>
    <div class="row">
    <c:if test="${domainUser.isEnabled() == false}">
        <p class="alert alert-danger">
            Para disponer de toda la funcionalidad de Insonet.<br/>
            Por favor confirme su registro por medio del correo que se le envio!
        </p>
    </c:if>           
    </div>
<%FacebookServiceImpl fb = (FacebookServiceImpl) request.getSession().getAttribute("fb");%>
<%InsonetUser domainUser = (InsonetUser) request.getSession().getAttribute("domainUser");%>
<%List<SocialNetwork> nets = domainUser.getSocialNetwork();%>
<% if (nets.size() > 0) {%>
    <div class="row">    
    <%for(SocialNetwork n : nets) {%>
        <div class="col-lg-6">        
        <%ResponseList<Post> posts = fb.getPosts(n.getId()); %>
        <%if(posts != null) {%>
        <% for(Post p : posts) { %>
            <div class="media">
                <a class="pull-left" href="#">
                    <img class="media-object" src="holder.js/64x64" alt="Foto de perfil de <%=n.getUsernameSocial()%>" class="img-thumbnail" style="width:64px; height:64px;">
                </a>
            <% if (p.getMessage() != null) {%>
                <%=n.getUsernameSocial() + " " + p.getCreatedTime() + " " + p.getMessage() %>
            <% }%>
            <% URL url = p.getPicture();%>
            <% if (url != null) { %>
                <img class="media-object" src="<%=url.toString()%>" alt="Foto que publico <%=n.getUsernameSocial() %>" class="img-thumbnail" style="width:140px; height:180px;">
            <% }%>
            </div>
        <% }%>
        <%} %>
        </div>
    <%}%>    
    </div>
<% }%>
</div>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
</body>
</html>