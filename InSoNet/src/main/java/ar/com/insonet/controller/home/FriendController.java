package ar.com.insonet.controller.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ar.com.insonet.dao.FriendDAO;
import ar.com.insonet.dao.HibernateUtil;
import ar.com.insonet.dao.InsonetUserDAO;
import ar.com.insonet.model.Friend;
import ar.com.insonet.model.InsonetUser;
import ar.com.insonet.model.SocialNetwork;
import ar.com.insonet.service.FacebookServiceImpl;


@Controller
//@Component("friend/facebook")
public class FriendController {
	
	@Autowired
	private FacebookServiceImpl facebookService;
	@Autowired
	private InsonetUserDAO insonetUserDAO;
	@Autowired
	private FriendDAO friendDAO;
	@Autowired
	private Friend friend;

	@RequestMapping(value="/friend/facebook/add/{id}", method=RequestMethod.GET)
	@ResponseBody
	public String addFriendFB(@PathVariable String id) {
		String result = "ok";
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    	
		UserDetails userDetails = (UserDetails)request.getSession().getAttribute("principal");
		if(userDetails == null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			userDetails = (UserDetails) auth.getPrincipal();
			request.getSession().setAttribute("principal", userDetails);
		}
		String loggedUsername = userDetails.getUsername();
		InsonetUser insonetUser = insonetUserDAO.getInsonetUserByUsername(loggedUsername);
		/*Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Hibernate.initialize(insonetUser.getFriend());
		tx.commit();*/
		List<Friend> list = insonetUser.getFriend();
		//Obtener el Facebook visible
		List<SocialNetwork> fb = facebookService.getVisiblesSocialNetworks();
		//TODO: Por ahora solo se agrega como amigo a la primer red facebook visible
		friend.setUserId(id);
		friend.setSocialNetwork(fb.get(0));
		friendDAO.addFriend(friend);
		list.add(friend);
		insonetUser.setFriend(list);
		insonetUserDAO.addFriend(insonetUser);
				
		return result;
	}
}
