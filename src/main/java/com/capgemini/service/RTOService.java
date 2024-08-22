package com.capgemini.service;

import com.capgemini.dto.BasicResponse;
import com.capgemini.entity.*;
import com.capgemini.exception.LicenseAppException;

import java.util.List;

public interface RTOService {
    BasicResponse login(RTOOfficer officer) throws LicenseAppException;
    List<Application> getAllPendingApplications();
    List<Application> getAllRejectedApplications();
    List<Application> getAllApprovedApplications();
    Application viewApplicationById(String applicationNumber);
    List<Challan> checkChallanByVehicleNumber(String vehicleNumber);
    List<Challan> checkAllChallan();
    Application modifyTestResultById(String applicationNumber, ApplicationStatus status) throws LicenseAppException;
    DrivingLicense generateLearnerLicense(String applicationNumber,String rtoEmail) throws LicenseAppException;
    DrivingLicense generateDrivingLicense(String applicationNumber,String rtoEmail) throws LicenseAppException;
    BasicResponse emailLicense(String email,DrivingLicense license);
}