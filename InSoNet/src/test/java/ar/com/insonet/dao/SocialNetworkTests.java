package ar.com.insonet.dao;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.insonet.model.AccessToken;
import ar.com.insonet.model.SocialNetwork;
import ar.com.insonet.model.SocialNetworkType;

public class SocialNetworkTests {

	
	//AccessToken accessToken = new AccessToken();
	//SocialNetwork socialNetwork = new SocialNetwork();
	//SocialNetworkTypeDAO socialNetworkTypeDAO = new SocialNetworkTypeDAOImpl();
	
	@Test
	public void testAdd() {
		
		/*Long expire = 1111L;
		String token = "CAACwZCWZB1ocwBAKuyxHhAvm9tySAlE9fpexTI28QRwkDFDHoFalhX8jC3QemkUEym08RDCYJL1fZC37ZCMXDGYeEILYA9prZBpwsZAtFEM2lOBmxWZCwkK1siqQKPxCpGPMU9Cd29WjJ7YTgIEn8H4dIuJ0KMYyorhZAhKjEUZBRJobZB73Vf2tYfSqm43tRpdyAZD";
		String usernameSocial = "rodolfo";
		//1=facebook,2=twitter
		SocialNetworkType socialNetworkType = socialNetworkTypeDAO.getSocialNetworkType(1);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		accessToken.setExpire(expire);
		accessToken.setAccessToken(token);
		accessToken.setLoginStatus("connected");
        socialNetwork.setAccessToken(accessToken);
        socialNetwork.setUsernameSocial(usernameSocial);
		socialNetwork.setSocialNetworkType(socialNetworkType);
		
		session.save(socialNetwork);
	
		
		tx.commit();*/
	}

}
