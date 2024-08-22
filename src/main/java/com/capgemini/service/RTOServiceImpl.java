package com.capgemini.service;

import com.capgemini.constants.Constants;
import com.capgemini.dto.BasicResponse;
import com.capgemini.entity.*;
import com.capgemini.exception.LicenseAppException;
import com.capgemini.repo.ApplicationsRepository;
import com.capgemini.repo.ChallanRepository;
import com.capgemini.repo.DrivingLicenseRepository;
import com.capgemini.repo.RTOOfficerRepository;
import com.capgemini.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RTOServiceImpl implements RTOService {

    // logging utility
    private final Logger logger = LoggerFactory.getLogger(RTOServiceImpl.class);

    @Autowired
    RTOOfficerRepository rtoOfficerRepository;

    @Autowired
    ApplicationsRepository applicationsRepository;

    @Autowired
    ChallanRepository challanRepository;

    @Autowired
    DrivingLicenseRepository drivingLicenseRepository;

    @Autowired
    CommonUtils commonUtils;

    @Autowired
    EmailService emailService;

    /**
     * RTO Officer login
     * @param officer with email and password
     * @return BasicResponse
     * @throws LicenseAppException if error
     */
    @Override
    public BasicResponse login(RTOOfficer officer) throws LicenseAppException {
        logger.info("******** RTO Login: {} ",officer);

        Optional<RTOOfficer> maybeOfficer = rtoOfficerRepository.findById(officer.getEmail());
        if (maybeOfficer.isPresent()){
            RTOOfficer isOfficer = maybeOfficer.get();
            if (isOfficer.getPassword().equals(officer.getPassword())){
                return new BasicResponse(true, Constants.AUTHENTICATED);
            } else {
                throw new LicenseAppException(Constants.INVALID_CREDENTIALS);
            }
        } else {
            throw new LicenseAppException(Constants.OFFICER_NOT_FOUND);
        }
    }

    /**
     * Get all the pending applications
     * @return List<Application>
     */
    @Override
    public List<Application> getAllPendingApplications() {
        logger.info("******** Getting Pending Applications");

        List<Application> applications = applicationsRepository.findAll();
        return applications.
                stream().
                filter(application -> application.getStatus().equals(ApplicationStatus.PENDING)).
                collect(Collectors.toList());
    }


    /**
     * Get all the rejected applications
     * @return List<Application>
     */
    @Override
    public List<Application> getAllRejectedApplications() {
        logger.info("******** Getting Rejected Applications");

        List<Application> applications = applicationsRepository.findAll();
        return applications.
                stream().
                filter(application -> application.getStatus().equals(ApplicationStatus.REJECTED)).
                collect(Collectors.toList());
    }


    /**
     * Get all the approved applications
     * @return List<Application>
     */
    @Override
    public List<Application> getAllApprovedApplications() {
        logger.info("******** Getting Approved Applications");

        List<Application> applications = applicationsRepository.findAll();
        return applications.
                stream().
                filter(application -> application.getStatus().equals(ApplicationStatus.APPROVED)).
                collect(Collectors.toList());
    }


    /**
     * Get application by id
     * @param applicationNumber id
     * @return Application
     */
    @Override
    public Application viewApplicationById(String applicationNumber) {
        logger.info("******** Viewing Application by id {}",applicationNumber);

        return applicationsRepository.getOne(applicationNumber);
    }

    /**
     * get all challans of a vehicle
     * @param vehicleNumber id of vehicle
     * @return List<Application>
     */
    @Override
    public List<Challan> checkChallanByVehicleNumber(String vehicleNumber) {
        logger.info("******** Checking challan {} ",vehicleNumber);

        return challanRepository.
                findAll().
                stream().
                filter(challan -> challan.getVehicleNumber().equals(vehicleNumber)).
                collect(Collectors.toList());
    }

    /**
     * Get all challans
     * @return List<Challan>
     */
    @Override
    public List<Challan> checkAllChallan() {
        logger.info("******** Checking all challans");
        return challanRepository.findAll();
    }

    /**
     * modify the test results by id
     * @param applicationNumber id
     * @param status modification
     * @return Application
     * @throws LicenseAppException if error
     */
    @Override
    public Application modifyTestResultById(String applicationNumber, ApplicationStatus status) throws LicenseAppException {
        logger.info("******** Modifying test {}",applicationNumber);

        Optional<Application> maybeApplication = applicationsRepository.findById(applicationNumber);
        if (maybeApplication.isPresent()){
            Application isApplication = maybeApplication.get();
            isApplication.setStatus(status);
            return applicationsRepository.save(isApplication);
        } else {
            throw new LicenseAppException(Constants.APPLICATION_NOT_FOUND);
        }
    }

    /**
     *  generate ll of a user
     * @param applicationNumber application id
     * @param rtoEmail email of issuing officer
     * @return DrivingLicense
     * @throws LicenseAppException if error
     */
    @Override
    public DrivingLicense generateLearnerLicense(String applicationNumber,String rtoEmail) throws LicenseAppException {
        logger.info("******** Generating ll {} ",applicationNumber);

        Optional<Application> maybeApplication = applicationsRepository.findById(applicationNumber);
        if (maybeApplication.isPresent()){
            Application isApplication = maybeApplication.get();
            if (isApplication.getStatus().equals(ApplicationStatus.APPROVED)){
                Calendar calendar = Calendar.getInstance();
                DrivingLicense drivingLicense = new DrivingLicense();
                drivingLicense.setDrivingLicenseNumber(commonUtils.generateRandomString(15));
                drivingLicense.setApplication(isApplication);
                drivingLicense.setDateOfIssue(calendar.getTime());
                Optional<RTOOfficer> rtoOfficer = rtoOfficerRepository.findById(rtoEmail);
                rtoOfficer.ifPresent(officer -> drivingLicense.setIssuedBy(officer.getOffice()));
                calendar.add(Calendar.YEAR,5);
                drivingLicense.setValidTill(calendar.getTime());
                drivingLicenseRepository.save(drivingLicense);
                return drivingLicense;
            } else {
                throw new LicenseAppException(Constants.APPLICATION_NOT_APPROVED);
            }
        } else {
            throw new LicenseAppException(Constants.APPLICATION_NOT_FOUND);
        }
    }


    /**
     * generating dl of a user
     * @param applicationNumber application id
     * @param rtoEmail email of issuing officer
     * @return DrivingLicense
     * @throws LicenseAppException if error
     */
    @Override
    public DrivingLicense generateDrivingLicense(String applicationNumber,String rtoEmail) throws LicenseAppException {
        logger.info("******** Generating DL {} ",applicationNumber);

        Optional<Application> maybeApplication = applicationsRepository.findById(applicationNumber);
        if (maybeApplication.isPresent()){
            Application isApplication = maybeApplication.get();
            if (isApplication.getStatus().equals(ApplicationStatus.APPROVED)){
                Calendar calendar = Calendar.getInstance();
                DrivingLicense drivingLicense = new DrivingLicense();
                drivingLicense.setDrivingLicenseNumber(commonUtils.generateRandomString(15));
                drivingLicense.setApplication(isApplication);
                drivingLicense.setDateOfIssue(calendar.getTime());
                Optional<RTOOfficer> rtoOfficer = rtoOfficerRepository.findById(rtoEmail);
                rtoOfficer.ifPresent(officer -> drivingLicense.setIssuedBy(officer.getOffice()));
                calendar.add(Calendar.MONTH,6);
                drivingLicense.setValidTill(calendar.getTime());
                drivingLicenseRepository.save(drivingLicense);
                return drivingLicense;
            } else {
                throw new LicenseAppException(Constants.APPLICATION_NOT_APPROVED);
            }
        } else {
            throw new LicenseAppException(Constants.APPLICATION_NOT_FOUND);
        }
    }

    /**
     * Email the license to user
     * @param email user's email
     * @param license user's license
     * @return BasicResponse
     */
    @Override
    public BasicResponse emailLicense(String email,DrivingLicense license) {
        logger.info("******** Email License {} ",email);
        return emailService.sendDrivingLicense(email,license);
    }
}
