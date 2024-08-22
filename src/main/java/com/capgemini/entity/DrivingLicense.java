package com.capgemini.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
public class DrivingLicense {

	@Id
	@Pattern(regexp = "^(([A-Z]{2}[0-9]{2})( )|([A-Z]{2}-[0-9]{2}))((19|20)[0-9][0-9])[0-9]{7}$", message = "invalid format")
	private String drivingLicenseNumber;

	@OneToOne
	@NotNull
	private Application application;

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date dateOfIssue;

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date validTill;

	@OneToOne
	@NotNull
	private RTOOffice issuedBy;

	public String getDrivingLicenseNumber() {
		return drivingLicenseNumber;
	}

	public void setDrivingLicenseNumber(String drivingLicenseNumber) {
		this.drivingLicenseNumber = drivingLicenseNumber;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public Date getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public Date getValidTill() {
		return validTill;
	}

	public void setValidTill(Date validTill) {
		this.validTill = validTill;
	}

	public RTOOffice getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(RTOOffice issuedBy) {
		this.issuedBy = issuedBy;
	}

	@Override
	public String toString() {
		return "DrivingLicense{" +
				"drivingLicenseNumber='" + drivingLicenseNumber + '\'' +
				", application=" + application +
				", dateOfIssue=" + dateOfIssue +
				", validTill=" + validTill +
				", issuedBy=" + issuedBy +
				'}';
	}
}
