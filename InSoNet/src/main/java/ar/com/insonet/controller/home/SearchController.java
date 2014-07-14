package ar.com.insonet.controller.home;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import ar.com.insonet.dao.InsonetUserDAO;
import ar.com.insonet.exception.RequestMethodNotSupportedException;
import ar.com.insonet.model.InsonetUser;
import ar.com.insonet.service.FacebookServiceImpl;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.Page;
import facebook4j.ResponseList;
import facebook4j.User;

@Controller
public class SearchController {
	
	@Autowired
	private FacebookServiceImpl facebookService;
	@Autowired
	private InsonetUserDAO insonetUserDAO;
		
	@RequestMapping(value="/search", params="searchTxt", method = RequestMethod.POST)
	public String search(String searchTxt, Model model) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		InsonetUser domainUser = null;
		UserDetails userDetails = null;
		
		if (auth.getPrincipal() instanceof UserDetails) {
			userDetails = (UserDetails)auth.getPrincipal();
		} else {
        	userDetails = (UserDetails) request.getUserPrincipal();
        }
		
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		if (facebook == null) {
			facebook = new FacebookFactory().getInstance();
			request.getSession().setAttribute("facebook", facebook);
		}
		
		domainUser = insonetUserDAO.getInsonetUserByUsername(userDetails.getUsername());
		model.addAttribute("user", userDetails);
		model.addAttribute("domainUser", domainUser);
		//Obtenemos la busqueda para cada red
		ResponseList<User> usersFB = facebookService.searchUsers(searchTxt);
		ResponseList<Page> pagesFB = facebookService.searchPages(searchTxt);
		request.getSession().setAttribute("fb", facebookService);
		request.getSession().setAttribute("usersFB", usersFB);
		request.getSession().setAttribute("pagesFB", pagesFB);
		request.getSession().setAttribute("domainUser", domainUser);
		
		return "/search";
	}
	
	@ExceptionHandler(RequestMethodNotSupportedException.class)
    public ModelAndView handleRequestMethodNotSupportedException(HttpServletRequest request, Exception ex){
        //logger.error("Requested URL="+request.getRequestURL());
        //logger.error("Exception Raised="+ex);
         
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
         
        modelAndView.setViewName("error");
        return modelAndView;
    } 
}
