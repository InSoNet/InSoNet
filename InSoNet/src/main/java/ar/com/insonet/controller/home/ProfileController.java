package ar.com.insonet.controller.home;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ar.com.insonet.model.InsonetUser;
import ar.com.insonet.model.Role;
import ar.com.insonet.model.User;
import ar.com.insonet.service.SendMailService;
import ar.com.insonet.dao.HibernateUtil;
import ar.com.insonet.dao.InsonetUserDAO;
import ar.com.insonet.dao.UserDAO;

@Controller
@Component("profile")
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
	UserDAO userDAO;
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
	
	@ModelAttribute("insonetUser")
    private InsonetUser getInsonetUser() {
        return insonetUser;
    }
	
	@RequestMapping(method = RequestMethod.GET)
	public String indexAction(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//UserDetails user = (UserDetails)auth.getPrincipal();
		String user;
		User domainUser;
		UserDetails userDetails = null;
		if (auth.getPrincipal() instanceof UserDetails) {
			//user = ((UserDetails)auth.getPrincipal()).getUsername();
			userDetails = (UserDetails)auth.getPrincipal();
			domainUser = userDAO.getUserByUsername(userDetails.getUsername());
        } else {
            return "/index";
        }
		model.addAttribute("user", userDetails);
		model.addAttribute("insonetUser", domainUser);
		return "/profile";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateAction(@ModelAttribute("insonetUser") InsonetUser user, BindingResult result) throws ServletException {
    	
		validator.validate(user, result);
		if (result.hasErrors()) {
    		return "/profile";
    	}    	    	
		try {
    		HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    		Role setRole = (Role) HibernateUtil.getSessionFactory().getCurrentSession().get(Role.class, new Integer(1));//1=user,2=moderator,3=admin
    		HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
    		user.setRole(setRole);
    		InsonetUser iuaux = insonetUserDAO.getInsonetUserByUsername(user.getUsername());
    		user.setId(iuaux.getId());
    		if(!user.getEmail().equals(iuaux.getEmail())) {
    			user.setEnabled(false);
    			sendMailService.sendMailConfirm(user.getEmail());
    		}
    		
    		insonetUserDAO.updateProfileInsonetUser(user);  		
    		
    		//sendMailService.sendMail(user.getEmail());            
    	} catch(MailException mailex) {
    		throw new ServletException(mailex);
    	} catch(Exception ex) {
    		//HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
    		throw new ServletException(ex); 
    	}
    	
    	return "redirect:/";
    	
    }
	@RequestMapping(value="/sendEmailConfirm", method = RequestMethod.GET)
	public @ResponseBody String sendEmailAction() {
		String result = "ok";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = null;
		Integer id = null;
		InsonetUser insonetUsr = null; 
		UserDetails userDetails = null;
		if (auth.getPrincipal() instanceof UserDetails) {
			userDetails = (UserDetails)auth.getPrincipal();
			user = userDAO.getUserByUsername(userDetails.getUsername());
			HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			id = user.getId();
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
			
        }
		try {
			HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			insonetUsr = insonetUserDAO.getInsonetUser(id);
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
			sendMailService.sendMail(insonetUsr.getEmail());
		} catch(MailException ex) {
			result = "nok";
		}
		
		return result;
		
	}
}
