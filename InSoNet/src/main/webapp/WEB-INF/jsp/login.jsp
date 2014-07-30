<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio de sesión</title>
    <!-- Bootstrap -->
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet" type="text/css" />
    <script src="<c:url value='/resources/js/holder.js' />"></script>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="<c:url value='/resources/js/jquery.js'/>"></script>
    <!-- conditional comments -->
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="<c:url value='/resources/js/html5shiv.js'/>"></script>
      <script src="<c:url value='/resources/js/respond.min.js'/>"></script>
    <![endif]-->
    <!--[if IE 9]>
        <script type='text/javascript' src='<c:url value="/resources/js/jquery.html5-placeholder-shim.js"/>'></script>
    <![endif]-->
    
</head>
<body>
<div class="container">
    <div class="row">
        <nav class="navbar navbar-default" role="navigation">
            <div class="col-lg-6">
                <img src="<c:url value='/resources/images/insonet_logo.png'/> " class="img-responsive" alt="InSoNet" title="InSoNet">
            </div>
            <div class="col-lg-6">
                
                
            </div>
        </nav>
    </div>
    <div class="row">
        <div class="col-lg-4">
        <c:out value="${message}"/>
        
        </div>
        <div class="col-lg-4">
            <c:if test="${param.error != null}">        
		        <p class="alert alert-danger">
		            <c:out value="${error}"/>
		        </p>
		    </c:if>
		    <c:if test="${param.logout != null}">       
		        <p class="alert alert-success">
		            <c:out value="${msg}"/>
		        </p>
		    </c:if>
            <form  role="form" action="<c:url value='j_spring_security_check'/>" method='POST'>
                <div class="form-group">
                    <label for="j_username" lang="es">Usuario</label>
                    <input type="text" class="form-control" id="j_username" name="j_username" tabindex="1" placeholder="Ingrese nombre de usuario" aria-describedby="username"/>
                    <div id="username" class="tooltip" role="tooltip">Ingresar el nombre de usuario es obligatorio</div>
                </div>
                <div class="form-group">
                    <label for="j_password" lang="es">Contraseña</label>
                    <input type="password" class="form-control" id="j_password" name="j_password" tabindex="2" placeholder="Ingrese contraseña" aria-describedby="password"/>
                    <div id="password" class="tooltip" role="tooltip">Ingresar la contraseña es obligatorio</div>
                </div>
                <div class="form-group">
                    
                        <div class="checkbox">
                            <label for="remember">
                                <input type="checkbox" name="remember-me" id="remember" tabindex="3" aria-describedby="rememberTooltip" > Recordarme
                            </label>
                            <div id="rememberTooltip" class="tooltip" role="tooltip">Seleccione con barra espaciadora y su sesión no se cerrara aunque cierre el navegador</div>
                        </div>
                    
                </div>
                <button type="submit" class="btn btn-default" title="Iniciar sesión" lang="es" tabindex="4">Enviar</button>
                &nbsp;&nbsp;<a href="./signup" title="Crear cuenta en InSoNet" tabindex="5">Registrarse</a>
                
            </form>
                   
        </div>
        <div class="col-lg-4">
        </div>
    </div>
</div>    
    
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
<script>
$(document).ready(function(){
    $("#j_username").focus();
});
</script>
</body>
</html>