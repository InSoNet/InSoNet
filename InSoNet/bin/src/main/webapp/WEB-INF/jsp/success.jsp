<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Has creado una cuenta en Insonet</title>
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
        </div>
        <div class="col-lg-6">
        </div>
    </nav>
</div>
<div class="row">
	<p class="bg-success">
	   Has creado una cuenta Insonet con exito.
	   Se ha enviado un mensaje a tu cuenta de email para que confirmes tu registro.
	   Hasta que confirmes tu registro solo tendras acceso a tu perfil.
	</p>
</div>
<div class="row">
    <p>
      <a href="${pageContext.request.contextPath}/profile" class="btn btn-primary btn-lg" role="button">Configurar cuenta</a> 
    </p>
</div>
</div>

<script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    
</body>
</html>