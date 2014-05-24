package ar.com.insonet.controller.facebook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import facebook4j.Facebook;

import ar.com.insonet.service.FacebookService;

@Controller
public class FacebookController {
	
	@RequestMapping("/fblogin")
    public ModelAndView loginHandler() {

        ModelAndView mav = new ModelAndView();
        // Use the view named "home" to display the data
        mav.setViewName("fblogin");
        // Add a model object to be displayed by the view
        mav.addObject("message", "Facebook login");

        return mav;
    }
	
	@RequestMapping("/signin")
	public String signinHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("services.xml");
        FacebookService fbService = ctx.getBean(FacebookService.class);
        String urlTarget = fbService.signin(request, response);
        
        return "redirect:" + urlTarget;
	}
	
	@RequestMapping(value="/callback", params="code")
	public String callbackHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
		        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("services.xml");
        FacebookService fbService = ctx.getBean(FacebookService.class);
        String urlTarget = fbService.callback(request, response);
        
        return "redirect:" + urlTarget;
	}
	
	@RequestMapping(value="/logout")
	protected String logoutHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("services.xml");
        FacebookService fbService = ctx.getBean(FacebookService.class);
        String urlTarget = fbService.logout(request, response);
        
        return "redirect:" + urlTarget;
	}

}
