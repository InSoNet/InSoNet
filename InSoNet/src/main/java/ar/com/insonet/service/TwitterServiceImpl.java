package ar.com.insonet.service;

import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import ar.com.insonet.dao.AccessTokenDAO;
import ar.com.insonet.dao.InsonetUserDAO;
import ar.com.insonet.dao.SocialNetworkDAO;
import ar.com.insonet.dao.SocialNetworkTypeDAO;
import ar.com.insonet.dao.UserDAO;
import ar.com.insonet.model.AccessToken;
import ar.com.insonet.model.InsonetUser;
import ar.com.insonet.model.SocialNetwork;
import ar.com.insonet.model.SocialNetworkType;

public class TwitterServiceImpl implements Serializable {

	/**
	 * 
	 */
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

	public TwitterServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public String signin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		Twitter twitter = new TwitterFactory().getInstance();
		request.getSession().setAttribute("twitter", twitter);
		try {
			StringBuffer callbackURL = request.getRequestURL();
			int index = callbackURL.lastIndexOf("/");
			callbackURL.replace(index, callbackURL.length(), "").append(
					"/callback");

			RequestToken requestToken = twitter
					.getOAuthRequestToken(callbackURL.toString());
			// RequestToken requestToken = twitter.getOAuthRequestToken();
			request.getSession().setAttribute("requestToken", requestToken);

			return requestToken.getAuthenticationURL();

		} catch (TwitterException e) {
			throw new ServletException(e);
		}
	}

	public String callback(String code, String error, String error_code)
			throws ServletException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		Twitter twitter = (Twitter) request.getSession()
				.getAttribute("twitter");
		RequestToken requestToken = (RequestToken) request.getSession()
				.getAttribute("requestToken");
		String verifier = request.getParameter("oauth_verifier");
		try {
			twitter.getOAuthAccessToken(requestToken, verifier);
			request.getSession().removeAttribute("requestToken");
			String username = twitter.getScreenName();
			accessToken.setAccessToken(verifier);
			
			SocialNetworkType socialNetworkType = socialNetworkTypeDAO.getSocialNetworkType(2);
			socialNetwork.setSocialNetworkType(socialNetworkType);
			String userSocialId = String.valueOf(twitter.getId());
			socialNetwork.setUserSocialId(userSocialId);
			socialNetwork.setUsernameSocial(username);
			socialNetwork.setVisible(true);
			
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
		} catch (TwitterException e) {
			throw new ServletException(e);
		}
		return "/index/";
	}
}
