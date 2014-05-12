package ar.com.insonet.model;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "insonetUser")
@DiscriminatorValue("2")
public class InsonetUser extends User {

	private static final long serialVersionUID = 1L;
	@NotNull
	private String name;
	@NotNull
	private String surname;
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
	@ManyToMany
	private Set<Roles> roles = new HashSet<Roles>();

	public InsonetUser() {
		super();
	}

	public InsonetUser(String username, String password, String name,
			String surname, String email, Integer celular) {
		super(username, password);
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

	public Set<Roles> getRoles() {
		return this.roles;
	}

	//@OneToMany(cascade = CascadeType.ALL)
	@ManyToMany
	public void setRoles(Roles role) {
		this.roles.add(role);
	}

}
