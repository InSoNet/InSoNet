package ar.com.insonet.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.com.insonet.model.InsonetUser;
import ar.com.insonet.model.SocialNetwork;

public class SocialNetworkDAOImpl implements SocialNetworkDAO {

	private Session getCurrentSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	@Override
	public void addSocialNetwork(SocialNetwork socialNetwork) {
		getCurrentSession().save(socialNetwork);
		
	}

	@Override
	public void updateSocialNetwork(SocialNetwork socialNetwork) {
		SocialNetwork socialNetworkToUpdate = getSocialNetwork(socialNetwork.getId());
		socialNetworkToUpdate.setAccessToken(socialNetwork.getAccessToken());
		socialNetworkToUpdate.setUsernameSocial(socialNetwork.getUsernameSocial());
		socialNetworkToUpdate.setSocialNetworkType(socialNetwork.getSocialNetworkType());
		getCurrentSession().update(socialNetworkToUpdate);
		
	}

	@Override
	public SocialNetwork getSocialNetwork(int id) {
		Transaction tx = getCurrentSession().beginTransaction();
		SocialNetwork socialNetwork = (SocialNetwork) getCurrentSession().get(SocialNetwork.class, id);
		tx.commit();
		return socialNetwork;
	}

	@Override
	public void deleteSocialNetwork(int id) {
		SocialNetwork socialNetwork = getSocialNetwork(id);
		if(socialNetwork != null) {
			getCurrentSession().delete(socialNetwork);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SocialNetwork> getSocialNetworks() {
		return getCurrentSession().createQuery("from socialnetwork").list();
	}
	
	
}