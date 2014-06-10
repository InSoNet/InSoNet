package ar.com.insonet.dao;

import java.util.List;

import ar.com.insonet.model.SocialNetworkType;

public interface SocialNetworkTypeDAO {
	public void addSocialNetworkType(SocialNetworkType socialNetworkType);
	public void updateSocialNetworkType(SocialNetworkType socialNetworkType);
	public SocialNetworkType getSocialNetworkType(int id);
	public void deleteSocialNetworkType(int id);
	public List<SocialNetworkType> getSocialNetworkTypes();
}
