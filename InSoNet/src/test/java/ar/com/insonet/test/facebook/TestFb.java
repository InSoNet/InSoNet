package ar.com.insonet.test.facebook;

import static org.junit.Assert.*;

import java.awt.PageAttributes.MediaType;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;

import ar.com.insonet.dao.HibernateUtil;

public class TestFb {

	@Test
	public void testLogin() throws FacebookException {
		Facebook facebook = new FacebookFactory().getInstance();
        String url = "https://www.facebook.com/dialog/oauth?client_id=194602547388876&redirect_uri=http://localhost:8080/InSoNet/callback&scope=email,publish_actions,read_stream";
		String result = facebook.getOAuthAuthorizationURL("http://localhost:8080/InSoNet/callback");
        assertEquals(url,result);
	}
    
    @Test
    public void testAddSocialNetwork() throws FacebookException {
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    	
    }
}
