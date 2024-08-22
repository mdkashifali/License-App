package com.capgemini.service;

import com.capgemini.dto.BasicResponse;
import com.capgemini.entity.*;
import com.capgemini.exception.LicenseAppException;
import com.capgemini.repo.ApplicationsRepository;
import com.capgemini.repo.ChallanRepository;
import com.capgemini.repo.DrivingLicenseRepository;
import com.capgemini.repo.RTOOfficerRepository;
import com.capgemini.util.CommonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RTOServiceImplTest {

    @InjectMocks
    RTOServiceImpl rtoService;

    @Mock
    RTOOfficerRepository rtoOfficerRepository;

    @Mock
    ApplicationsRepository applicationsRepository;

    @Mock
    ChallanRepository challanRepository;

    @Mock
    DrivingLicenseRepository drivingLicenseRepository;

    @Mock
    CommonUtils commonUtils;

    @Mock
    EmailService emailService;

    RTOOfficer rtoOfficer;
    DrivingLicense license;


    private static final String EMAIL = "mayankkhattar007@gmail.com";
    private static final String PASSWORD = "Mayank@007";
    private static final String RANDOM_10 = "1234567890";

    List<Application> applications;
    List<Challan> challanList;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        rtoOfficer = new RTOOfficer();
        rtoOfficer.setEmail(EMAIL);
        rtoOfficer.setPassword(PASSWORD);

        RTOOffice office = new RTOOffice();
        office.setRtoId(732876);
        office.setRtoName("yooo");
        rtoOfficer.setOffice(office);

        license = new DrivingLicense();
        license.setDrivingLicenseNumber(RANDOM_10);
        license.setValidTill(Calendar.getInstance().getTime());


        applications = new ArrayList<>();
        Application application;
        for (int i = 0; i < 9; i++) {
            application = new Application();
            application.setType(ApplicationType.DL);
            application.setApplicationNumber(RANDOM_10);
            switch (i%3) {
                case 0: {
                    application.setStatus(ApplicationStatus.REJECTED);
                }break;
                case 1:{
                    application.setStatus(ApplicationStatus.APPROVED);
                }break;
                case 2:{
                    application.setStatus(ApplicationStatus.PENDING);
                }break;
            }
            applications.add(application);
        }
        challanList = new ArrayList<>();
        Challan challan = new Challan();
        challan.setChallanNumber(RANDOM_10);
        challan.setVehicleNumber(RANDOM_10);
        challan.setAmount(10000);
        challanList.add(challan);
    }

    @Test
    void login() {
        Mockito.when(rtoOfficerRepository.findById(EMAIL)).thenReturn(Optional.ofNullable(rtoOfficer));
        BasicResponse response;
        try {
            response = rtoService.login(rtoOfficer);
        } catch (LicenseAppException e){
            response = new BasicResponse(false,e.getMessage());
        }
        System.out.println(response.getMsg());
        Assertions.assertTrue(response.isStatus());
    }

    @Test
    void getAllPendingApplications() {
        Mockito.when(applicationsRepository.findAll()).thenReturn(applications);
        List<Application> applicationList = rtoService.getAllPendingApplications();
        Assertions.assertEquals(3,applicationList.size());
    }

    @Test
    void getAllRejectedApplications() {
        Mockito.when(applicationsRepository.findAll()).thenReturn(applications);
        List<Application> applicationList = rtoService.getAllRejectedApplications();
        Assertions.assertEquals(3,applicationList.size());
    }

    @Test
    void getAllApprovedApplications() {
        Mockito.when(applicationsRepository.findAll()).thenReturn(applications);
        List<Application> applicationList = rtoService.getAllApprovedApplications();
        Assertions.assertEquals(3,applicationList.size());
    }

    @Test
    void viewApplicationById() {
        Mockito.when(applicationsRepository.getOne(RANDOM_10)).thenReturn(applications.get(0));
        Application application = rtoService.viewApplicationById(RANDOM_10);
        Assertions.assertEquals(ApplicationType.DL,application.getType());
    }

    @Test
    void checkChallanByVehicleNumber() {
        Mockito.when(challanRepository.findAll()).thenReturn(challanList);
        List<Challan> challans = rtoService.checkChallanByVehicleNumber(RANDOM_10);
        Assertions.assertEquals(1,challans.size());
    }

    @Test
    void checkAllChallan() {
        Mockito.when(challanRepository.findAll()).thenReturn(challanList);
        List<Challan> challans = rtoService.checkAllChallan();
        Assertions.assertEquals(1,challans.size());
    }

    @Test
    void modifyTestResultById() throws LicenseAppException {
        Application application = new Application();
        application.setApplicationNumber(RANDOM_10);
        application.setStatus(ApplicationStatus.APPROVED);
        Mockito.when(applicationsRepository.findById(RANDOM_10)).thenReturn(Optional.of(application));
        Mockito.when(applicationsRepository.save(application)).thenReturn(application);
        Application application1 = rtoService.modifyTestResultById(RANDOM_10,ApplicationStatus.APPROVED);
        Assertions.assertEquals(ApplicationStatus.APPROVED,application1.getStatus());
    }

    @Test
    void generateLearnerLicense() throws LicenseAppException {
        Application application = new Application();
        application.setApplicationNumber(RANDOM_10);
        application.setStatus(ApplicationStatus.APPROVED);
        Mockito.when(applicationsRepository.findById(RANDOM_10)).thenReturn(Optional.of(application));
        Mockito.when(rtoOfficerRepository.findById(EMAIL)).thenReturn(Optional.ofNullable(rtoOfficer));
        Mockito.when(commonUtils.generateRandomString(15)).thenReturn(RANDOM_10);
        DrivingLicense license1 = rtoService.generateLearnerLicense(RANDOM_10,EMAIL);
        Assertions.assertEquals(RANDOM_10,license1.getDrivingLicenseNumber());
    }

    @Test
    void generateDrivingLicense() throws LicenseAppException {
        Application application = new Application();
        application.setApplicationNumber(RANDOM_10);
        application.setStatus(ApplicationStatus.APPROVED);
        Mockito.when(applicationsRepository.findById(RANDOM_10)).thenReturn(Optional.of(application));
        Mockito.when(rtoOfficerRepository.findById(EMAIL)).thenReturn(Optional.ofNullable(rtoOfficer));
        Mockito.when(commonUtils.generateRandomString(15)).thenReturn(RANDOM_10);
        DrivingLicense license1 = rtoService.generateDrivingLicense(RANDOM_10,EMAIL);
        Assertions.assertEquals(RANDOM_10,license1.getDrivingLicenseNumber());
    }

    @Test
    void emailLicense() {
        Mockito.when(emailService.sendDrivingLicense(EMAIL,license)).thenReturn(new BasicResponse(true,"Email sent!"));
        BasicResponse response;
        response = rtoService.emailLicense(EMAIL,license);
        System.out.println(response.getMsg());
        Assertions.assertTrue(response.isStatus());
    }
}