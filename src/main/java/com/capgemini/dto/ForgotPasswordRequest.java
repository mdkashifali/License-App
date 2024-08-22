package com.capgemini.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ForgotPasswordRequest {
	
	@NotBlank @Size(min = 5, max = 255)
	@Pattern(regexp=".+@.+\\.[a-z]+", message="Invalid email address!")
	String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ForgotPasswordRequest [email=" + email + "]";
	}

}
