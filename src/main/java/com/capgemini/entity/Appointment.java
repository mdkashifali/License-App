package com.capgemini.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Appointment {
	// LL - Online Test. DL - Driving Test
	// RTO officer has to set the test result because conducting test is out of scope

	@Id
	@GenericGenerator(name = "sequence_imei_id", strategy = "com.capgemini.util.CustomStringIdentifier")
	@GeneratedValue(generator = "sequence_imei_id")
	private String appointmentNumber;


	@Temporal(TemporalType.DATE)
	@NotNull
	private Date testDate;

	@NotNull
	private String timeSlot;

	private String testResult;

	@OneToOne
	@NotNull
	private RTOOfficer approver;

	public String getAppointmentNumber() {
		return appointmentNumber;
	}

	public void setAppointmentNumber(String appointmentNumber) {
		this.appointmentNumber = appointmentNumber;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public RTOOfficer getApprover() {
		return approver;
	}

	public void setApprover(RTOOfficer approver) {
		this.approver = approver;
	}
}
