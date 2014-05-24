package ar.com.insonet.facebook.profile;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Post;
import facebook4j.ResponseList;

public class StatusServlet extends HttpServlet {
	private static final long serialVersionUID = -7453606094644144082L;

	@Override
	protected void doGet(HttpServletRequest request,
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
				.getRequestDispatcher("/facebok/posts.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
