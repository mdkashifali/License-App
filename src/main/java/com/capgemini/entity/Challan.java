package com.capgemini.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Entity
public class Challan {

	@Id
	@GenericGenerator(name = "sequence_imei_id", strategy = "com.capgemini.util.CustomStringIdentifier")
	@GeneratedValue(generator = "sequence_imei_id")
	private String challanNumber;

	@NotNull
	private String vehicleNumber;

	@Min(0)
	@Max(2000)
	private double amount;

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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
