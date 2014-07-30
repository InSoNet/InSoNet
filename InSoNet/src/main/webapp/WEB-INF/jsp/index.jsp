<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URL" %>
<%@ page import="ar.com.insonet.model.InsonetUser" %>
<%@ page import="ar.com.insonet.model.SocialNetwork" %>
<%@ page import="ar.com.insonet.service.FacebookServiceImpl" %>
<%@ page import="facebook4j.Facebook" %>
<%@ page import="facebook4j.Post" %>
<%@ page import="facebook4j.Comment" %>
<%@ page import="facebook4j.Notification" %>
<%@ page import="facebook4j.ResponseList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
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
<script src="<c:url value='/resources/js/jquery.validate.js'/>"></script>
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
<body onkeypress="isKeyPressed(event)">
<div class="container">
    <div class="row">
<%FacebookServiceImpl fb = (FacebookServiceImpl) request.getSession().getAttribute("fb");%>
<%ResponseList<Notification> noti = fb.getNotifications(); %>
<c:choose>
  <c:when test="${domainUser.isEnabled() == true}">
        <nav class="navbar navbar-default" role="navigation">
            <div class="col-lg-6">
                <form action="${pageContext.request.contextPath}/facebook/post/add" method="post" onSubmit="validate();return false;" role="form" id="formMessage" enctype="multipart/form-data">
                <div class="input-group">                   
                    <input name="messageTxt" id="messageTxt" type="text" class="form-control" placeholder="Escribir mensaje..." aria-describedby="mtxt" required/>
                    <div id="mtxt" class="tooltip" role="tooltip">Escriba el mensaje y seleccione las redes para postearlo</div>
                    <div class="input-group-btn">
                        <button type="button" class="btn btn-default" name="privacidad" title="Privacidad de mensaje" data-toggle="modal" data-target="#modalPrivacidad"><span class="glyphicon glyphicon-lock"></span></button>
                        <button id="adjuntar" class="btn btn-default" type="button" title="Adjuntar foto" lang="es">Adjuntar Foto</button>
                        <button type="submit" id="publishingButton" class="btn btn-default" title="Publicar mensaje" lang="es">Enviar</button>
                        <input type="file" id="filePhoto" name="filePhoto" class="hidden" title="Foto adjunta" aria-hidden="true"/>
                        <div class="modal fade" id="modalPrivacidad" tabindex="-1" role="dialog" aria-labelledby="Privacidad" aria-hidden="true">
						  <div class="modal-dialog">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
						        <h4 class="modal-title">Elija nivel de privacidad</h4>
						      </div>
						      <div class="modal-body">
							      <div class="radio">
									  
									  <input type="radio" name="privacy" id="privacity1" value="SELF" title="Solo Yo">
									  <label for="privacity1">Solo Yo</label>
								  </div>
							      <div class="radio">
									  
									    <input type="radio" name="privacy" id="privacity2" value="FRIENDS_OF_FRIENDS" title="amigos de mis amigos">
									  <label for="privacity2">Amigos de mis amigos</label>  
								  </div>
								  <div class="radio">
									  
									    <input type="radio" name="privacy" id="privacity3" value="ALL_FRIENDS" title="Amigos">
									  <label for="privacity3">Amigos</label>  
								  </div>
								  <div class="radio">
	                                  
	                                  <input type="radio" name="privacy" id="privacity4" value="EVERYONE" title="Público" checked>
	                                  <label for="privacity4">Público</label>  
	                              </div>						      				        
						      </div>
						      <div class="modal-footer">
						        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						      </div>
						    </div><!-- /.modal-content -->
						  </div><!-- /.modal-dialog -->
						</div><!-- /.modal -->
                    <input type="submit" name="submit" value="enviar"/>
                    </div>
                </div>
                <div id="noticeMessage" class=""></div>
                <label for="messageTxt" class="error hidden" style="display:none important;" aria-hidden="true">Escriba un mensaje</label>
                <div class="checkbox-block input-sm">
                    <span>Publicar en:</span>
                    
                    <input name="publishingIn" id="network1" type="checkbox" value="Facebook" title="Publicar en Facebook" <%if(fb.getVisiblesSocialNetworks().isEmpty()){ %>disabled<%} %>/>
                    <label for="network1">Facebook</label>
                    
                    <input name="publishingIn" id="network2" type="checkbox" value="Twitter" title="Publicar en Twitter" <c:if test="${domainUser.getSocialNetwork().isEmpty()}">disabled</c:if>>
                    <label for="network2">Twitter</label>
                    
                    <input name="publishingIn" id="network3" type="checkbox" value="Todos" title="Publicar en todos" <c:if test="${domainUser.getSocialNetwork().isEmpty()}">disabled</c:if>>
                    <label for="network3">Todos</label>
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
                                <button type="submit" id="searchButton" class="btn btn-default <c:if test='${domainUser.getSocialNetwork().isEmpty()}'>disabled</c:if>" title="Buscar" lang="es">Buscar</button>
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
        <audio id="noticeAudio">
            <source src="<c:url value='/resources/audio/no-se-pudo-publicar-mensaje.wma'/>" type="audio/wma"></source>
        </audio>
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
<%InsonetUser domainUser = (InsonetUser) request.getSession().getAttribute("domainUser");%>
<%List<SocialNetwork> nets = domainUser.getSocialNetwork();%>
<%if(nets.size() > 0) {%>
    <div class="row">    
    <%for(SocialNetwork n : nets) {%>
        <%if(n.isVisible()==true) {%>
        <div class="col-lg-6" id="columnSocial-<%=n.getId() %>">
            <p class="text-center">Facebook - <%=n.getUsernameSocial()%><button type="button" id="columnSocial-<%=n.getId() %>-x" onclick="closeColumn(this.id)" class="close" aria-hidden="true" title="Cerrar columna">&times;</button></p>    
        <%ResponseList<Post> posts = fb.getPosts(n.getId()); %>
        <%if(posts != null) {%>
        <% for(Post p : posts) { %>
            <div class="media">
            <%URL url = p.getPicture();%>
            <%if(p.getMessage() != null || url != null) {%>
                <a class="pull-left" href="#">
                    <img class="media-object" src="holder.js/64x64" alt="Foto de perfil de <%=p.getFrom().getName() %>" class="img-thumbnail" style="width:64px; height:64px;">
                </a>
                <div class="media-body">
                    <p class="media-heading"><a href="#"><%=p.getFrom().getName() %></a></p>
                    <p class="element"><%=p.getCreatedTime()%></p>
                <%if(p.getMessage() != null) {%>
                    <p class="element"><%=p.getMessage() %></p>
                <%}%>
                <%if(url != null) {%>
	                <img class="media-object" src="<%=url.toString()%>" alt="Foto que publico <%=p.getFrom().getName() %>" class="img-thumbnail" style="width:140px; height:180px;">
	            <%}%>
	            
                    <a id="likePost-<%=p.getId()%>-<%=n.getId()%>" href="#" title="Marcar con me gusta">Me gusta</a> . <a href="#" title="Comentar">Comentar</a> . <a href="#" title="Compartir">Compartir</a>
                </div>
            <%}%>
            </div>
            <%ResponseList<Comment> comm = fb.getCommentsByPost(p.getId(), n.getId()); %>
            <%if(comm != null) {%>
            <%for(Comment c : comm) {%>
            <ul class="media-list">
                <li class="media">
                    <a class="pull-left" href="#">
                        <img class="media-object" src="holder.js/64x64" alt="Foto de perfil de <%=c.getFrom().getName() %>" title="Foto de perfil de <%=c.getFrom().getName() %>">
                    </a>
                    <div class="media-body">
                        <p class="media-heading"><a href="#"><%=c.getFrom().getName() %></a> comentario</p>
                        <p class="element"><%=c.getMessage() %></p>
                        <a id="likeComm-<%=c.getId()%>-<%=n.getId()%>" href="#" title="Marcar con me gusta">Me gusta</a>
                    </div>
                </li>
            </ul>
            <%} %>
            <%} %>
            <%if(p.getMessage() != null || url != null) {%>
            <div class="media">
                <ul class="media-list">
                    <li class="media">
                        <a class="pull-left" href="#">
                            <img class="media-object" src="holder.js/64x64" alt="Mi foto de perfil" title="Mi foto de perfil">
                        </a>
                        <div class="media-body">
                            <form id="commentForm-<%=p.getId()%>" role="form">
                                <input type="hidden" name="commentHidden" id="commentHidden-<%=p.getId()%>" value="<%=n.getId()%>" title="Post a comentar"/>
                                <div class="input-group">
                                    <input type="text" id="comment-<%=p.getId()%>" alt="Escribe un comentario" title="Escribe un comentario" name="comment-<%=p.getId()%>" class="form-control" placeholder="Escribe un comentario" aria-describedby="tp<%=p.getId()%>" required/>
                                    <span class="input-group-btn">
                                        <button type="button" id="pictureComm-<%=p.getId()%>" class="btn btn-default" title="Adjuntar foto a comentario">Adjuntar Foto</button>
                                    </span>
                                    <input type="file" name="adPicture" id="adPicture-<%=p.getId()%>" class="hidden" title="Adjuntar foto" aria-hidden="true"/>                              
                                </div>
                                <div class="input-group">
                                    <label for="comment-<%=p.getId()%>" class="error hidden" style="display:none important;">Escriba un mensaje</label>
                                    <span class="help-block">Presione enter para postear</span>
                                    <div id="tp<%=p.getId()%>" class="tooltip" role="tooltip">Presione enter para postear</div>
                                </div>
                                <input type="submit" name="comments" value="enviar" alt="comentar" class="hidden" aria-hidden="true"/>
                            </form>
                        </div>
                    
                    </li>
                </ul>
            </div>
            <%} %>
           
        <% }%>
        <%} %>
        </div>
        <%} %>
    <%}%>
    </div>
<%}%>
</div>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
</body>
</html>