package ar.com.insonet.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Friend;
import facebook4j.Post;
import facebook4j.ResponseList;

public class FacebookServiceImpl {

	public String signin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Facebook facebook = new FacebookFactory().getInstance();
		request.getSession().setAttribute("facebook", facebook);
		StringBuffer callbackURL = request.getRequestURL();
		int index = callbackURL.lastIndexOf("/");
		callbackURL.replace(index, callbackURL.length(), "").append("/callback");
		//response.sendRedirect(facebook.getOAuthAuthorizationURL(callbackURL.toString()));
		return facebook.getOAuthAuthorizationURL(callbackURL.toString());
	}
	
	public String callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		String oauthCode = request.getParameter("code");
		try {
			facebook.getOAuthAccessToken(oauthCode);
		} catch (FacebookException e) {
			throw new ServletException(e);
		}
		//response.sendRedirect(request.getContextPath() + "/");
		
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