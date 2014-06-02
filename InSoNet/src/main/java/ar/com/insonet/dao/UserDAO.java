package ar.com.insonet.dao;

import java.util.List;

import ar.com.insonet.model.User;

public interface UserDAO {
	
	public void addUser(User user);
	public void updateUser(User user);
	public User getUser(int id);
	public User getUserByUsername(String username);
	public void deleteUser(int id);
	public List<User> getUsers();

}
