package ar.com.insonet.twitter.sign;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = -7453606094644144082L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Twitter twitter = new TwitterFactory().getInstance();
		request.getSession().setAttribute("twitter", twitter);
		try {
			StringBuffer callbackURL = request.getRequestURL();
			int index = callbackURL.lastIndexOf("/");
			callbackURL.replace(index, callbackURL.length(), "").append(
					"/callback");

			RequestToken requestToken = twitter
					.getOAuthRequestToken(callbackURL.toString());
			request.getSession().setAttribute("requestToken", requestToken);
			response.sendRedirect(requestToken.getAuthenticationURL());

		} catch (TwitterException e) {
			throw new ServletException(e);
		}

	}
}