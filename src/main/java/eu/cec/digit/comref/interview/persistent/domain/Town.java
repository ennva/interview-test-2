package eu.cec.digit.comref.interview.persistent.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TOWN")
public class Town implements Serializable {

	private static final long serialVersionUID = 6085577047571202444L;

	@Id
	@Column(name = "NAME")
	private String name;

	@Column(name = "INHABITANTS")
	private Integer inhabitants;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "town_isp")
	private Set<InternetServiceProvider> internetServiceProviders = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getInhabitants() {
		return inhabitants;
	}

	public void setInhabitants(Integer inhabitants) {
		this.inhabitants = inhabitants;
	}

	public Set<InternetServiceProvider> getInternetServiceProviders() {
		return internetServiceProviders;
	}

	public void setInternetServiceProviders(Set<InternetServiceProvider> internetServiceProviders) {
		this.internetServiceProviders = internetServiceProviders;
	}

}
