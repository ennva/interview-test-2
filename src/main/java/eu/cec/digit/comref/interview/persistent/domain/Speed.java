package eu.cec.digit.comref.interview.persistent.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SPEED")
@Getter
@Setter
public class Speed {

	@Id
	@Column(name = "isp_name")
	private String ispName;
	
	@Column(name = "speed")
	private Integer speed;

	public String getIspName() {
		return ispName;
	}

	public void setIspName(String ispName) {
		this.ispName = ispName;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	
	
}
