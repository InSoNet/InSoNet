package ar.com.insonet.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import ar.com.insonet.model.Friend;

@Repository
public class FriendDAOImpl implements FriendDAO {
	
	private Session getCurrentSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	public void addFriend(Friend friend) {
		Transaction tx = getCurrentSession().beginTransaction();
		getCurrentSession().save(friend);
		tx.commit();
	}
	
	public void updateFriend(Friend friend) {
		Friend friendToUpdate = getFriend(friend.getId());
		friendToUpdate.setUserId(friend.getUserId());
		if(getCurrentSession().isConnected())
			friendToUpdate.setSocialNetwork(friend.getSocialNetwork());
		getCurrentSession().update(friendToUpdate);
	}
	public Friend getFriend(int id) {
		Friend friend = (Friend) getCurrentSession().get(Friend.class, id);
		return friend;
	}
	public void deleteFriend(int id) {
		Friend friend = getFriend(id);
		if (friend != null)
			getCurrentSession().delete(friend);
	}
	
	@SuppressWarnings("unchecked")
	public List<Friend> getFriends() {
		return getCurrentSession().createQuery("from Friend").list();
	}

}