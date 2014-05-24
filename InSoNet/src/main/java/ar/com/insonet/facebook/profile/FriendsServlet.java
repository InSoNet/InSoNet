package ar.com.insonet.facebook.profile;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Friend;
import facebook4j.ResponseList;

public class FriendsServlet extends HttpServlet {
	private static final long serialVersionUID = 4179545353414298791L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ResponseList<Friend> lista;
		request.setCharacterEncoding("UTF-8");

		Facebook facebook = (Facebook) request.getSession().getAttribute(
				"facebook");
		try {
			lista = facebook.getFriends();
			System.out.println(lista);
		} catch (FacebookException e) {
			throw new ServletException(e);
		}
		request.setAttribute("friends", lista);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/facebook/friends.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
}