package ar.com.insonet.controller.home;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.com.insonet.dao.HibernateUtil;
import ar.com.insonet.dao.InsonetUserDAO;
import ar.com.insonet.dao.InsonetUserValidator;
import ar.com.insonet.model.InsonetUser;
import ar.com.insonet.model.Role;
import ar.com.insonet.model.User;
import ar.com.insonet.service.SendMailService;

@Controller
public class HomeController {
	
	//@Autowired
	//private ApplicationContext applicationContext;
	@Autowired
	private InsonetUserDAO insonetUserDAO;
	@Autowired
	private InsonetUser insonetUser;
	@Autowired
	private SendMailService sendMailService;
	@Autowired
    private Validator validator;
    
    public void setValidator(Validator validator) {
        this.validator = validator;
    }
    
	/*@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new InsonetUserValidator());
	}*/
	
	@RequestMapping(value={"/", "/index"}, method=RequestMethod.GET)
    public String defaultHandler(Model model) {

        return "/index";
    }
	
	@ModelAttribute("insonetUser")
    private InsonetUser getInsonetUser() {
        return insonetUser;
    }
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signupHandler() throws ServletException {
    	
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("signup");
    	mav.addObject("message", "Cree una cuenta");
    	    	    	
    	return mav;
    }
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String postbackHandler(@ModelAttribute("insonetUser") InsonetUser user, BindingResult result) throws ServletException {
    	
		validator.validate(user, result);
		if (result.hasErrors()) {
    		return "/signup";
    	}    	    	
		try {
    		HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    		Role setRole = (Role) HibernateUtil.getSessionFactory().getCurrentSession().get(Role.class, new Integer(1));//1=user,2=moderator,3=admin
    		user.setRole(setRole);
    		insonetUserDAO.addInsonetUser(user);    		
    		HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
    		//SendMailService mm = (SendMailService) applicationContext.getBean("sendMail");
            sendMailService.sendMailConfirm(user.getEmail());
    	//} catch(MailException mailex) {
    		//throw new ServletException(mailex);
    	} catch(Exception ex) {
    		HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
    		throw new ServletException(ex); 
    	}
    	    	
    	return "redirect:/success";
    	
    }
    
	@RequestMapping(value = "/success", method = RequestMethod.GET)
    protected String successHandler(Model model) {
    
		return "/success";
	}
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    protected String createHandler(@Valid InsonetUser user, BindingResult result) throws ServletException {
    	
    	if (result.hasErrors()) {
    		return "/signup";
    	}
    	
    	try {
    		HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    		Role setRole = (Role) HibernateUtil.getSessionFactory().getCurrentSession().get(Role.class, new Integer(1));//1=user,2=moderator,3=admin
    		user.setRole(setRole);
    		insonetUserDAO.addInsonetUser(user);    		
    		HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
    	} catch(Exception ex) {
    		HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
    		throw new ServletException(ex); 
    	}
    	    	
    	return "/create";
    }
    
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
