package ar.com.insonet.controller.facebook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.com.insonet.service.FacebookServiceImpl;

@Controller
@Component("facebook/signin")
public class SigninController {
	
	@Autowired
	private ApplicationContext ctx;
	private FacebookServiceImpl fbService;

	@Autowired
	private SigninController(ApplicationContext applicationContext, FacebookServiceImpl facebookService) {
		this.fbService = facebookService;
	}
	
	@RequestMapping("/facebook")
    public String indexHandler(Model model) {

        return "redirect:/facebook/index";
    }
	
	@RequestMapping("/facebook/index")
    public ModelAndView loginHandler() {

        ModelAndView mav = new ModelAndView();
        // Use the view named "home" to display the data
        mav.setViewName("/facebook/index");
        // Add a model object to be displayed by the view
        mav.addObject("message", "Facebook login");

        return mav;
    }
	
	@RequestMapping("/facebook/signin")
	public String signinHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	    String urlTarget = this.fbService.signin(request, response);
        
        return "redirect:" + urlTarget;
	}
	
	@RequestMapping(value="/facebook/callback")
	public String callbackHandler(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "error_code", required = false) String error_code,
	  		@RequestParam(value = "code", required = false) String code) throws Exception {
		        
        String urlTarget = fbService.callback(code, error, error_code);
        
        return "redirect:" + urlTarget;
	}
	
	@RequestMapping(value="/facebook/logout")
	protected String logoutHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
        String urlTarget = fbService.logout(request, response);
        
        return "redirect:" + urlTarget;
	}

}
