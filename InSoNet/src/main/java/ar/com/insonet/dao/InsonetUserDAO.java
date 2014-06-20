package ar.com.insonet.dao;

import java.util.List;

import ar.com.insonet.model.InsonetUser;

public interface InsonetUserDAO {
	
	public void addInsonetUser(InsonetUser insonetUser);
	public void updateInsonetUser(InsonetUser insonetUser);
	public InsonetUser getInsonetUser(int id);
	public InsonetUser getInsonetUserByUsername(String username);
	public void deleteInsonetUser(int id);
	public List<InsonetUser> getInsonetUsers();
	public void addSocialNetwork(InsonetUser insonetUser);
	public void delSocialNetwork(InsonetUser insonetUser, int idnet);

}
