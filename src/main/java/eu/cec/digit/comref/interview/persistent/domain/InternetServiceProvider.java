package eu.cec.digit.comref.interview.persistent.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ISP")
public class InternetServiceProvider implements Serializable {

	private static final long serialVersionUID = -4292369331387884243L;

	@Id
	@Column(name = "NAME")
	private String name;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_isp_speeds")
	private Set<Speed> speeds;

	@Column(name = "AVAILABLE")
	private Boolean available;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Speed> getSpeeds() {
		return speeds;
	}

	public void setSpeeds(Set<Speed> speeds) {
		this.speeds = speeds;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
	
}
