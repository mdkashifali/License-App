package com.capgemini.dto;

import javax.validation.constraints.NotBlank;

public class RenewLicenseRequest {
	public String getDrivingLicenseNumber() {
		return drivingLicenseNumber;
	}

	public void setDrivingLicenseNumber(String drivingLicenseNumber) {
		this.drivingLicenseNumber = drivingLicenseNumber;
	}

	@NotBlank
	String drivingLicenseNumber;
}
