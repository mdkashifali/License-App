package com.capgemini.dto;

import javax.validation.constraints.NotBlank;

public class UploadDocumentsRequest {
	@NotBlank
	String photo;
	@NotBlank
	String idProof;
	@NotBlank
	String addressProof;
	@NotBlank
	String applicationNumber;
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getIdProof() {
		return idProof;
	}
	public void setIdProof(String idProof) {
		this.idProof = idProof;
	}
	public String getAddressProof() {
		return addressProof;
	}
	public void setAddressProof(String addressProof) {
		this.addressProof = addressProof;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
}
