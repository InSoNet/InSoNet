package ar.com.insonet.model;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import ar.com.insonet.model.SocialNetworkType;

@Entity
@Table(name="socialnetwork")
public class SocialNetwork {
	@Id
	@GeneratedValue
	private Integer id;
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	private SocialNetworkType socialNetworkType;
	@NotNull
	private String usernameSocial;
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	private AccessToken accessToken;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public SocialNetworkType getSocialNetworkType() {
		return socialNetworkType;
	}
	
	public void setSocialNetworkType(SocialNetworkType typeId) {
		this.socialNetworkType = typeId;
	}
	public String getUsernameSocial() {
		return usernameSocial;
	}
	public void setUsernameSocial(String usernameSocial) {
		this.usernameSocial = usernameSocial;
	}
	public AccessToken getAccessToken() {
		return accessToken;
	}
	
	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}
	
	
}