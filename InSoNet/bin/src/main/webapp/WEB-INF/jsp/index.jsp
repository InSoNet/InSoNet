<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html">
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Agregue una red social</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet" type="text/css">
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
                        <input type="checkbox" value="Facebook" title="Publicar en Facebook">
                        Facebook
                    </label>
                    <label>
                        <input type="checkbox" value="Twitter" title="Publicar en Twitter">
                        Twitter
                    </label>
                    <label>
                        <input type="checkbox" value="Todos" title="Publicar en todos">
                        Todos
                    </label>
                </div>
                <div>
                    <button type="button" class="btn btn-default btn-xs" title="Agregar Columna">
                      <span class="glyphicon glyphicon-plus"></span> Agregar Columna
                    </button>
                    <button type="button" class="btn btn-default btn-xs" title="Agregar red social">
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
            Por favor confirme su registro por medio del correo que se le envio!<br/>
            Para disponer de toda la funcionalidad de Insonet.
        </p>
    </c:if>
        <p>
	        <a href="${pageContext.request.contextPath}/facebook/signin" class="btn btn-default btn-lg <c:if test="${domainUser.isEnabled() == false}">disabled</c:if>" role="button" id="addFacebook" title="Agregar una cuenta de Facebook">Agregar una cuenta de Facebook</a>
	    </p>
	    <p>
	        <a href="${pageContext.request.contextPath}/twitter" id="addTwitter" class="btn btn-primary btn-lg <c:if test="${domainUser.isEnabled() == false}">disabled</c:if>" title="Agregar una cuenta de Twitter" role="button">Agregar una cuenta de Twitter</a>
	    </p>
	    
            
            
    </div>
</div>
	
</body>
</html>