package eu.cec.digit.comref.interview.persistent.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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

	@Column(name = "SPEED")
	private Integer speed;

	@Column(name = "AVAILABLE")
	private Boolean available;
	
//	@OneToOne(mappedBy = "internetServiceProvider")
//	private Town town;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

}
