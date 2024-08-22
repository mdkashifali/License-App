package com.capgemini.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ChangePasswordRequest {
	
	@NotBlank @Size(min = 5, max = 255)
	@Pattern(regexp=".+@.+\\.[a-z]+", message="Invalid email address!")
	String email;

	@NotBlank @Size(min = 8, max = 32)
	String password;

	@NotBlank @Size(min = 8, max = 32)
	String newPassword;

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

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "ChangePasswordRequest [email=" + email + ", password=" + password + ", newPassword=" + newPassword
				+ "]";
	}
	
	
}
