<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro de usuario InSoNet</title>
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
        <nav class="navbar navbar-default" role="navigation">
            <div class="col-lg-6">
                <img src="<c:url value='/resources/images/insonet_logo.png'/>" class="img-responsive" alt="InSoNet" title="InSoNet">
                
            </div><!-- /.col-lg-6 -->
            <div class="col-lg-6">
                
                
            </div>
        </nav>
    </div>
    <div class="row">
        <div class="col-lg-4">
        </div>
        <div class="col-lg-4">
            <form:form action="./signup" commandName="insonetUser" method="post" role="form">
                <form:errors path="*" cssClass="alert alert-danger" element="div" />
                <div class="form-group">
                    <label for="name">Nombre</label>
                    <form:input path="name" class="form-control" placeholder="Ingrese su nombre" required="required" />
                </div>
                <div class="form-group">
                    <label for="surname">Apellido</label>
                    <form:input path="surname" class="form-control" placeholder="Ingrese su apellido" required="required"/>
                </div>
                <div class="form-group">
                    <label for="username">Nombre de usuario</label>
                    <form:input path="username" maxlength="20" class="form-control" placeholder="Ingrese su nombre de usuario" required="required"/>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <form:input type="email" class="form-control" path="email" placeholder="Ingrese Email" required="required"/>
                </div>
                <div class="form-group">
                    <label for="password">Contraseña</label>
                    <form:password path="password" id="password" class="form-control" placeholder="Ingrese contraseña" required="required"/>
                </div>
                <div class="form-group">
                    <label for="password2">Repita contraseña</label>
                    <input type="password" name="password2" id="password2" oninput="validatePass(document.getElementById('password'), this);" class="form-control" placeholder="Ingrese contraseña nuevamente" required="required"/>
                </div>
                <div class="form-group">
                    
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" name="agree" required="required" /> acepto los términos y condiciones del sitio.
                            </label>
                        </div>
                    
                </div>
                <button type="submit" class="btn btn-default">Registrarme</button>
                                
            </form:form>
                   
        </div>
        <div class="col-lg-4">
        </div>
    </div>
</div>    
    
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    
</body>
</html>