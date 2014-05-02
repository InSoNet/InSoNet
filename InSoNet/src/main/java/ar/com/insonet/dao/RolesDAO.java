package ar.com.insonet.dao;

import java.util.Set;

import ar.com.insonet.model.Roles;

public interface RolesDAO {
	
	public void addRole(Roles role);
	public void updateRole(Roles role);
	public void deleteRole(int id);
	public Set<Roles> getRoles();
	public Roles getRoleById(int id);
	
}
