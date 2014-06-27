package ar.com.insonet.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import ar.com.insonet.model.SocialNetworkType;
import ar.com.insonet.model.AccessToken;

@Entity
@Table(name="socialnetwork")
public class SocialNetwork implements Serializable {
	
	private static final long serialVersionUID = -30398628290038867L;
	@Id
	@GeneratedValue
	private Integer id;
	@NotNull
	@OneToOne
	private SocialNetworkType socialNetworkType;
	@NotNull
	private String usernameSocial;
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	private AccessToken accessToken;
	private boolean visible;
	
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
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
	//@OneToOne(cascade=CascadeType.ALL, mappedBy="socialNetwork")
	public AccessToken getAccessToken() {
		return accessToken;
	}
	
	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}
	
	
}