package com.capgemini.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class RTOOfficer {

	@Size(min = 3, max = 32)
	private String username;

	@NotBlank(message = "password is mandatory")
	@Size(min = 8, max = 32)
	private String password;

	@Id
	@NotBlank @Size(min = 5, max = 256)
	@Pattern(regexp=".+@.+\\.[a-z]+", message="invalid email address")
	private String email;

	@OneToOne
	private RTOOffice office;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RTOOffice getOffice() {
		return office;
	}

	public void setOffice(RTOOffice office) {
		this.office = office;
	}
}
