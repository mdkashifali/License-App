package com.capgemini.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class BookSlotRequest {
	@NotBlank
	@Pattern(regexp="^[0-9]{4}/(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])$", message="Invalid date!")
	String testDate;
	String timeSlot;


	public String getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}


	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
}
