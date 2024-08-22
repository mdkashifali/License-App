package com.capgemini.service;

import com.capgemini.dto.BasicResponse;
import com.capgemini.entity.*;
import com.capgemini.exception.LicenseAppException;

import java.util.List;

public interface UserService {

    BasicResponse userRegistration(User user, String otp) throws LicenseAppException;
    BasicResponse userLogin(User user) throws LicenseAppException;
    BasicResponse changePassword(User user, String newPassword) throws LicenseAppException;
    BasicResponse forgotPassword(User user);
    BasicResponse applyForLL(Application llApplication);
    BasicResponse applyForDL(Application dlApplication);
    BasicResponse uploadDocuments(String application, Documents documents) throws LicenseAppException;
    Challan checkChallanByVehicleNumber(String vehicleNumber) throws LicenseAppException;
    BasicResponse payChallanByVehicleNumber(String vehicleNumber);
    BasicResponse payFees(String applicationNo,int amount, String modeOfPayment) throws LicenseAppException;
    @SuppressWarnings("UnusedReturnValue")
    BasicResponse emailFeesReceipt(String email, int amount);
    BasicResponse bookSlotLLTest(Appointment appointment);
    BasicResponse bookSlotDLTest(Appointment appointment);
    List<Appointment> getAvailableSlots();
    BasicResponse renewLL(String drivingLicenseNumber) throws LicenseAppException;
    BasicResponse renewDL(String drivingLicenseNumber) throws LicenseAppException;
    BasicResponse cancelAppointment(Appointment appointment) throws LicenseAppException;

}
