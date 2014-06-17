package ar.com.insonet.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Repository;


/**
 * https://developers.facebook.com/docs/facebook-login/access-tokens
 * @author InSoNet
 *
 */
@Entity
@Table(name = "accesstoken")
public class AccessToken implements Serializable {
		
	private static final long serialVersionUID = -132808273190787764L;
	@Id
	@GeneratedValue
	private Integer id;
	@NotNull
	private String accessToken;
	@NotNull
	//@Temporal(javax.persistence.TemporalType.TIMESTAMP)
    //@Future
	private Long expire;
	private String loginStatus;
	private String machineId;
	
	public AccessToken() {
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * A long-lived access token that you can use for Graph API calls.
	 * @return String
	 */
	
	//@OneToOne(mappedBy="accessToken")
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	/**
	 * The number of seconds until this access token expires.
	 * @return Integer
	 */
	public Long getExpire() {
		return expire;
	}
	public void setExpire(Long expire) {
		this.expire = expire;
	}
	public String getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	/**
	 * The machine_id for this client. Please store this for future calls to generate a new access token from a code.
	 * This helps identify this client and is used to prevent spam.
	 * @return String
	 */
	public String getMachineId() {
		return machineId;
	}
	
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	
	
	
}