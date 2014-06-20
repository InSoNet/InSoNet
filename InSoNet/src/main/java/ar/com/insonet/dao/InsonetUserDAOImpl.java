package ar.com.insonet.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import ar.com.insonet.model.InsonetUser;
import ar.com.insonet.model.SocialNetwork;
import ar.com.insonet.model.User;

@Repository
public class InsonetUserDAOImpl implements InsonetUserDAO, Serializable {

	private static final long serialVersionUID = 1L;

	private Session getCurrentSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public void addInsonetUser(InsonetUser insonetUser) {
		Transaction tx = getCurrentSession().beginTransaction();
		getCurrentSession().save(insonetUser);
		tx.commit();

	}

	public void updateInsonetUser(InsonetUser insonetUser) {
		
		InsonetUser insonetUserToUpdate = getInsonetUser(insonetUser.getId());
		//insonetUserToUpdate.setUsername(insonetUser.getUsername());
		insonetUserToUpdate.setPassword(insonetUser.getPassword());
		insonetUserToUpdate.setEmail(insonetUser.getEmail());
		insonetUserToUpdate.setName(insonetUser.getName());
		insonetUserToUpdate.setRole(insonetUser.getRole());
		insonetUserToUpdate.setSurname(insonetUser.getSurname());
		insonetUserToUpdate.setEnabled(insonetUser.isEnabled());
		Transaction tx = getCurrentSession().beginTransaction();
		if (getCurrentSession().isConnected())
			insonetUserToUpdate.setSocialNetwork(insonetUser.getSocialNetwork());
		
		getCurrentSession().update(insonetUserToUpdate);
		tx.commit();
	}

	public void addSocialNetwork(InsonetUser insonetUser) {
		InsonetUser insonetUserToUpdate = getInsonetUser(insonetUser.getId());
		Transaction tx = getCurrentSession().beginTransaction();
		if (getCurrentSession().isConnected())
			insonetUserToUpdate.setSocialNetwork(insonetUser.getSocialNetwork());
		getCurrentSession().flush();
		getCurrentSession().update(insonetUserToUpdate);
		
		tx.commit();
	}
	public void delSocialNetwork(InsonetUser insonetUser, int idnet) {
		InsonetUser insonetUserToUpdate = getInsonetUser(insonetUser.getId());
		List<SocialNetwork> aux = new ArrayList<SocialNetwork>();
		List<SocialNetwork> list = insonetUser.getSocialNetwork();
		for(SocialNetwork sn : list) {
			if(sn.getId() != idnet) {
				aux.add(sn);
			}
		}
		
		Transaction tx = getCurrentSession().beginTransaction();
		if (getCurrentSession().isConnected())
			insonetUserToUpdate.setSocialNetwork(aux);
		getCurrentSession().flush();
		getCurrentSession().update(insonetUserToUpdate);
		
		tx.commit();
	}
	public InsonetUser getInsonetUser(int id) {
		Transaction tx =  getCurrentSession().beginTransaction();
		InsonetUser insonetUser = (InsonetUser) getCurrentSession().get(
				InsonetUser.class, id);
		tx.commit();
		return insonetUser;
	}
	
	public InsonetUser getInsonetUserByUsername(String username) {
		Transaction tx =  getCurrentSession().beginTransaction();
		InsonetUser user = (InsonetUser) getCurrentSession().createQuery("from User u where u.username = :username").setParameter("username", username).uniqueResult();
		tx.commit();
		return user;
	}

	public void deleteInsonetUser(int id) {
		InsonetUser insonetUser = getInsonetUser(id);
		if (insonetUser != null)
			getCurrentSession().delete(insonetUser);
	}

	@SuppressWarnings("unchecked")
	public List<InsonetUser> getInsonetUsers() {
		return getCurrentSession().createQuery("from User").list();
	}

}
