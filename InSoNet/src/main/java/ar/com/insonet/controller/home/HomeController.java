package ar.com.insonet.controller.home;

import javax.servlet.ServletException;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@RequestMapping(value={"/", "/index"}, method=RequestMethod.GET)
    public String defaultHandler(Model model) {

        return "/index";
    }
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
    public ModelAndView homeHandler() {

        ModelAndView mav = new ModelAndView();
        // Use the view named "home" to display the data
        mav.setViewName("home");
        // Add a model object to be displayed by the view
        mav.addObject("message", "Bienvenido a InSoNet");

        return mav;
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    protected ModelAndView loginHandler() throws ServletException {
    	
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("login");
    	mav.addObject("message", "Ingrese usuario y contraseña");
    	    	
    	return mav;
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    protected String login() throws ServletException{
    	
    	//ModelAndView mav = new ModelAndView();
    	//mav.setViewName("login");
    	//mav.addObject("message", "Bienvenido username");
    	
    	return "/login?error";
    }
    
    //Spring Security see this :
  	/*@RequestMapping(value = "/login", method = RequestMethod.GET)
  	public ModelAndView login(
  		@RequestParam(value = "error", required = false) String error,
  		@RequestParam(value = "logout", required = false) String logout) {
   
  		ModelAndView model = new ModelAndView();
  		if (error != null) {
  			model.addObject("error", "Invalid username and password!");
  		}
   
  		if (logout != null) {
  			model.addObject("msg", "You've been logged out successfully.");
  		}
  		model.setViewName("login");
   
  		return model;
   
  	}*/

}
