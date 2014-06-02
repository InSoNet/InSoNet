package ar.com.insonet.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsServiceTests {

	//static ApplicationContext applicationContext = null;
    //static InsonetUserDetailsService insonetUserDetailsService = null;
     
    @BeforeClass
    public static void setup()
    {
        //Create application context instance
        //applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        //Get user details service configured in configuration
        //insonetUserDetailsService = applicationContext.getBean("insonetUserDetailsService", InsonetUserDetailsService.class);
    }
     
    @Test
    public void testValidRole()
    {
        //Get the user by username from configured user details service
    	/*UserDetails userDetails = insonetUserDetailsService.loadUserByUsername("demo");
        Authentication authToken = new UsernamePasswordAuthenticationToken (userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        Iterator<? extends GrantedAuthority> iterator = userDetails.getAuthorities().iterator();
        GrantedAuthority ga = null;
        while ( iterator.hasNext() ) {
        	ga = iterator.next();
        }
        assertEquals("ROLE_USER", ga.getAuthority().toString());//authorities.get(0).getAuthority());
        */
    }

}
