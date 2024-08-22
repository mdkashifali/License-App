package com.capgemini.controller;

import com.capgemini.dto.*;
import com.capgemini.entity.*;
import com.capgemini.exception.LicenseAppException;
import com.capgemini.service.UserService;
import com.capgemini.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

/**
 * UserController
 *
 * contains all the endpoints for user related services
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private UserUtils userUtils;

    @PostMapping("/login")
    public ResponseEntity<BasicResponse> login(@RequestBody @Valid LoginUserRequest request) throws LicenseAppException {
        return new ResponseEntity<>(userService.userLogin(userUtils.toUser(request)), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<BasicResponse> register(@RequestBody @Valid RegisterUserRequest request) throws LicenseAppException {
        return new ResponseEntity<>(userService.userRegistration(userUtils.toUser(request),""), HttpStatus.OK);
    }

    @PutMapping("/register")
    public ResponseEntity<BasicResponse> verify(@RequestBody @Valid OTPVerifyRequest request) throws LicenseAppException {
        return new ResponseEntity<>(userService.userRegistration(userUtils.toUser(request),
                request.getOtp()), HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<BasicResponse> changePassword(@RequestBody @Valid ChangePasswordRequest request) throws LicenseAppException {
        return new ResponseEntity<>(userService.changePassword(userUtils.toUser(request),
                request.getNewPassword()), HttpStatus.OK);
    }

    @DeleteMapping("/password")
    public ResponseEntity<BasicResponse> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        return new ResponseEntity<>(userService.forgotPassword(userUtils.toUser(request)), HttpStatus.OK);
    }


    @PostMapping("/apply/dl")
    public BasicResponse applyForDL(@RequestBody @Valid ApplyForLicenseRequest request) throws ParseException {
        Application application = userUtils.toApplication(request,ApplicationType.DL);
        return userService.applyForDL(application);
    }

    @PostMapping("/apply/ll")
    public BasicResponse applyForLL(@RequestBody @Valid ApplyForLicenseRequest request) throws ParseException {
        Application application = userUtils.toApplication(request, ApplicationType.LL);
        return userService.applyForLL(application);
    }

    @PostMapping("/apply/docs")
    public BasicResponse uploadDocuments(@RequestBody @Valid UploadDocumentsRequest request) throws LicenseAppException {
        Documents documents = userUtils.toDocuments(request);
        return userService.uploadDocuments(request.getApplicationNumber(),documents);
    }

    @PostMapping("/apply/fees")
    public BasicResponse payFees(@RequestBody @Valid PayFeesRequest request) throws LicenseAppException {
        return userService.payFees(request.getApplicationNumber(),
                request.getAmountPaid(),request.getModeOfPayment());
    }

    @GetMapping("/challan")
    public Challan checkChallan(@RequestBody @Valid CheckChallanRequest request) throws LicenseAppException {
        return userService.checkChallanByVehicleNumber(request.getVehicleNumber());
    }

    @PostMapping("/challan")
    public BasicResponse payChallan(@RequestBody @Valid PayChallanRequest request) {
        return userService.payChallanByVehicleNumber(request.getVehicleNumber());
    }

    @GetMapping("/slots")
    public List<Appointment> getAvailableSlots() {
        return userService.getAvailableSlots();
    }

    @PostMapping("/slots/dl")
    public BasicResponse bookDLSlot(@RequestBody @Valid BookSlotRequest request) throws ParseException {
        Appointment appointment = userUtils.toAppointment(request);
        return userService.bookSlotDLTest(appointment);
    }

    @PostMapping("/slots/ll")
    public BasicResponse bookLLSlot(@RequestBody @Valid BookSlotRequest request) throws ParseException {
        Appointment appointment = userUtils.toAppointment(request);
        return userService.bookSlotLLTest(appointment);
    }

    @PutMapping("/renew/dl")
    public BasicResponse renewDL(@RequestBody @Valid RenewLicenseRequest request) throws LicenseAppException {
        return userService.renewDL(request.getDrivingLicenseNumber());
    }

    @PutMapping("/renew/ll")
    public BasicResponse renewLL(@RequestBody @Valid RenewLicenseRequest request) throws LicenseAppException {
        return userService.renewLL(request.getDrivingLicenseNumber());
    }

    @DeleteMapping("/apply/cancel")
    public BasicResponse cancelAppointment(@RequestBody @Valid BookSlotRequest request) throws ParseException, LicenseAppException {
        Appointment appointment = userUtils.toAppointment(request);
        return userService.cancelAppointment(appointment);
    }

}
