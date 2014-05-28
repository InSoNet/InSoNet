package ar.com.insonet.controller.facebook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ar.com.insonet.service.FacebookServiceImpl;

import facebook4j.Friend;
import facebook4j.Post;
import facebook4j.ResponseList;

@Controller
public class ProfileController {

	private ApplicationContext ctx;
	
	private ProfileController() {
		this.ctx = new ClassPathXmlApplicationContext("classpath:ar/com/insonet/services.xml");
	}
	
	@RequestMapping(value="/facebook/posts", params="message", method=RequestMethod.POST)
	public String postHandler(HttpServletRequest request,	HttpServletResponse response) throws Exception {
		
		FacebookServiceImpl fbService = ctx.getBean("facebookService", FacebookServiceImpl.class);
        String urlTarget = fbService.post(request, response);
        
        return "redirect:" + urlTarget;
	}
	
	@RequestMapping(value="/facebook/posts", params="list", method=RequestMethod.GET)
	public String postListHandler(HttpServletRequest request,	HttpServletResponse response, Model model) throws Exception {
		
		ResponseList<Post> postsList;
		
		FacebookServiceImpl fbService = ctx.getBean("facebookService", FacebookServiceImpl.class);
        postsList = fbService.getPosts(request, response);
        
        model.addAttribute("posts", postsList);
        
        return "/facebook/posts";
	}
	
	@RequestMapping(value="/facebook/friends")
	protected String friendsHandler(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ResponseList<Friend> friendsList;
		
		FacebookServiceImpl fbService = ctx.getBean("facebookService", FacebookServiceImpl.class);
        friendsList = fbService.getFriends(request, response);
        
        model.addAttribute("friends", friendsList);
        
        return "/facebook/friends";
        		
	}

	@RequestMapping(value="/facebook/status")
	protected String statusHandler(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ResponseList<Post> postsList;
		
		FacebookServiceImpl fbService = ctx.getBean("facebookService", FacebookServiceImpl.class);
        postsList = fbService.getStatus(request, response);
        
        model.addAttribute("status", postsList);
        
        return "/facebook/status";
	}
	
	/*@RequestMapping(value="/facebook/stream")
	protected void streamHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");

		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");

		RequestDispatcher dispatcher = request.getRequestDispatcher("/facebook/friends.jsp");
		dispatcher.forward(request, response);
		
	}*/
}