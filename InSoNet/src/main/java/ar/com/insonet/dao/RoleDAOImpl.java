package ar.com.insonet.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import ar.com.insonet.model.Role;

@Repository
public class RoleDAOImpl implements RoleDAO {

	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void addRole(Role role) {
		getCurrentSession().save(role);
	}

	@Override
	public void updateRole(Role role) {
		Role roleUpdate = getRoleById(role.getId());
		roleUpdate.setRole(role.getRole());
		getCurrentSession().update(roleUpdate);

	}

	@Override
	public void deleteRole(int id) {
		Role role = getRoleById(id);
		if (role != null)
			getCurrentSession().delete(role);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Role> getAllRoles() {
		List<Role> list = getCurrentSession().createQuery("from role").list();
		Set<Role> roles = new HashSet<Role>(list);
		return roles;
	}

	@Override
	public Role getRoleById(int id) {
		Role role = (Role)getCurrentSession().load(Role.class, id);
		return role;
	}

}
