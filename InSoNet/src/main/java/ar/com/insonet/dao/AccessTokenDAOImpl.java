package ar.com.insonet.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ar.com.insonet.model.AccessToken;

public class AccessTokenDAOImpl implements AccessTokenDAO, Serializable {

	private static final long serialVersionUID = 1L;
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void addAccessToken(AccessToken accessToken) {
		getCurrentSession().save(accessToken);
		
	}

	@Override
	public void updateAccessToken(AccessToken accessToken) {
		AccessToken accessTokenToUpdate = getAccessToken(accessToken.getId());
		accessTokenToUpdate.setAccessToken(accessToken.getAccessToken());
		accessTokenToUpdate.setExpire(accessToken.getExpire());
		getCurrentSession().update(accessTokenToUpdate);
		
	}

	@Override
	public AccessToken getAccessToken(int id) {
		AccessToken accessToken = (AccessToken)getCurrentSession().get(AccessToken.class, id);
		return accessToken;
	}

	@Override
	public void deleteAccessToken(int id) {
		AccessToken accessToken = getAccessToken(id);
		if ( accessToken != null)
		{
			getCurrentSession().delete(accessToken);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccessToken> getAllAccessTokens() {
		return getCurrentSession().createQuery("from access_token").list();
	}

	/*@Override
	public AccessToken getAccessTokenBy(String user) {
		getCurrentSession().createQuery("from socialnetwork").list();
		return null;
	}*/
	
}