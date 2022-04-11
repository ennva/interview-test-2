package eu.cec.digit.comref.interview.persistent.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "technicians")
	private List<Town> towns = new ArrayList<>();

}
