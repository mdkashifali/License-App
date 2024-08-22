package com.capgemini.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterUserRequest {
	
	@NotBlank @Size(min = 5, max = 255)
	@Pattern(regexp=".+@.+\\.[a-z]+", message="Invalid email address!")
	String email;

	@NotBlank @Size(min = 8, max = 32)
	String password;

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
		return "RegisterUserRequest [email=" + email + ", password=" + password + "]";
	}

}
