package ar.com.insonet.dao;

import java.util.Set;

import ar.com.insonet.model.Role;

public interface RoleDAO {
	
	public void addRole(Role role);
	public void updateRole(Role role);
	public void deleteRole(int id);
	public Set<Role> getAllRoles();
	public Role getRoleById(int id);
	
}
