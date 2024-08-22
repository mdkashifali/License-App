package com.capgemini.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CheckChallanRequest {
	@NotBlank @Size(min = 10, max = 10)
	String vehicleNumber;
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

}
