package ar.com.insonet.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import ar.com.insonet.model.Roles;

@Repository
public class RolesDAOImpl implements RolesDAO {

	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void addRole(Roles role) {
		getCurrentSession().save(role);
	}

	@Override
	public void updateRole(Roles role) {
		Roles roleUpdate = getRoleById(role.getId());
		roleUpdate.setRole(role.getRole());
		getCurrentSession().update(roleUpdate);

	}

	@Override
	public void deleteRole(int id) {
		Roles role = getRoleById(id);
		if (role != null)
			getCurrentSession().delete(role);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Roles> getRoles() {
		List<Roles> list = getCurrentSession().createQuery("from roles").list();
		Set<Roles> roles = new HashSet<Roles>(list);
		return roles;
	}

	@Override
	public Roles getRoleById(int id) {
		Roles role = (Roles)getCurrentSession().get(Roles.class, id);
		return role;
	}

}
