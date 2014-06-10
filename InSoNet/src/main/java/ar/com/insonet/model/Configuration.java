package ar.com.insonet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="configuration")
public class Configuration {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long Id;
	//@NotNull
	//private int userInsonetId;
	@NotNull
	private String parameter;
	@NotNull
	private String value;
	
	public Configuration() {
		
	}
	public String getParameter() {
		return this.parameter;
	}
	
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	/*public int getUserInsonetId() {
		return userInsonetId;
	}

	public void setUserInsonetId(int userInsonetId) {
		this.userInsonetId = userInsonetId;
	}*/

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}