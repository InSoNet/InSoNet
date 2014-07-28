package ar.com.insonet.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ar.com.insonet.dao.AccessTokenDAO;
import ar.com.insonet.dao.HibernateUtil;
import ar.com.insonet.dao.InsonetUserDAO;
import ar.com.insonet.dao.SocialNetworkDAO;
import ar.com.insonet.dao.SocialNetworkTypeDAO;
import ar.com.insonet.dao.UserDAO;
import ar.com.insonet.model.AccessToken;
import ar.com.insonet.model.InsonetUser;
import ar.com.insonet.model.SocialNetwork;
import ar.com.insonet.model.SocialNetworkType;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Friend;
import facebook4j.Media;
import facebook4j.Notification;
import facebook4j.Page;
import facebook4j.PagePhotoUpdate;
import facebook4j.Photo;
import facebook4j.PhotoUpdate;
import facebook4j.Post;
import facebook4j.Comment;
import facebook4j.PostUpdate;
import facebook4j.PrivacyBuilder;
import facebook4j.PrivacyParameter;
import facebook4j.PrivacyType;
import facebook4j.RawAPIResponse;
import facebook4j.ResponseList;
import facebook4j.User;
import facebook4j.auth.Authorization;

@Service
public class FacebookServiceImpl implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Autowired
	AccessToken accessToken;
	@Autowired
	SocialNetwork socialNetwork;
	@Autowired
	AccessTokenDAO accessTokenDAO;
	@Autowired
	SocialNetworkDAO socialNetworkDAO;
	@Autowired 
	SocialNetworkTypeDAO socialNetworkTypeDAO;
	@Autowired
	InsonetUserDAO insonetUserDAO;
	@Autowired
    private UserDAO userDAO;
	
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	
	/*private enum LoginStatus {
	    CONNECTED, NOT_AUTHORIZED, UNKNOWN
	}*/
	
	public String signin(HttpServletRequest request, HttpServletResponse response) throws Exception {

		/*Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		if (facebook != null) {
			facebook.shutdown();
		}*/
		Facebook facebook = new FacebookFactory().getInstance();
		
		request.getSession().setAttribute("facebook", facebook);
		
		StringBuffer callbackURL = request.getRequestURL();
		int index = callbackURL.lastIndexOf("/");
		callbackURL.replace(index, callbackURL.length(), "").append("/callback");
		
		return facebook.getOAuthAuthorizationURL(callbackURL.toString());
	}
	
	public String callback(String code, String error, String error_code) throws Exception {
		
		facebook4j.auth.AccessToken fbToken = null;
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		if(error == null && error_code == null) {
			String oauthCode = code;
			try {
				fbToken = facebook.getOAuthAccessToken(oauthCode);
				String usernameSocial = facebook.getMe().getUsername();
				if(usernameSocial == null) {
					usernameSocial=facebook.getMe().getName();
				}
				
				accessToken.setExpire(fbToken.getExpires());
				accessToken.setAccessToken(fbToken.getToken());
				accessToken.setLoginStatus("connected");
		        socialNetwork.setAccessToken(accessToken);
				
				facebook.setOAuthAccessToken(fbToken);
				SocialNetworkType socialNetworkType = socialNetworkTypeDAO.getSocialNetworkType(1);
				socialNetwork.setSocialNetworkType(socialNetworkType);
				String userSocialId = facebook.getMe().getId();
				socialNetwork.setUserSocialId(userSocialId);
				socialNetwork.setUsernameSocial(usernameSocial);
				socialNetwork.setVisible(true);
				//TODO evitar error de crear dos veces la misma lista de amigos
				//String friendListId = facebook.friends().createFriendlist("InSoNet");
				//socialNetwork.setFriendListId(friendListId);
				
				UserDetails userDetails = (UserDetails)request.getSession().getAttribute("principal");
				if(userDetails == null) {
					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					userDetails = (UserDetails) auth.getPrincipal();
					request.getSession().setAttribute("principal", userDetails);
				}
				
				String loggedUsername = userDetails.getUsername();
				InsonetUser insonetUser = insonetUserDAO.getInsonetUserByUsername(loggedUsername);
				socialNetworkDAO.addSocialNetwork(socialNetwork);
				List<SocialNetwork> list = insonetUser.getSocialNetwork();
				list.add(socialNetwork);
				insonetUser.setSocialNetwork(list);
				insonetUserDAO.addSocialNetwork(insonetUser);
				
				
			} catch (FacebookException e) {
				throw new ServletException(e);
			}
		} else {
			//TODO log de no aceptar agregar red social.
		}
		
		return "http://www.facebook.com/logout.php?next=" + "http://localhost:8080/InSoNet/" + "&access_token=" + accessToken.getAccessToken();
		
	}
	
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		String accessToken = "";
		AccessToken accessTokenDB = (AccessToken) request.getSession().getAttribute("accessToken");
		
		request.getSession().setAttribute("accessToken", null);
		try {
			accessToken = accessTokenDB.getAccessToken();//facebook.getOAuthAccessToken().getToken();
		} catch (Exception e) {
			throw new ServletException(e);
		}
		request.getSession().invalidate();

		// Log Out of the Facebook
		StringBuffer next = request.getRequestURL();
		int index = next.lastIndexOf("/");
		next.replace(index + 1, next.length(), "");//por ahora solo se llama desde /addnet
		//response.sendRedirect("http://www.facebook.com/logout.php?next="	+ next.toString() + "&access_token=" + accessToken);
		//return "https:/graph.facebook.com/" + facebook.getMe().getId() + "/permissions?next=" + next.toString() + "&method=delete&access_token=" +;
		return "http://www.facebook.com/logout.php?next="	+ "http://localhost:8080/InSoNet/addnet" + "&access_token=" + accessToken;
	}
	
	public String post(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		String message = request.getParameter("message");
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		try {
			facebook.postStatusMessage(message);
		} catch (FacebookException e) {
			throw new ServletException(e);
		}
				
		return "/facebook/posts?list";
	}
	
	public String addPost(String message, String privacy, String fileName) throws Exception {
		String result = "nok";
		String idPost;
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		request.setCharacterEncoding("UTF-8");
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		
		List<SocialNetwork> list = getVisiblesSocialNetworks();
		PhotoUpdate photoUpdate = null;
		PostUpdate pu = null;
		InputStream auxMedia = null;
		/*if(photo != null) {
			auxMedia = photo.getMediaBody();			
			pp = new PagePhotoUpdate(photo);
		}*/
		Media mediaFile = null;
		PrivacyParameter pb = null;
		//if(privacy != "") {
			switch(privacy) {
				case "SELF":
					pb = new PrivacyBuilder().setValue(PrivacyType.SELF).build();
					break;
				case "FRIENDS_OF_FRIENDS":
					pb = new PrivacyBuilder().setValue(PrivacyType.FRIENDS_OF_FRIENDS).build();
					break;
				case "ALL_FRIENDS":
					pb = new PrivacyBuilder().setValue(PrivacyType.ALL_FRIENDS).build();
					break;
				case "EVERYONE":
					pb = new PrivacyBuilder().setValue(PrivacyType.EVERYONE).build();
					break;
				default:
					pb = new PrivacyBuilder().setValue(PrivacyType.EVERYONE).build();
					break;
			}
		//}
		
		try {
			for(SocialNetwork sn : list) {
				if(sn.getSocialNetworkType().getId() == 1) {
					AccessToken accesstokenDB = sn.getAccessToken();
					facebook4j.auth.AccessToken accesstoken = new facebook4j.auth.AccessToken(accesstokenDB.getAccessToken());
					facebook.setOAuthAccessToken(accesstoken);
					
					//if(photo != null) {
					if(fileName != null) {
						Path path = FileSystems.getDefault().getPath("c:\\Projects\\Java\\InSoNetDAO\\InSoNet\\InSoNet\\src\\main\\webapp\\resources\\tmp", fileName);
						InputStream file = Files.newInputStream(path);
						mediaFile = new Media(fileName, file);
					}
					if(mediaFile != null) {	
						Media photo = mediaFile;
						//Al albun insonet, y es solo para amigos
						photoUpdate = new PhotoUpdate(photo);
						photoUpdate.setMessage(message);
						idPost = facebook.photos().postPhoto(photoUpdate);
						
					} else {
						pu = new PostUpdate(message);
						pu.setPrivacy(pb);
						idPost = facebook.postFeed(pu);
						//TODO Para poder postear una foto va ser necesario subirlo a un ftp publico o cdn gratuito.
						//para usar pu.picture(URL picture)
						
						//idPost = facebook.posts().postStatusMessage(message);
					}
					
					result = "ok";
				}
			}
			
		} catch (FacebookException e) {
			throw new ServletException(e);
		}
				
		return result;
	}
	
	public String addComment(String id, String message, String post) throws ServletException {
		String result = "nok";
		String idComm ="";
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		if(facebook == null) {
			facebook = new FacebookFactory().getInstance();
			request.getSession().setAttribute("facebook", facebook);
		}
		SocialNetwork sn = socialNetworkDAO.getSocialNetwork(Integer.parseInt(id));
		try {
			AccessToken accesstokenDB = sn.getAccessToken();
			facebook4j.auth.AccessToken accesstoken = new facebook4j.auth.AccessToken(accesstokenDB.getAccessToken());
			facebook.setOAuthAccessToken(accesstoken);
			idComm = facebook.posts().commentPost(post, message);
			result = "ok";
		
		} catch (FacebookException e) {
			throw new ServletException(e);
		}
		
		return result;
	}
	
	public List<SocialNetwork> getVisiblesSocialNetworks() {
		List<SocialNetwork> aux = new ArrayList<SocialNetwork>();
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		UserDetails userDetails = (UserDetails)request.getSession().getAttribute("principal");
		if(userDetails == null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			userDetails = (UserDetails) auth.getPrincipal();
			request.getSession().setAttribute("principal", userDetails);
		}
		String loggedUsername = userDetails.getUsername();
		InsonetUser insonetUser = insonetUserDAO.getInsonetUserByUsername(loggedUsername);
		List<SocialNetwork> list = insonetUser.getSocialNetwork();
		if(list != null) {
			for(SocialNetwork sn : list) {
				if(sn.isVisible()) {
					aux.add(sn);
				}
			}
		}
		
		return aux;
	}
	
	public ResponseList<Post> getPosts(int idfb) throws Exception {
		ResponseList<Post> postsList = null;
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		request.setCharacterEncoding("UTF-8");

		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		AccessToken accesstokenDB = socialNetworkDAO.getSocialNetwork(idfb).getAccessToken();
		facebook4j.auth.AccessToken accesstoken = new facebook4j.auth.AccessToken(accesstokenDB.getAccessToken());
		try {
			facebook.setOAuthAccessToken(accesstoken);
			postsList = facebook.posts().getFeed();//.getPosts();
			
		} catch (FacebookException e) {
			int subcode = e.getErrorSubcode();
			if(subcode == 458) {
				//El usuario borro la app de entre sus aplicaciones. Debe loguearse otra vez.
				UserDetails userDetails = (UserDetails)request.getSession().getAttribute("principal");
				if(userDetails == null) {
					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					userDetails = (UserDetails) auth.getPrincipal();
					request.getSession().setAttribute("principal", userDetails);
				}
				String loggedUsername = userDetails.getUsername();
				InsonetUser insonetUser = insonetUserDAO.getInsonetUserByUsername(loggedUsername);
				//quitamos la red del usuario actual
				insonetUserDAO.delSocialNetwork(insonetUser, idfb);
				socialNetworkDAO.deleteSocialNetwork(idfb);
			}
			//throw new ServletException("El Usuario ha desautorizado su aplicación", e);
			
		}
		
		return postsList;
	}
	
	public ResponseList<Comment> getCommentsByPost(String idPost, int idfb) throws Exception {
		ResponseList<Comment> commList = null;
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		//request.setCharacterEncoding("UTF-8");

		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		if(facebook == null) {
			facebook = new FacebookFactory().getInstance();
		}
		AccessToken accesstokenDB = socialNetworkDAO.getSocialNetwork(idfb).getAccessToken();
		facebook4j.auth.AccessToken accesstoken = new facebook4j.auth.AccessToken(accesstokenDB.getAccessToken());
		try {
			facebook.setOAuthAccessToken(accesstoken); 
			commList = facebook.posts().getPostComments(idPost);
		} catch (FacebookException e) {
			int subcode = e.getErrorSubcode();
			if(subcode == 458) {
				//El usuario borro la app de entre sus aplicaciones. Debe loguearse otra vez.
				UserDetails userDetails = (UserDetails)request.getSession().getAttribute("principal");
				if(userDetails == null) {
					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					userDetails = (UserDetails) auth.getPrincipal();
					request.getSession().setAttribute("principal", userDetails);
				}
				String loggedUsername = userDetails.getUsername();
				InsonetUser insonetUser = insonetUserDAO.getInsonetUserByUsername(loggedUsername);
				//quitamos la red del usuario actual
				insonetUserDAO.delSocialNetwork(insonetUser, idfb);
				socialNetworkDAO.deleteSocialNetwork(idfb);
			}
			//throw new ServletException("El Usuario ha desautorizado su aplicación", e);
			
		}
		
		return commList;
	}
	
	public ResponseList<Notification> getNotifications() {
		
		ResponseList<Notification> noti = null;
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		if(facebook == null) {
			facebook = new FacebookFactory().getInstance();
		}
		//Obtenemos los user_id de las redes visibles, y por cada uno sus noticias
		List<SocialNetwork> socialNetworks = getVisiblesSocialNetworks();
		int idfb = 0;
		try {
			if(socialNetworks != null) {
				for(SocialNetwork sn : socialNetworks) {
					ResponseList<Notification> aux = null;
					idfb = sn.getId();
					String user_id = sn.getUserSocialId();
					AccessToken accesstokenDB = sn.getAccessToken();
					facebook4j.auth.AccessToken accesstoken = new facebook4j.auth.AccessToken(accesstokenDB.getAccessToken());
					facebook.setOAuthAccessToken(accesstoken);
					aux = facebook.notifications().getNotifications(user_id);
					if(noti == null) {
						noti = aux;
					} else {
						noti.addAll(aux);
					}
				}
			}
		} catch(FacebookException e) {
			int subcode = e.getErrorSubcode();
			if(subcode == 458) {
				//El usuario borro la app de entre sus aplicaciones. Debe loguearse otra vez.
				UserDetails userDetails = (UserDetails)request.getSession().getAttribute("principal");
				if(userDetails == null) {
					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					userDetails = (UserDetails) auth.getPrincipal();
					request.getSession().setAttribute("principal", userDetails);
				}
				String loggedUsername = userDetails.getUsername();
				InsonetUser insonetUser = insonetUserDAO.getInsonetUserByUsername(loggedUsername);
				//quitamos la red del usuario actual
				insonetUserDAO.delSocialNetwork(insonetUser, idfb);
				socialNetworkDAO.deleteSocialNetwork(idfb);
			}
		}
		
		return noti;
	}
	
	public ResponseList<User> searchUsers(String name) {
		ResponseList<User> users = null;
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		if(facebook == null) {
			facebook = new FacebookFactory().getInstance();
		}
		
		//Obtenemos las redes visibles, y por cada uno sus users con la esperanza de obtener relaciones
		List<SocialNetwork> socialNetworks = getVisiblesSocialNetworks();
		String query = name;//"https://graph.facebook.com/v2.0/search?q=Java&type=user";
		
		try {
			if(socialNetworks != null) {
				for(SocialNetwork sn : socialNetworks) {
					ResponseList<User> aux = null;
					AccessToken accesstokenDB = sn.getAccessToken();
					facebook4j.auth.AccessToken accesstoken = new facebook4j.auth.AccessToken(accesstokenDB.getAccessToken());
					facebook.setOAuthAccessToken(accesstoken);
					aux = facebook.searchUsers(query);
					if(users == null) {
						users = aux;
					} else {
						users.addAll(aux);
					}
				}
			}
		} catch(FacebookException e) {
			
		}
		
		return users;
	}
	
	public ResponseList<Page> searchPages(String name) {
		ResponseList<Page> pages = null;
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		if(facebook == null) {
			facebook = new FacebookFactory().getInstance();
		}
		
		List<SocialNetwork> socialNetworks = getVisiblesSocialNetworks();
		String query = name;
		
		try {
			if(socialNetworks != null) {
				for(SocialNetwork sn : socialNetworks) {
					ResponseList<Page> aux = null;
					AccessToken accesstokenDB = sn.getAccessToken();
					facebook4j.auth.AccessToken accesstoken = new facebook4j.auth.AccessToken(accesstokenDB.getAccessToken());
					facebook.setOAuthAccessToken(accesstoken);
					aux = facebook.searchPages(query);
					if(pages == null) {
						pages = aux;
					} else {
						pages.addAll(aux);
					}
				}
			}
			
		} catch(FacebookException e) {
			
		}
		
		return pages;
	}
	
	public Post getPost(int idfb, String idpost) throws FacebookException {
		Post post = null;
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		//Obtenemos red social y su access_token
		/*AccessToken accesstokenDB = socialNetworkDAO.getSocialNetwork(idfb).getAccessToken();
		facebook4j.auth.AccessToken accesstoken = new facebook4j.auth.AccessToken(accesstokenDB.getAccessToken());
		facebook.setOAuthAccessToken(accesstoken);
		
		ResponseList<Post> list = facebook.getPosts();
		for (Post p : list) {
	        if (p.getId() == idpost) {
	            post = p;
	            break;
	        }
	    }*/
		post = facebook.getPost(idpost);
		return post;
	}
	
	public ResponseList<Friend> getFriends(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ResponseList<Friend> friendsList;
		request.setCharacterEncoding("UTF-8");

		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		try {
			friendsList = facebook.getFriends();
			
		} catch (FacebookException e) {
			throw new ServletException(e);
		}
				
		return friendsList;
	}
	
	public boolean isFriend(String userId) {
		boolean aux = false;
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		
		if(facebook == null) {
			facebook = new FacebookFactory().getInstance();
		}
		
		List<SocialNetwork> socialNetworks = getVisiblesSocialNetworks();
				
		try {
			//String friendId = null;
			if(socialNetworks != null) {
				for(SocialNetwork sn : socialNetworks) {
					AccessToken accesstokenDB = sn.getAccessToken();
					facebook4j.auth.AccessToken accesstoken = new facebook4j.auth.AccessToken(accesstokenDB.getAccessToken());
					facebook.setOAuthAccessToken(accesstoken);
					ResponseList<Friend> friends = facebook.getBelongsFriend(userId);
					for(Friend f : friends) {
						if(f.getId() == userId) {
							aux = true;
						}
					}
				}
			}
			
		} catch(FacebookException e) {
			
		}
		
		return aux;
	}
	
	public ResponseList<Post> getStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResponseList<Post> postsList;
		
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		
		try {
			postsList = facebook.getStatuses();
		} catch (FacebookException e) {
			throw new ServletException(e);
		}
		
		return postsList;
	}
	
	@ExceptionHandler(FacebookException.class)
	public ResponseList<Post> handleIOException(FacebookException ex) {
		ResponseList<Post> listposts = null;
	
		return listposts;
	}
}
