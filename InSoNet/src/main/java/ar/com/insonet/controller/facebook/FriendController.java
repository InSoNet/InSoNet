package ar.com.insonet.controller.facebook;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.FormSubmitEvent.MethodType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ar.com.insonet.dao.InsonetUserDAO;
import ar.com.insonet.model.AccessToken;
import ar.com.insonet.model.InsonetUser;
import ar.com.insonet.model.SocialNetwork;
import ar.com.insonet.service.FacebookServiceImpl;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Friend;
import facebook4j.Page;
import facebook4j.ResponseList;
import facebook4j.User;

@Controller
@Component("facebook/friend")
public class FriendController {
	
	@Autowired
	InsonetUserDAO insonetUserDAO;
	@Autowired
	FacebookServiceImpl facebookService;
	
	//Los amigos se agregaran a todas las redes fb visibles
	//Quizas sea conveniente preguntar a cual red quiere agregar como amigo
	@RequestMapping(value="/facebook/friend/add/{id}", method=RequestMethod.GET)
	@ResponseBody
	public String add(@PathVariable String id, Model model) {
		String result = "nok";
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    	Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		if (facebook == null) {
			facebook = new FacebookFactory().getInstance();
			request.getSession().setAttribute("facebook", facebook);
		}
		List<SocialNetwork> list = facebookService.getVisiblesSocialNetworks();
		
		try {			
			for(SocialNetwork sn : list) {
				AccessToken accesstokenDB = sn.getAccessToken();
				facebook4j.auth.AccessToken accesstoken = new facebook4j.auth.AccessToken(accesstokenDB.getAccessToken());
				facebook.setOAuthAccessToken(accesstoken);
				Boolean isFriendAdd = facebook.friends().addFriendlistMember(sn.getFriendListId(), id);
				if(isFriendAdd == true) {
					result = "ok";
				}
			}
		} catch (FacebookException e) {
			e.printStackTrace();
		}
						
		return result;
	}
	
	
	@RequestMapping(value="/facebook/friend/list")
	protected String list(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ResponseList<Friend> friendsList;
		
		friendsList = facebookService.getFriends(request, response);
        
        model.addAttribute("friends", friendsList);
        
        return "/facebook/friend/list";
        		
	}
}
