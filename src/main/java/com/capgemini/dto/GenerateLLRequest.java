package com.capgemini.dto;

public class GenerateLLRequest {
    private String applicationNumber;
    private String rtoEmail;

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getRtoEmail() {
        return rtoEmail;
    }

    public void setRtoEmail(String rtoEmail) {
        this.rtoEmail = rtoEmail;
    }
}
