package ar.com.insonet.dao;

import java.util.List;

import ar.com.insonet.model.AccessToken;

public interface AccessTokenDAO {
	
	public void addAccessToken(AccessToken accessToken);
	public void updateAccessToken(AccessToken accessToken);
	public AccessToken getAccessToken(int id);
	//public AccessToken getAccessTokenByUser(String user);
	public void deleteAccessToken(int id);
	public List<AccessToken> getAllAccessTokens();

}