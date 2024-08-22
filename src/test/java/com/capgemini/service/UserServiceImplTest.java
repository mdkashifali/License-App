package com.capgemini.service;

import com.capgemini.dto.BasicResponse;
import com.capgemini.entity.*;
import com.capgemini.exception.LicenseAppException;
import com.capgemini.repo.*;
import com.capgemini.util.CommonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@SuppressWarnings("unused")
@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {


    @Mock
    UserRepository userRepository;

    @Mock
    EmailService emailService;

    @Mock
    CommonUtils commonUtils;

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    ApplicationsRepository applicationsRepository;

    @Mock
    ChallanRepository challanRepository;

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    DrivingLicenseRepository drivingLicenseRepository;

    @Mock
    ApplicantsRepository applicantsRepository;

    @Mock
    DocumentsRepository documentsRepository;

    @Mock
    AddressRepository addressRepository;

    private static final String EMAIL = "mayankkhattar007@gmail.com";
    private static final String PASSWORD = "Mayank@007";
    private static final String RANDOM_10 = "1234567890";

    User user;
    Application application;
    Challan challan;
    Appointment appointment;
    DrivingLicense license;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        application = new Application();
        application.setApplicationNumber(RANDOM_10);
        Applicant applicant = new Applicant();
        applicant.setEmail(EMAIL);
        application.setApplicant(applicant);

        challan = new Challan();
        challan.setVehicleNumber(RANDOM_10);
        challan.setAmount(10000);

        appointment = new Appointment();
        appointment.setAppointmentNumber(RANDOM_10);

        license = new DrivingLicense();
        license.setDrivingLicenseNumber(RANDOM_10);
        license.setValidTill(Calendar.getInstance().getTime());
    }

    @Test
    void userRegistration() {
        Mockito.when(commonUtils.generateRandomString(4)).thenReturn("1234");
        BasicResponse regResponse;
        try {
            regResponse = userService.userRegistration(user,"");
        } catch (LicenseAppException e) {
            regResponse = new BasicResponse(false,e.getMessage());
        }
        Assertions.assertTrue(regResponse.isStatus());

        try {
            regResponse = userService.userRegistration(user,"1234");
        } catch (LicenseAppException e) {
            regResponse = new BasicResponse(false,e.getMessage());
        }
        System.out.println(regResponse.getMsg());
        Assertions.assertTrue(regResponse.isStatus());
    }

    @Test
    void userLogin() {
        Mockito.when(userRepository.findById(EMAIL)).thenReturn(Optional.of(user));
        BasicResponse loginResponse;
        try {
            loginResponse = userService.userLogin(user);
        } catch (LicenseAppException e) {
            loginResponse = new BasicResponse(false,e.getMessage());
        }
        System.out.println(loginResponse.getMsg());
        Assertions.assertTrue(loginResponse.isStatus());
    }

    @Test
    void changePassword() {
        Mockito.when(userRepository.findById(EMAIL)).thenReturn(Optional.of(user));
        BasicResponse changePassword;
        try {
            changePassword = userService.changePassword(user,"yahoo");
        } catch (LicenseAppException e) {
            changePassword = new BasicResponse(false,e.getMessage());
        }
        System.out.println(changePassword.getMsg());
        Assertions.assertTrue(changePassword.isStatus());
    }

    @Test
    void forgotPassword() {
        Mockito.when(commonUtils.generateRandomString(10)).thenReturn("1234567890");
        Mockito.when(emailService.sendNewPass(EMAIL,"1234567890")).thenReturn(new BasicResponse(true,"Password Reset"));
        BasicResponse resetPassword = userService.forgotPassword(user);
        System.out.println(resetPassword.getMsg());
        Assertions.assertTrue(resetPassword.isStatus());
    }


    @Test
    void applyForLL() {
        Application application = new Application();
        Applicant applicant = new Applicant();
        application.setApplicant(applicant);
        application.setType(ApplicationType.LL);
        Mockito.when(applicationsRepository.save(application)).thenReturn(application);
        BasicResponse response = userService.applyForLL(application);
        System.out.println(response.getMsg());
        Assertions.assertTrue(response.isStatus());
    }

    @Test
    void applyForDL() {
        application.setType(ApplicationType.DL);
        Mockito.when(applicationsRepository.save(application)).thenReturn(application);
        BasicResponse response = userService.applyForDL(application);
        System.out.println(response.getMsg());
        Assertions.assertTrue(response.isStatus());
    }

    @Test
    void uploadDocuments() {
        Mockito.when(applicationsRepository.findById(RANDOM_10)).thenReturn(Optional.of(application));
        Documents documents = new Documents();
        BasicResponse response;
        try {
            response = userService.uploadDocuments(RANDOM_10,documents);
        } catch (LicenseAppException e) {
            response = new BasicResponse(false,e.getMessage());
        }
        System.out.println(response.getMsg());
        Assertions.assertTrue(response.isStatus());
    }

    @Test
    void checkChallanByVehicleNumber() throws LicenseAppException {
        Mockito.when(challanRepository.findById(RANDOM_10)).thenReturn(Optional.of(challan));
        Challan challan1 = userService.checkChallanByVehicleNumber(RANDOM_10);
        Assertions.assertEquals(challan1.getAmount(),challan.getAmount());
    }

    @Test
    void payChallanByVehicleNumber() {
        Mockito.when(challanRepository.findAll()).thenReturn(new ArrayList<>());
        Assertions.assertTrue(userService.payChallanByVehicleNumber(RANDOM_10).isStatus());
    }

    @Test
    void payFees() {
        Mockito.when(applicationsRepository.findById(RANDOM_10)).thenReturn(Optional.ofNullable(application));

        BasicResponse response;
        try {
            response = userService.payFees(RANDOM_10,10000,"Check");
        } catch (LicenseAppException e) {
            response = new BasicResponse(false,e.getMessage());
        }
        System.out.println(response.getMsg());
        Assertions.assertTrue(response.isStatus());
    }

    @Test
    void emailFeesReceipt() {
        Mockito.when(emailService.sendFeesReceipt(EMAIL,10000)).thenReturn(new BasicResponse(true,"Emailed fees receipt!"));
        BasicResponse response = userService.emailFeesReceipt(EMAIL,10000);
        System.out.println(response.getMsg());
        Assertions.assertTrue(response.isStatus());
    }

    @Test
    void bookSlotLLTest() {
        Mockito.when(appointmentRepository.save(appointment)).thenReturn(appointment);
        BasicResponse response = userService.bookSlotLLTest(appointment);
        Assertions.assertTrue(response.isStatus());
    }

    @Test
    void bookSlotDLTest() {
        Mockito.when(appointmentRepository.save(appointment)).thenReturn(appointment);
        BasicResponse response = userService.bookSlotDLTest(appointment);
        Assertions.assertTrue(response.isStatus());
    }

    @Test
    void getAvailableSlots() {
        Mockito.when(appointmentRepository.findAll()).thenReturn(new ArrayList<>());
        Assertions.assertEquals(0,userService.getAvailableSlots().size());
    }

    @Test
    void renewLL() {
        Mockito.when(drivingLicenseRepository.findById(RANDOM_10)).thenReturn(Optional.ofNullable(license));
        BasicResponse response;
        try {
            response = userService.renewLL(RANDOM_10);
        } catch (LicenseAppException e){
            response = new BasicResponse(false,e.getMessage());
        }
        System.out.println(response.getMsg());
        Assertions.assertFalse(response.isStatus());
    }

    @Test
    void renewDL() {
        Mockito.when(drivingLicenseRepository.findById(RANDOM_10)).thenReturn(Optional.ofNullable(license));
        BasicResponse response;
        try {
            response = userService.renewDL(RANDOM_10);
        } catch (LicenseAppException e){
            response = new BasicResponse(false,e.getMessage());
        }
        System.out.println(response.getMsg());
        Assertions.assertFalse(response.isStatus());
    }

    @Test
    void cancelAppointment() {
        Mockito.when(appointmentRepository.findById(RANDOM_10)).thenReturn(Optional.ofNullable(appointment));
        BasicResponse response;
        try {
            response = userService.cancelAppointment(appointment);
        } catch (LicenseAppException e){
            response = new BasicResponse(false,e.getMessage());
        }
        System.out.println(response.getMsg());
        Assertions.assertTrue(response.isStatus());
    }


}