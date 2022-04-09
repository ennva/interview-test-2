package eu.cec.digit.comref.interview.persistent.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class Speed1 {

	//private static final long serialVersionUID = 8286222035705958418L;
	
	public Speed1() {
		// TODO Auto-generated constructor stub
	}
	
	public Speed1(String ispName, Integer ispSpeed) {
		this.ispName = ispName;
		speed = ispSpeed;
	}

	private String ispName;

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
