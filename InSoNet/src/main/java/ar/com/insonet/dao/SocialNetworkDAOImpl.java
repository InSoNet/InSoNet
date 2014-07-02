package ar.com.insonet.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.com.insonet.model.InsonetUser;
import ar.com.insonet.model.SocialNetwork;

public class SocialNetworkDAOImpl implements SocialNetworkDAO, Serializable {

	private static final long serialVersionUID = 1L;

	private Session getCurrentSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	
	public void addSocialNetwork(SocialNetwork socialNetwork) {
		Transaction tx = getCurrentSession().beginTransaction();
		getCurrentSession().save(socialNetwork);
		tx.commit();
	}

	
	public void updateSocialNetwork(SocialNetwork socialNetwork) {
		SocialNetwork socialNetworkToUpdate = getSocialNetwork(socialNetwork.getId());
		socialNetworkToUpdate.setAccessToken(socialNetwork.getAccessToken());
		socialNetworkToUpdate.setUserSocialId(socialNetwork.getUserSocialId());
		socialNetworkToUpdate.setUsernameSocial(socialNetwork.getUsernameSocial());
		socialNetworkToUpdate.setSocialNetworkType(socialNetwork.getSocialNetworkType());
		socialNetworkToUpdate.setVisible(socialNetwork.isVisible());
		Transaction tx = getCurrentSession().beginTransaction();
		getCurrentSession().update(socialNetworkToUpdate);
		tx.commit();
		
	}

	
	public SocialNetwork getSocialNetwork(int id) {
		Transaction tx = getCurrentSession().beginTransaction();
		SocialNetwork socialNetwork = (SocialNetwork) getCurrentSession().get(SocialNetwork.class, id);
		tx.commit();
		return socialNetwork;
	}

	
	public void deleteSocialNetwork(int id) {
		SocialNetwork socialNetwork = getSocialNetwork(id);
		if(socialNetwork != null) {
			Transaction tx = getCurrentSession().beginTransaction();
			getCurrentSession().delete(socialNetwork);
			tx.commit();
		}
	}

	@SuppressWarnings("unchecked")
	public List<SocialNetwork> getSocialNetworks() {
		Transaction tx = getCurrentSession().beginTransaction();
		List<SocialNetwork> list = getCurrentSession().createQuery("from SocialNetwork").list();
		tx.commit();
		return list;
	}
	
	
}