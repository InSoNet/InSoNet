package ar.com.insonet.model;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

@Entity
@DiscriminatorValue("2")
public class InsonetUser extends User {

	private static final long serialVersionUID = 1L;
	@NotNull
	private String name;
	@NotNull
	private String surname;
	@NotNull
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
			+ "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
			+ "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "{invalid.email}")
	private String email;
	private Integer celular;

	// @OneToMany(mappedBy="sociable")
	@OneToMany(targetEntity = SociableImpl.class)
	private Collection<AbstractSociable> sociable = new ArrayList<AbstractSociable>();
	@OneToMany(targetEntity = Configuration.class)
	private List<Configuration> personalConfiguration = new ArrayList<Configuration>();
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinTable(name="user_role",
		joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
		inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
	)
	private Role role;

	public InsonetUser() {
		super();
	}

	public InsonetUser(String username, String password, String name,
			String surname, String email, Integer celular, Boolean enabled) {
		super(username, password, enabled);
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.celular = celular;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getCelular() {
		return celular;
	}

	public void setCelular(Integer celular) {
		this.celular = celular;
	}

	public List<AbstractSociable> getSociable() {
		List<AbstractSociable> list = new ArrayList<>(this.sociable);
		return list;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public void setSociable(Collection<AbstractSociable> sociable) {
		this.sociable = sociable;
	}

	public List<Configuration> getPersonalConfiguration() {
		return this.personalConfiguration;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public void setPersonalConfiguration(Configuration configuration) {
		this.personalConfiguration.add(configuration);
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
