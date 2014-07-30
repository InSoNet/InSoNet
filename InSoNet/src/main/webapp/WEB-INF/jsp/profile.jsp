<%@ page import="ar.com.insonet.service.FacebookServiceImpl" %>
<%@ page import="facebook4j.ResponseList" %>
<%@ page import="facebook4j.Notification" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="ar.com.insonet.model.InsonetUser" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
<%FacebookServiceImpl fb = (FacebookServiceImpl) request.getSession().getAttribute("fb");%>
<%ResponseList<Notification> noti = fb.getNotifications(); %>
<c:choose>
  <c:when test="${insonetUser.isEnabled() == true}">
        <nav class="navbar navbar-default" role="navigation">
      <div class="col-lg-6">
                <form action="${pageContext.request.contextPath}/post/add" method="post" role="form" id="formMessage" enctype="multipart/form-data">
                <div class="input-group">                   
                    <input name="messageTxt" id="messageTxt" type="text" class="form-control" placeholder="Escribir mensaje..." aria-describedby="mtxt" required/>
                    <div id="mtxt" class="tooltip" role="tooltip">Escriba el mensaje y seleccione las redes para postearlo</div>
                    <div class="input-group-btn">
                        <button type="button" class="btn btn-default" name="privacidad" title="Privacidad de mensaje" data-toggle="modal" data-target="#modalPrivacidad"><span class="glyphicon glyphicon-lock"></span></button>
                        <button id="adjuntar" class="btn btn-default" type="button" title="Adjuntar foto">Adjuntar Foto</button>
                        <button type="submit" id="publishingButton" class="btn btn-default" title="Publicar mensaje" lang="es">Enviar</button>
                        <input type="file" id="filePhoto" name="filePhoto" class="hidden">
                        <div class="modal fade" id="modalPrivacidad" tabindex="-1" role="dialog" aria-labelledby="Privacidad" aria-hidden="true">
                          <div class="modal-dialog">
                            <div class="modal-content">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                <h4 class="modal-title">Elija nivel de privacidad</h4>
                              </div>
                              <div class="modal-body">
                                  <div class="radio">
                                      <label>
                                        <input type="radio" name="privacy" id="privacity1" value="SELF" title="Solo Yo">
                                        Solo Yo
                                      </label>
                                  </div>
                                  <div class="radio">
                                      <label>
                                        <input type="radio" name="privacy" id="privacity2" value="FRIENDS_OF_FRIENDS" title="amigos de mis amigos">
                                        Amigos de mis amigos
                                      </label>
                                  </div>
                                  <div class="radio">
                                      <label>
                                        <input type="radio" name="privacy" id="privacity3" value="ALL_FRIENDS" title="Amigos">
                                        Amigos
                                      </label>
                                  </div>
                                  <div class="radio">
                                      <label>
                                        <input type="radio" name="privacy" id="privacity4" value="EVERYONE" title="Público" checked>
                                        Público
                                      </label>
                                  </div>                                                    
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                              </div>
                            </div><!-- /.modal-content -->
                          </div><!-- /.modal-dialog -->
                        </div><!-- /.modal -->
                    </div>
                </div>
                <div id="noticeMessage" class=""></div>
                <label for="messageTxt" class="error hidden" style="display:none important;">Escriba un mensaje</label>
                <div class="checkbox-block input-sm">
                    <label>Publicar en:</label>
                    <label>
                        <input name="publishingIn" type="checkbox" value="Facebook" title="Publicar en Facebook" <%if(fb.getVisiblesSocialNetworks().isEmpty()){ %>disabled<%} %>/>
                        Facebook
                    </label>
                    <label>
                        <input name="publishingIn" type="checkbox" value="Twitter" title="Publicar en Twitter" <c:if test="${domainUser.getSocialNetwork().isEmpty()}">disabled</c:if>>
                        Twitter
                    </label>
                    <label>
                        <input name="publishingIn" type="checkbox" value="Todos" title="Publicar en todos" <c:if test="${domainUser.getSocialNetwork().isEmpty()}">disabled</c:if>>
                        Todos
                    </label>
                </div>
                </form>
                <div>
                    <button type="button" onclick="location.href='${pageContext.request.contextPath}/addcolumn';" class="btn btn-default btn-xs <c:if test="${domainUser.getSocialNetwork().isEmpty()}">disabled</c:if>" title="Agregar Columna">
                      <span class="glyphicon glyphicon-plus"></span> Agregar Columna
                    </button>
                    <button type="button" onclick="location.href='${pageContext.request.contextPath}/addnet';" class="btn btn-default btn-xs" title="Agregar red social">
                      <span class="glyphicon glyphicon-plus"></span> Agregar Red Social
                    </button>
                    
                </div>
            </div>
            <div class="col-lg-6">
                <div>
                    <form id="searchForm" action="search" method="post" style="margin-top:0px;padding-left:0px;" role="search">
                        <div class="input-group">
                            <input type="text" id="searchTxt" name="searchTxt" class="form-control" placeholder="Buscar Personas, Páginas, etc." required <c:if test="${domainUser.getSocialNetwork().isEmpty()}">disabled</c:if>/>
                            <div class="input-group-btn">
                                <button type="submit" id="searchButton" class="btn btn-default <c:if test='${domainUser.getSocialNetwork().isEmpty()}'>disabled</c:if>" title="Buscar">Buscar</button>
                            </div>
                        </div>
                        <div id="alertSearch" class=""></div>
                        <label for="searchTxt" class="error hidden" style="display:none important;">Escriba algo</label>
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
                            <%if(noti != null){ %><span class="badge pull-right"><%=noti.size() %></span><%} %>
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
    <c:when test="${insonetUser.isEnabled() == false}">
    
        <nav class="navbar navbar-default" role="navigation">
            <div class="col-lg-6">
                <img src="<c:url value='/resources/images/insonet_logo.png'/> " class="img-responsive" alt="InSoNet" title="InSoNet">
            </div>
            <div class="col-lg-6">
                <button type="button" class="btn-sm btn-default pull-right" onclick="location.href='<c:url value="/j_spring_security_logout" />';" name="logout" title="Cerrar sesión">
                    <span class="glyphicon glyphicon-off"></span>
                </button>
            </div>
        </nav>
    
    </c:when>
</c:choose>    
</div>
<div class="row">
    <ul class="list-unstyled list-block">
        <li title="Facebook" lang="en">
        <div class="col-lg-6">
            <p class="text-center">Perfil</p>
            <a href="${pageContext.request.contextPath}/index" class="btn btn-default btn-lg" role="button" id="col0" title="Inicio">Inicio</a>
            <a href="${pageContext.request.contextPath}/profile" id="linkPerfil" class="btn btn-primary btn-lg" title="Perfil" role="button">Perfil</a>
            <script>
                $('#linkPerfil').focus();
            </script>
            <p class="lead"><strong>Información básica</strong></p>
            <form:form action="${pageContext.request.contextPath}/profile/update" commandName="insonetUser" method="post" role="form">
                <form:errors path="*" cssClass="alert alert-danger" element="div" />
                <form:hidden path="enabled"/>
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
                    <form:input path="username" maxlength="20" class="form-control" placeholder="Ingrese su nombre de usuario" required="required" readOnly="true"/>
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
                
                <button type="submit" class="btn btn-default" tittle="Guardar cambios">Guardar</button>
                          
            </form:form>
            
            <p></p>
            
            <ul class="media-list">
                <li>
                    <a href="#" onclick="javascript:sendEmailConfirm();">Volver a enviar correo de confirmación de cuenta.</a>
                    <div class="" id="result"></div>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/addnet">Añadir red social</a>
                </li>
            </ul>                                    
        </div>
        </li>        
    </ul>
</div>
</div>
<script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    
</body>
</html>