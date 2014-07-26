package ar.com.insonet.controller.twitter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.com.insonet.service.TwitterServiceImpl;

@Controller
@Component("twitter/signin")
public class SigninController {

	private TwitterServiceImpl twService;

	@Autowired
	private SigninController(TwitterServiceImpl twService) {
		this.twService = twService;
	}

	@RequestMapping(value = "/twitter")
	public String indexHandler(Model model) {
		return "redirect:/twitter/index";
	}

	@RequestMapping(value = "/twitter/index")
	public ModelAndView loginHandler() {
		ModelAndView mav = new ModelAndView();
		// Use the view named "home" to display the data
		mav.setViewName("/twitter/index");
		// Add a model object to be displayed by the view
		mav.addObject("message", "Twitter login");

		return mav;
	}

	@RequestMapping(value = "/twitter/signin")
	public String signinHandler(HttpServletRequest request,
			HttpServletResponse response) {
		String urlTarget = null;
		try {
			urlTarget = this.twService.signin(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:" + urlTarget;
	}

	@RequestMapping(value = "/twitter/callback")
	public String callbackHandler(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "error_code", required = false) String error_code,
			@RequestParam(value = "code", required = false) String code)
			throws ServletException {

		String urlTarget = "/index";
		urlTarget = this.twService.callback(code, error, error_code);
		return "redirect:" + urlTarget;
	}
}
