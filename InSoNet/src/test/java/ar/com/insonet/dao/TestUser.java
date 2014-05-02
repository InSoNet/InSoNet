package ar.com.insonet.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import org.junit.Test;
import org.junit.Before;

import ar.com.insonet.model.InsonetUser;
import ar.com.insonet.model.Roles;

public class TestUser {
	
	@Before
	public void testSaveRoles() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		Transaction tx = session.beginTransaction();	
		Roles setRoleUser = (Roles) session.get(Roles.class, new Integer(1));//1=user,2=moderator,3=admin
		Roles setRoleMod = (Roles) session.get(Roles.class, new Integer(2));
		Roles setRoleAdmin = (Roles) session.get(Roles.class, new Integer(3));
		
		if (setRoleUser == null) {
			setRoleUser = new Roles();
			setRoleUser.setRole("user");
			session.save(setRoleUser);
		}
		
		if (setRoleMod == null ) {
			setRoleMod = new Roles();
			setRoleMod.setRole("moderator");
			session.save(setRoleMod);
		}
		
		if (setRoleAdmin == null) {
			setRoleAdmin = new Roles();
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
		String setUsername = "rodo";
		String setPassword = "";
		String setName = "rodolfo";
		String setSurname = "inturias";
		String setEmail = "inturias_r@yahoo.com.ar";
		Integer setCelular = 1558905890;
		InsonetUser user = new InsonetUser(setUsername,setPassword,setName,setSurname,setEmail,setCelular);
		Roles setRole = (Roles) session.get(Roles.class, new Integer(1));//1=user,2=moderator,3=admin
		user.setRoles(setRole);
		session.save(user);
		tx.commit();
	}
}