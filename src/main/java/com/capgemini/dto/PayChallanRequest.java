package com.capgemini.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PayChallanRequest {
	@NotBlank
	String challanNumber;
	@NotBlank @Size(min = 10, max = 10)
	String vehicleNumber;
	@NotBlank
	Double amount;
	@Override
	public String toString() {
		return "PayChallanRequest [challanNumber=" + challanNumber + ", vehicleNumber=" + vehicleNumber + ", amount="
				+ amount + "]";
	}
	public String getChallanNumber() {
		return challanNumber;
	}
	public void setChallanNumber(String challanNumber) {
		this.challanNumber = challanNumber;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	

}
