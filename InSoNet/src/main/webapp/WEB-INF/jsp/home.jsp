<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Inicio</title>
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
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Escribir mensaje...">
                    <div class="input-group-btn">
                        <button type="button" class="btn btn-default" name="privacidad" title="Privacidad de mensaje"><span class="glyphicon glyphicon-lock"></span></button>
                        <button class="btn btn-default" type="button" title="Adjuntar foto">Adjuntar Foto</button>
                        
                    </div><!-- /btn-group -->
                </div><!-- /input-group -->
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
                    <!--<button type="button" class="btn btn-default btn-xs" value="cerrar">
                      Facebook <span class="glyphicon glyphicon-remove"></span>
                    </button>
                    <button type="button" class="btn btn-default btn-xs" value="cerrar">
                      Twitter <span class="glyphicon glyphicon-remove"></span>
                    </button>-->
                </div>
            </div><!-- /.col-lg-6 -->
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
                        <button type="button" class="btn" name="usuario" title="Configurar cuenta de usuario">
                           <span class="glyphicon glyphicon-user"></span>
                        </button>
                        <button type="button" class="btn" name="mensajes" title="Mensajes">
                           <span class="glyphicon glyphicon-envelope"></span>
                        </button>
                        <button type="button" class="btn" name="notificaciones" title="Notificaciones">
                           <span class="glyphicon glyphicon-globe"></span>
                        </button>
                        <button type="button" class="btn-sm btn-default pull-right" name="logout" title="Cerrar sesión">
                            <span class="glyphicon glyphicon-off"></span>
                        </button>
                    </div>
                </div>
                
            </div>
        </nav>
    </div>
</div>
<div class="row">
    <p>
	  <button type="button" class="btn btn-primary Extra btn-lg">Large button</button>
	  <button type="button" class="btn btn-default Extra btn-lg">Large button</button>
	</p>
</div>

<script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    
</body>
</html>