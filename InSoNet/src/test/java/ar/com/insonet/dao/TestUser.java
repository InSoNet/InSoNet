package ar.com.insonet.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import org.junit.Test;
import org.junit.Before;

import ar.com.insonet.model.InsonetUser;
import ar.com.insonet.model.Role;

public class TestUser {
	
	@Before
	public void testSaveRoles() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		Transaction tx = session.beginTransaction();	
		Role setRoleUser = (Role) session.get(Role.class, new Integer(1));//1=user,2=moderator,3=admin
		Role setRoleMod = (Role) session.get(Role.class, new Integer(2));
		Role setRoleAdmin = (Role) session.get(Role.class, new Integer(3));
		
		if (setRoleUser == null) {
			setRoleUser = new Role();
			setRoleUser.setRole("user");
			session.save(setRoleUser);
		}
		
		if (setRoleMod == null ) {
			setRoleMod = new Role();
			setRoleMod.setRole("moderator");
			session.save(setRoleMod);
		}
		
		if (setRoleAdmin == null) {
			setRoleAdmin = new Role();
			setRoleAdmin.setRole("admin");
			session.save(setRoleAdmin);
		}
		
		tx.commit();
		//session.flush();
	}

	@Test
	public void testSaveInsonetUser() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		Transaction tx = session.beginTransaction();		
		String setUsername = "demo";
		String setPassword = "demo";
		String setName = "user";
		String setSurname = "developer";
		String setEmail = "user@insonet.com";
		Integer setCelular = 1558905890;
		Boolean isEnabled = true;
		InsonetUser user = new InsonetUser(setUsername,setPassword,setName,setSurname,setEmail,setCelular, isEnabled);
		Role setRole = (Role) session.get(Role.class, new Integer(1));//1=user,2=moderator,3=admin
		user.setRole(setRole);
		session.save(user);
		tx.commit();
	}
}