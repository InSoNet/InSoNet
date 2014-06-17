package ar.com.insonet.controller.home;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import facebook4j.Facebook;
import facebook4j.FacebookFactory;

import ar.com.insonet.dao.HibernateUtil;
import ar.com.insonet.dao.InsonetUserDAO;
import ar.com.insonet.dao.InsonetUserValidator;
import ar.com.insonet.dao.UserDAO;
import ar.com.insonet.model.InsonetUser;
import ar.com.insonet.model.Role;
import ar.com.insonet.model.User;
import ar.com.insonet.service.FacebookServiceImpl;
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
    @Autowired
    UserDAO userDAO;
    @Autowired
    FacebookServiceImpl facebookService;
    
    public void setValidator(Validator validator) {
        this.validator = validator;
    }
    
    @ModelAttribute("insonetUser")
    private InsonetUser getInsonetUser() {
        return insonetUser;
    }
    
	/*@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new InsonetUserValidator());
	}*/
    
	@RequestMapping(value={"/", "/index"}, method=RequestMethod.GET)
    public String defaultHandler(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User domainUser;
		UserDetails userDetails = null;
		if (auth.getPrincipal() instanceof UserDetails) {
			//user = ((UserDetails)auth.getPrincipal()).getUsername();
			userDetails = (UserDetails)auth.getPrincipal();
			
        } else {
        	userDetails = (UserDetails) request.getUserPrincipal();
            //return "/index";
        }
		//Si llego aca es porque se logueo correctamente
		//TODO: Obtener la redes sociales que tiene agregadas el usuario
		//TODO: Primero verificar si por Lazy Spring las carga por defecto al llamar a getSocialNetwork
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		if (facebook == null) {
			facebook = new FacebookFactory().getInstance();
			request.getSession().setAttribute("facebook", facebook);
		}
		domainUser = userDAO.getUserByUsername(userDetails.getUsername());
		model.addAttribute("user", userDetails);
		model.addAttribute("domainUser", domainUser);
		//model.addAttribute("fb", facebookService);
		//request.getSession().setAttribute("principal", userDetails);
		request.getSession().setAttribute("fb", facebookService);
		request.getSession().setAttribute("domainUser", domainUser);
        
		return "/index";
    }
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signupHandler() throws ServletException {
    	
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("signup");
    	mav.addObject("message", "Cree una cuenta");
    	    	    	
    	return mav;
    }
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String postbackHandler(@ModelAttribute("insonetUser") InsonetUser user, BindingResult result, HttpServletRequest httpServletRequest) throws ServletException {
    	
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
    		httpServletRequest.login(user.getUsername(), user.getPassword());
    		sendMailService.sendMailConfirm(user.getEmail());            
    	} catch(MailException mailex) {
    		throw new ServletException(mailex);
    	} catch(Exception ex) {
    		HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
    		throw new ServletException(ex); 
    	}
    	
    	return "redirect:/success";
    	
    }
    
	@PreAuthorize("hasRole('ROLE_USER')")
	//@PreAuthorize("#c.name == authentication.name")
	@RequestMapping(value = "/success", method = RequestMethod.GET)
    protected String successHandler(Model model) {
		//TODO: que se cargue la seccion de perfil, cabecera, pie de pagina y mensaje de registro exitoso.
		return "/success";
	}
    /*@RequestMapping(value = "/create", method = RequestMethod.POST)
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
    }*/
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
  	public ModelAndView loginAction(
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
    
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmAction() {
		User domainUser;
		UserDetails userDetails = null;
		
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth.getPrincipal() instanceof UserDetails) {
			userDetails = (UserDetails)auth.getPrincipal();
			domainUser = userDAO.getUserByUsername(userDetails.getUsername());
			domainUser.setEnabled(true);
			HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			userDAO.updateUser(domainUser);
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        } else {
            return "redirect:/login";
        }
		
		return "redirect:/index";
    }
    
    @RequestMapping(value="/addnet", method = RequestMethod.GET)
    public String addSocialNetwork(HttpServletRequest request, Model model) {
    	//TODO Implementar programacion orientada a aspectos
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User domainUser = null;
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
		domainUser = userDAO.getUserByUsername(userDetails.getUsername());
		//model.addAttribute("user", userDetails);
		model.addAttribute("domainUser", domainUser);
		//model.addAttribute("fb", facebookService);
		//request.getSession().setAttribute("principal", userDetails);
		//request.getSession().setAttribute("fb", facebookService);
		//request.getSession().setAttribute("domainUser", domainUser);
    	
    	return "/addnet";
    }

}
