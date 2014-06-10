package ar.com.insonet.controller.twitter;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Post;
import facebook4j.ResponseList;

import twitter4j.Twitter;
import twitter4j.TwitterException;

@Controller
@Component("twitter/profile")
public class ProfileController {

	protected void post(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String text = request.getParameter("text");
		Twitter twitter = (Twitter) request.getSession()
				.getAttribute("twitter");
		try {
			twitter.updateStatus(text);
		} catch (TwitterException e) {
			throw new ServletException(e);
		}
		response.sendRedirect(request.getContextPath() + "/twitter/");
	}
	
	protected void status(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Facebook facebook = (Facebook) request.getSession().getAttribute(
				"facebook");
		ResponseList<Post> posts = null;
		try {
			posts = facebook.getStatuses();
		} catch (FacebookException e) {
			e.printStackTrace();
		}
		request.setAttribute("posts", posts);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/posts.jsp");
		dispatcher.forward(request, response);
	}

	
}
