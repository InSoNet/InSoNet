package ar.com.insonet.controller.home;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.Session;
import org.hibernate.Transaction;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;

import ar.com.insonet.dao.HibernateUtil;
import ar.com.insonet.dao.InsonetUserDAO;
import ar.com.insonet.dao.InsonetUserValidator;
import ar.com.insonet.dao.SocialNetworkDAO;
import ar.com.insonet.dao.UserDAO;
import ar.com.insonet.model.AccessToken;
import ar.com.insonet.model.InsonetUser;
import ar.com.insonet.model.Role;
import ar.com.insonet.model.User;
import ar.com.insonet.model.SocialNetwork;
import ar.com.insonet.service.FacebookServiceImpl;
import ar.com.insonet.service.SendMailService;

@Controller
public class HomeController {
	
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	@Autowired
	private InsonetUserDAO insonetUserDAO;
	@Autowired
	SocialNetworkDAO socialNetworkDAO;
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
		InsonetUser domainUser;
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
		//Necesario para obtener los posts
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		if (facebook == null) {
			facebook = new FacebookFactory().getInstance();
			request.getSession().setAttribute("facebook", facebook);
		}
		domainUser = insonetUserDAO.getInsonetUserByUsername(userDetails.getUsername());
		model.addAttribute("user", userDetails);
		/*List<SocialNetwork> list = new ArrayList<SocialNetwork>();
		list = domainUser.getSocialNetwork();
		domainUser.setSocialNetwork(list);*/
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
    		HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
    		user.setRole(setRole);
    		insonetUserDAO.addInsonetUser(user);
    		
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
    public String addSocialNetwork(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
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
		//AccessToken de Facebook
		//TODO cuando se trabaje con Twitter ver de cambiar nombre o no
		AccessToken accessToken = (AccessToken) request.getSession().getAttribute("accessToken");
		if (accessToken != null) {
			//request.getSession().setAttribute("facebook", null);
			String urlTarget = facebookService.logout(request, response);
			return "redirect:" + urlTarget;
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
    
    /*public void logoutFacebook(HttpServletRequest request) {
    	Facebook facebook = (Facebook) request.getSession()..getAttribute("facebook");
		String accessToken = "";
		
		try {
			accessToken = facebook.getOAuthAccessToken().getToken();
		} catch (Exception e) {
			throw new ServletException(e);
		}
		request.getSession().invalidate();

		// Log Out of the Facebook
		StringBuffer next = request.getRequestURL();
		int index = next.lastIndexOf("/");
		next.replace(index + 1, next.length(), "");
		//response.sendRedirect("http://www.facebook.com/logout.php?next="	+ next.toString() + "&access_token=" + accessToken);
	
		return "http://www.facebook.com/logout.php?next="	+ next.toString() + "&access_token=" + accessToken;
    }*/
    @RequestMapping(value="/addcolumn", method = RequestMethod.GET)
    public String addcolumn(Model model) throws Exception {
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
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
		//AccessToken de Facebook
		//TODO cuando se trabaje con Twitter ver de cambiar nombre o no
		AccessToken accessToken = (AccessToken) request.getSession().getAttribute("accessToken");
		domainUser = userDAO.getUserByUsername(userDetails.getUsername());
		request.getSession().setAttribute("domainUser", domainUser);
		model.addAttribute("domainUser", domainUser);
		
    	return "/addcolumn";
    }
    
    @RequestMapping(value="/addcolumn", method = RequestMethod.POST)
    public String addcolumnsPost(HttpServletRequest request, Model model) throws Exception {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User domainUser = null;
		UserDetails userDetails = null;
		
		String[] columns = request.getParameterValues("socialNetworks");
		if(columns != null) {
			for(String c : columns) {
				int idaux = Integer.parseInt(c);
				SocialNetwork nw = socialNetworkDAO.getSocialNetwork(idaux);
				nw.setVisible(true);
				socialNetworkDAO.updateSocialNetwork(nw);
			}
		}
		if (auth.getPrincipal() instanceof UserDetails) {
			userDetails = (UserDetails)auth.getPrincipal();
		} else {
        	userDetails = (UserDetails) request.getUserPrincipal();
        }		
		domainUser = insonetUserDAO.getInsonetUserByUsername(userDetails.getUsername());
		request.getSession().setAttribute("domainUser", domainUser);
		model.addAttribute("domainUser", domainUser);
		
    	return "/addcolumn";
    }
    
    @RequestMapping(value="/hidecol/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String hidecolumn(@PathVariable String id) throws Exception {
    	String result = "nok";
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	UserDetails userDetails = null;
		if (auth.getPrincipal() instanceof UserDetails) {
			userDetails = (UserDetails)auth.getPrincipal();
		} else {
        	userDetails = (UserDetails) request.getUserPrincipal();
        }
		InsonetUser insonetUser = insonetUserDAO.getInsonetUserByUsername(userDetails.getUsername());
		int until = insonetUser.getSocialNetwork().size();
		for(int i=0;i<until;i++) {
			int idaux = Integer.parseInt(id);
			if (insonetUser.getSocialNetwork().get(i).getId() == idaux) {
				insonetUser.getSocialNetwork().get(i).setVisible(false);
				socialNetworkDAO.updateSocialNetwork(insonetUser.getSocialNetwork().get(i));
				request.getSession().setAttribute("domainUser", insonetUser);
				result = "ok";
			}
		}
		return result;
    }

}
