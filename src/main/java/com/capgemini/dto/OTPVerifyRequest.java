package com.capgemini.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class OTPVerifyRequest {
	@NotBlank @Size(min = 5, max = 255)
	@Pattern(regexp=".+@.+\\.[a-z]+", message="Invalid email address!")
	String email;

	@NotBlank @Size(min = 8, max = 32)
	String password;
	
	@NotBlank @Size(min = 4, max = 4)
	String otp;

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

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return "OTPVerifyRequest [email=" + email + ", password=" + password + ", otp=" + otp + "]";
	}


}
