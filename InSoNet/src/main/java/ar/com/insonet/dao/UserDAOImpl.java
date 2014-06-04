package ar.com.insonet.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import ar.com.insonet.model.User;

@Repository
public class UserDAOImpl implements UserDAO {

	private Session getCurrentSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public void addUser(User user) {
		
		getCurrentSession().save(user);
		
	}

	public void updateUser(User user) {
		User userToUpdate = getUser(user.getId());
		userToUpdate.setUsername(user.getUsername());
		userToUpdate.setPassword(user.getPassword());
		getCurrentSession().update(userToUpdate);
	}

	public User getUser(int id) {
		User user = (User) getCurrentSession().get(User.class, id);
		return user;
	}

	public void deleteUser(int id) {
		User user = getUser(id);
		if (user != null)
			getCurrentSession().delete(user);
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		return getCurrentSession().createQuery("from user").list();
	}

	public User getUserByUsername(String username) {
		//User user = null;
		//List<User> userList = new ArrayList<User>();
		
		Transaction tx =  getCurrentSession().beginTransaction();
        
		User user = (User) getCurrentSession().createQuery("from User u where u.username = :username").setParameter("username", username).uniqueResult();
        
		tx.commit();
		
        //if (userList.size() > 0)
        //    user = userList.get(0);
        
        return user;
	}
	
}