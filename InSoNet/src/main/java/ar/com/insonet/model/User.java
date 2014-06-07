package ar.com.insonet.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


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
	@NotEmpty
	@NotNull
	@Column(length = 20, unique=true)
	private String username;
	@NotNull
	@NotEmpty
	private String password;
	private String passwordRecovery;
	@NotNull
	private boolean enabled;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinTable(name="user_role",
		joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
		inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
	)
	private Role role;
	
	public User() {
	
	}

	public User(String username, String password, Boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
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

	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
