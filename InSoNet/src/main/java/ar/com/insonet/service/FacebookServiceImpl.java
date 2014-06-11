package ar.com.insonet.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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
import facebook4j.Post;
import facebook4j.ResponseList;

public class FacebookServiceImpl {
	
	@Autowired
	AccessToken accessToken;
	@Autowired
	SocialNetwork socialNetwork;
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
		Facebook facebook = new FacebookFactory().getInstance();
		request.getSession().setAttribute("facebook", facebook);
		StringBuffer callbackURL = request.getRequestURL();
		int index = callbackURL.lastIndexOf("/");
		callbackURL.replace(index, callbackURL.length(), "").append("/callback");
		
		return facebook.getOAuthAuthorizationURL(callbackURL.toString());
	}
	
	public String callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		facebook4j.auth.AccessToken fbToken = null; 
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		String oauthCode = request.getParameter("code");
		try {
			fbToken = facebook.getOAuthAccessToken(oauthCode);
			String usernameSocial = facebook.getMe().getUsername();
			if(usernameSocial == null){
				usernameSocial=facebook.getMe().getName();
			}
			accessToken.setExpire(fbToken.getExpires());
			accessToken.setAccessToken(fbToken.getToken());
			//if (facebook.getAuthorization().isEnabled()) {
			//	accessToken.setLoginStatus(LoginStatus.CONNECTED.toString());
			//} else {
				accessToken.setLoginStatus("connected");
	        //}
			socialNetwork.setAccessToken(accessToken);
			
			//SocialNetworkType socialNetworkType = (SocialNetworkType) session.get(SocialNetworkType.class, new Integer(1));
			//1=facebook,2=twitter
			SocialNetworkType socialNetworkType = socialNetworkTypeDAO.getSocialNetworkType(1);
			socialNetwork.setSocialNetworkType(socialNetworkType);
			
			socialNetwork.setUsernameSocial(usernameSocial);
			//Habilitar uno de los dos para guardar socialNetwork 
			//session.save(socialNetwork);
			//socialNetworkDAO.addSocialNetwork(socialNetwork);
			UserDetails userDetails = (UserDetails)request.getSession().getAttribute("principal");
			if(userDetails == null) {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				userDetails = (UserDetails) auth.getPrincipal();
				request.getSession().setAttribute("principal", userDetails);
			}
			String loggedUsername = userDetails.getUsername();
			InsonetUser insonetUser = insonetUserDAO.getInsonetUserByUsername(loggedUsername);
			//lo carga por Lazy, probar!
			//ar.com.insonet.model.User domainUser = userDAO.getUserByUsername(loggedUsername);
			//insonetUser.setRole(domainUser.getRole());
			//Transaction tx = session.beginTransaction();
			List<SocialNetwork> list = insonetUser.getSocialNetwork();
			//List<SocialNetwork> list =  new ArrayList<SocialNetwork>();
			list.add(socialNetwork);
			insonetUser.setSocialNetwork(list);
			
			insonetUserDAO.updateInsonetUser(insonetUser);
			
			//tx.commit();
			
			request.getSession().setAttribute("accessToken", accessToken);
			
		} catch (FacebookException e) {
			throw new ServletException(e);
		}
		
		//TODO: to convert short-lived token to long-lived token
		//TODO: to store access token and login status
		//TODO: to add the token as a session variable to identify that browser session with a particular person
		//http://facebook4j.org/javadoc/index.html ver setOAuthAccessToken(AccessToken accessToken, String callbackURL) 
		//si sirve para obtener un code de un access token como hace
		//https://graph.facebook.com/oauth/client_code?access_token=...&client_secret=...&redirect_uri=...&client_id=...
		return "/facebook/index";
	}
	
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		String accessToken = "";
		
		try {
			accessToken = facebook.getOAuthAccessToken().getToken();
		} catch (Exception e) {
			throw new ServletException(e);
		}
		request.getSession().invalidate();

		// Log Out of the Facebook
		StringBuffer next = request.getRequestURL();
		int index = next.lastIndexOf("/");
		next.replace(index + 1, next.length(), "");
		//response.sendRedirect("http://www.facebook.com/logout.php?next="	+ next.toString() + "&access_token=" + accessToken);
	
		return "http://www.facebook.com/logout.php?next="	+ next.toString() + "&access_token=" + accessToken;
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
	
	public ResponseList<Post> getPosts(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResponseList<Post> postsList;
		request.setCharacterEncoding("UTF-8");

		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		try {
			postsList = facebook.getPosts();
			
		} catch (FacebookException e) {
			throw new ServletException(e);
		}
				
		return postsList;
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
}
