package ar.com.insonet.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "friend")
public class Friend implements Serializable {
	
	private static final long serialVersionUID = -3636109191517746707L;
	
	@Id
	@GeneratedValue
	private int id;
	@NotNull
	private String userId;
	@NotNull
	@OneToOne
	private SocialNetwork socialNetwork;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public SocialNetwork getSocialNetwork() {
		return socialNetwork;
	}
	public void setSocialNetwork(SocialNetwork socialNetwork) {
		this.socialNetwork = socialNetwork;
	}
	
	
}