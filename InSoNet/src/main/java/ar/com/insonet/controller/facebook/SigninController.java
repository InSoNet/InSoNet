package ar.com.insonet.controller.facebook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.com.insonet.service.FacebookServiceImpl;

@Controller
@RequestMapping("/facebook")
public class SigninController {
	
	private ApplicationContext ctx;

	private SigninController() {
		this.ctx = new ClassPathXmlApplicationContext("classpath:ar/com/insonet/services.xml");
	}
	
	@RequestMapping("/")
    public String indexHandler(Model model) {

        model.addAttribute("message", "Facebook login");
        
        return "/facebook/index";
    }
	
	@RequestMapping("/index")
    public ModelAndView loginHandler() {

        ModelAndView mav = new ModelAndView();
        // Use the view named "home" to display the data
        mav.setViewName("/facebook/index");
        // Add a model object to be displayed by the view
        mav.addObject("message", "Facebook login");

        return mav;
    }
	
	@RequestMapping("/signin")
	public String signinHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	    FacebookServiceImpl fbService = ctx.getBean("facebookService", FacebookServiceImpl.class);
        String urlTarget = fbService.signin(request, response);
        
        return "redirect:" + urlTarget;
	}
	
	@RequestMapping(value="/callback", params="code")
	public String callbackHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
		        
        FacebookServiceImpl fbService = ctx.getBean("facebookService", FacebookServiceImpl.class);
        String urlTarget = fbService.callback(request, response);
        
        return "redirect:" + urlTarget;
	}
	
	@RequestMapping(value="/logout")
	protected String logoutHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
        FacebookServiceImpl fbService = ctx.getBean("facebookService", FacebookServiceImpl.class);
        String urlTarget = fbService.logout(request, response);
        
        return "redirect:" + urlTarget;
	}

}
