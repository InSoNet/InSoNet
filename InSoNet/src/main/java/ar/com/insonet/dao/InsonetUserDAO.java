package ar.com.insonet.dao;

import java.util.List;

import ar.com.insonet.model.InsonetUser;

public interface InsonetUserDAO {
	
	public void addInsonetUser(InsonetUser insonetUser);
	public void updateInsonetUser(InsonetUser insonetUser);
	public InsonetUser getInsonetUser(int id);
	public void deleteInsonetUser(int id);
	public List<InsonetUser> getInsonetUsers();

}
