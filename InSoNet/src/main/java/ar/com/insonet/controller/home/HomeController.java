package ar.com.insonet.controller.home;

import javax.servlet.ServletException;

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
	
	/*@RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginHandler() throws ServletException {
    	
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("login");
    	mav.addObject("message", "Inicio de sesión");
    	    	
    	return mav;
    }*/
    
    /*@RequestMapping(value = "/login", method = RequestMethod.POST)
    protected String login() throws ServletException{
    	
    	//ModelAndView mav = new ModelAndView();
    	//mav.setViewName("login");
    	//mav.addObject("message", "Bienvenido username");
    	
    	return "/login?error";
    }*/
    
    //Spring Security see this :
  	@RequestMapping(value = "/login", method = RequestMethod.GET)
  	public ModelAndView loginHandler(
  		@RequestParam(value = "error", required = false) String error,
  		@RequestParam(value = "logout", required = false) String logout) {
   
  		ModelAndView model = new ModelAndView();
  		if (error != null) {
  			model.addObject("error", "Nombre de usuario o contraseña incorrecto!");
  		}
   
  		if (logout != null) {
  			model.addObject("msg", "Has cerrado sesión con exito.");
  		}
  		model.setViewName("login");
  		model.addObject("message", "Inicio de sesión");
   
  		return model;
   
  	}

}
