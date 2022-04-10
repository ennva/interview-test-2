package eu.cec.digit.comref.interview.persistent.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "technician")
@Getter
@Setter
public class Technician {
	@Id
	@Column(name = "name")
	private String name;
	
	@Column(name = "skill")
	private String skill;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

}
