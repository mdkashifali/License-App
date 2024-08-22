package com.capgemini.util;

import com.capgemini.dto.*;
import com.capgemini.entity.*;

import com.capgemini.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Parsing User related DTOs to entities
 */
@Component
public class UserUtils {


    @Autowired
    UserRepository userRepository;

    @Autowired
    CommonUtils commonUtils;

    public User toUser(LoginUserRequest request) {
        User user = new User();
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        return user;
    }

    public User toUser(RegisterUserRequest request) {
        User user = new User();
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        return user;
    }

    public User toUser(ChangePasswordRequest request) {
        User user = new User();
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        return user;
    }

    public User toUser(OTPVerifyRequest request) {
        User user = new User();
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        return user;
    }

    public User toUser(ForgotPasswordRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        return user;
    }

    public Appointment toAppointment(BookSlotRequest request) throws ParseException {
        Appointment appointment = new Appointment();
        appointment.setTimeSlot(request.getTimeSlot());
        appointment.setTestDate(commonUtils.convertFromString(request.getTestDate()));
        return appointment;
    }

    public Application toApplication(ApplyForLicenseRequest request, ApplicationType type) throws ParseException {
        Application application = new Application();
        Applicant applicant = new Applicant();
        applicant.setFirstName(request.getFirstName());
        applicant.setLastName(request.getLastName());
        applicant.setMiddleName(request.getMiddleName());
        applicant.setUser(userRepository.getOne(request.getEmail()));
        applicant.setGender(commonUtils.fromString(request.getGender()));
        applicant.setDateOfBirth(commonUtils.convertFromString(request.getDateOfBirth()));
        applicant.setPlaceOfBirth(request.getPlaceOfBirth());
        applicant.setQualification(request.getQualification());
        applicant.setNationality(request.getNationality());
        applicant.setMobile(request.getMobile());
        applicant.setEmail(request.getEmail());
        applicant.setPermanentAddress(request.getPermanentAddress());
        applicant.setPresentAddress(request.getPresentAddress());
        applicant.setVehicleType(request.getVehicleType());
        applicant.setVehicleNumber(request.getVehicleNumber());
        application.setApplicant(applicant);
        application.setApplicationDate(Calendar.getInstance().getTime());
        application.setAmountPaid(0);
        application.setType(type);
        application.setStatus(ApplicationStatus.PENDING);
        return application;
    }

    public Documents toDocuments(UploadDocumentsRequest request) {
        Documents documents = new Documents();
        documents.setPhoto(request.getPhoto());
        documents.setAddressProof(request.getAddressProof());
        documents.setIdProof(request.getIdProof());
        return documents;
    }

}
