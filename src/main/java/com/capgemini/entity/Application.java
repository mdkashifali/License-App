package com.capgemini.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Application {

	@Id
	@GenericGenerator(name = "sequence_imei_id", strategy = "com.capgemini.util.CustomStringIdentifier")
	@GeneratedValue(generator = "sequence_imei_id")
	private String applicationNumber;

	@NotNull
	@OneToOne
	private Applicant applicant;

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date applicationDate;

	@OneToOne
	private RTOOffice rtoOffice;

	@Enumerated(EnumType.STRING)
	@NotNull
	private ApplicationType type;

	@OneToOne
	private Documents documents;
	
	// need to pay the fees
	private String modeOfPayment;
	private double amountPaid;
	private String paymentStatus;
	
	// Test timing and result
	@OneToOne
	private Appointment appointment;
	
	// status will be updated by RTO officer. Initially status will be PENDING
	@Enumerated(EnumType.STRING)
	@NotNull
	private ApplicationStatus status;

	private String remarks;

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public RTOOffice getRtoOffice() {
		return rtoOffice;
	}

	public void setRtoOffice(RTOOffice rtoOffice) {
		this.rtoOffice = rtoOffice;
	}

	public ApplicationType getType() {
		return type;
	}

	public void setType(ApplicationType type) {
		this.type = type;
	}

	public Documents getDocuments() {
		return documents;
	}

	public void setDocuments(Documents documents) {
		this.documents = documents;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
