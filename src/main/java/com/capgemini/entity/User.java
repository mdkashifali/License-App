package com.capgemini.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
public class User {

	@Id
	@NotBlank @Size(min = 5, max = 256)
	@Pattern(regexp=".+@.+\\.[a-z]+", message="invalid email address")
	private String email;

	@NotBlank(message = "password is mandatory")
	@Size(min = 8, max = 32)
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{" +
				"email='" + email + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
