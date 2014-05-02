package ar.com.insonet.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="user")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type_id",discriminatorType=DiscriminatorType.INTEGER)
@DiscriminatorValue(value="2")
public abstract class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	@Column(length = 10)
	@NotNull
	private String username;
	@NotNull
	private String password;
	@NotNull
	private String passwordRecovery;

	public User() {
	
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRecovery() {
		return passwordRecovery;
	}

	public void setPasswordRecovery(String passwordRecovery) {
		this.passwordRecovery = passwordRecovery;
	}

}
