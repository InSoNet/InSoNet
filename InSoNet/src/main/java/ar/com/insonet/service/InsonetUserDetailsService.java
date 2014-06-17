package ar.com.insonet.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.insonet.dao.UserDAO;

@Service
@Transactional(readOnly=true)
public class InsonetUserDetailsService implements UserDetailsService {

	@Autowired
    private UserDAO userDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		ar.com.insonet.model.User domainUser = userDAO.getUserByUsername(username);
        
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
 
        return new User(
                domainUser.getUsername(),
                domainUser.getPassword(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                getAuthorities(domainUser.getRole().getId())
        );
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }
	
	public List<String> getRoles(Integer idRole) {
		List<String> roles = new ArrayList<String>();
        
		if (idRole.intValue() == 1) {
            roles.add("ROLE_USER");
        } else if (idRole.intValue() == 2) {
            roles.add("ROLE_MODERATOR");
        } else if (idRole.intValue() == 3){
        	roles.add("ROLE_ADMIN");
        }
		
        return roles;
    }
     
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
         
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}
