package com.capgemini.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * List of RTO offices in Maharashtra
 */

@Entity
public class RTOOffice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int rtoId;

	@NotBlank(message = "rto office name is mandatory")
	@Size(min = 3, max = 32)
	private String rtoName;

	public int getRtoId() {
		return rtoId;
	}

	public void setRtoId(int rtoId) {
		this.rtoId = rtoId;
	}

	public String getRtoName() {
		return rtoName;
	}

	public void setRtoName(String rtoName) {
		this.rtoName = rtoName;
	}
}
