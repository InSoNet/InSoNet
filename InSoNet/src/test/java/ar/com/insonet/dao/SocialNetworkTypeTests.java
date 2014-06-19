package ar.com.insonet.dao;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import ar.com.insonet.model.SocialNetworkType;

public class SocialNetworkTypeTests {

	@Test
	public void testAddSocialNetworkType() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		Transaction tx = session.beginTransaction();
		SocialNetworkType setSocialNetworkTypeFb = (SocialNetworkType) session.get(SocialNetworkType.class, new Integer(1));//1=facebook,2=twitter
		SocialNetworkType setSocialNetworkTypeTw = (SocialNetworkType) session.get(SocialNetworkType.class, new Integer(2));
		
		if (setSocialNetworkTypeFb == null) {
			setSocialNetworkTypeFb = new SocialNetworkType();
			setSocialNetworkTypeFb.setSocialNetworkType("facebook");
			session.save(setSocialNetworkTypeFb);
		}
		
		if (setSocialNetworkTypeTw == null ) {
			setSocialNetworkTypeTw = new SocialNetworkType();
			setSocialNetworkTypeTw.setSocialNetworkType("twitter");
			session.save(setSocialNetworkTypeTw);
		}
		
		tx.commit();
	}

}
