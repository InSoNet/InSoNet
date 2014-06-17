package ar.com.insonet.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.com.insonet.model.SocialNetworkType;

public class SocialNetworkTypeDAOImpl implements SocialNetworkTypeDAO, Serializable {

	private static final long serialVersionUID = 1L;

	private Session getCurrentSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	@Override
	public void addSocialNetworkType(SocialNetworkType socialNetworkType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSocialNetworkType(SocialNetworkType socialNetworkType) {
		// TODO Auto-generated method stub

	}

	@Override
	public SocialNetworkType getSocialNetworkType(int id) {
		Transaction tx = getCurrentSession().beginTransaction();
		SocialNetworkType socialNetworkType = (SocialNetworkType) getCurrentSession().get(SocialNetworkType.class, id);
		tx.commit();
		return socialNetworkType;
	}

	@Override
	public void deleteSocialNetworkType(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<SocialNetworkType> getSocialNetworkTypes() {
		// TODO Auto-generated method stub
		return null;
	}

}
