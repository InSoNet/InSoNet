package ar.com.insonet.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="socialnetworktype")
public class SocialNetworkType implements Serializable{
	
	private static final long serialVersionUID = 2275336671757691555L;
	@Id
	@GeneratedValue
	private int Id;
	@NotNull
	@Column(name="socialNetworkType")
	private String socialNetworkType;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	//@OneToOne(mappedBy="socialNetworkType")
	public String getSocialNetworkType() {
		return socialNetworkType;
	}
	public void setSocialNetworkType(String socialNetworkType) {
		this.socialNetworkType = socialNetworkType;
	}
	
	
}