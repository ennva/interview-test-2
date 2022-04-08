package eu.cec.digit.comref.interview.persistent.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

	@OneToOne
	@JoinColumn(name = "ISP_NAME", referencedColumnName = "NAME")
	private InternetServiceProvider internetServiceProvider;

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

	public InternetServiceProvider getInternetServiceProvider() {
		return internetServiceProvider;
	}

	public void setInternetServiceProvider(InternetServiceProvider internetServiceProvider) {
		this.internetServiceProvider = internetServiceProvider;
	}

}
