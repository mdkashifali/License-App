package com.capgemini.dto;

import com.capgemini.entity.DrivingLicense;

public class EmailDLRequest {

    private String email;
    private DrivingLicense drivingLicense;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DrivingLicense getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(DrivingLicense drivingLicense) {
        this.drivingLicense = drivingLicense;
    }
}
