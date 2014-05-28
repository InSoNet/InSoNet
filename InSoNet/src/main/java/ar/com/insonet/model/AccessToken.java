package ar.com.insonet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.Validator;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
//import org.hibernate.validator.Future;
//import org.hibernate.validator.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "accesstoken")
public class AccessToken implements Serializable {
	@Autowired
	private Validator validator;
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer id;
	@NotNull
	private Long userId;
	@NotNull
	private Long socialNetworkId;
	@NotNull
	private String accessToken;
	@NotNull
	@Temporal(javax.persistence.TemporalType.DATE)
    @Future
	private Date expire;
	
	public AccessToken() {
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getSocialNetworkId() {
		return socialNetworkId;
	}
	public void setSocialNetworkId(Long socialNetworkId) {
		this.socialNetworkId = socialNetworkId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Date getExpire() {
		return expire;
	}
	public void setExpire(Date expire) {
		this.expire = expire;
	}
	
	
}