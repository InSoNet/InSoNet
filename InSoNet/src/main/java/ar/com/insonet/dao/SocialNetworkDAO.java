package ar.com.insonet.dao;

import java.util.List;

import ar.com.insonet.model.SocialNetwork;

public interface SocialNetworkDAO {
	
	public void addSocialNetwork(SocialNetwork socialNetwork);
	public void updateSocialNetwork(SocialNetwork socialNetwork);
	public SocialNetwork getSocialNetwork(int id);
	public void deleteSocialNetwork(int id);
	public List<SocialNetwork> getSocialNetworks();

}