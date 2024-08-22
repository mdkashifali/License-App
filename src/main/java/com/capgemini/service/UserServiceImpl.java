package com.capgemini.service;

import com.capgemini.constants.Constants;
import com.capgemini.dto.BasicResponse;
import com.capgemini.entity.*;
import com.capgemini.exception.LicenseAppException;
import com.capgemini.repo.*;
import com.capgemini.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApplicantsRepository applicantsRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    CommonUtils commonUtils;

    @Autowired
    ApplicationsRepository applicationsRepository;

    @Autowired
    AddressRepository addressRepository;


    @Autowired
    ChallanRepository challanRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    DrivingLicenseRepository drivingLicenseRepository;

    @Autowired
    DocumentsRepository documentsRepository;

    ConcurrentHashMap<String,String> otpMap = new ConcurrentHashMap<>();

    @Override
    public BasicResponse userRegistration(User user, String otp) throws LicenseAppException {
        logger.info("******** User Registration: {} ",user);

        if (otpMap.containsKey(user.getEmail())) {
            if (otpMap.get(user.getEmail()).equals(otp)){
                userRepository.save(user);
                otpMap.remove(user.getEmail());
                return new BasicResponse(true,Constants.USER_CREATED);
            } else {
                throw new LicenseAppException(Constants.OTP_INVALID);
            }
        } else {
            otp = commonUtils.generateRandomString(4);
            otpMap.put(user.getEmail(),otp);
            emailService.sendOTP(user.getEmail(),otp);
            return new BasicResponse(true,Constants.VERIFICATION_OTP_SENT);
        }
    }

    @Override
    public BasicResponse userLogin(User user) throws LicenseAppException {
        logger.info("******** User Login: {} ",user);

        Optional<User> maybeUser = userRepository.findById(user.getEmail());
        if (maybeUser.isPresent()){
            User isUser = maybeUser.get();
            if (isUser.getPassword().equals(user.getPassword())){
                return new BasicResponse(true,Constants.AUTHENTICATED);
            } else {
                throw new LicenseAppException(Constants.INVALID_CREDENTIALS);
            }
        } else {
            throw new LicenseAppException(Constants.USER_NOT_FOUND);
        }
    }

    @Override
    public BasicResponse changePassword(User user, String newPassword) throws LicenseAppException {
        logger.info("******** Changing Password: {}",user);

        Optional<User> u = userRepository.findById(user.getEmail());
        if (u.isPresent()){
            User uu = u.get();
            if (uu.getPassword().equals(user.getPassword())){
                uu.setPassword(newPassword);
                userRepository.save(uu);
                return new BasicResponse(true,Constants.PASSWORD_CHANGED);
            } else {
                throw new LicenseAppException(Constants.INVALID_CREDENTIALS);
            }
        } else {
            throw new LicenseAppException(Constants.USER_NOT_FOUND);
        }
    }

    @Override
    public BasicResponse forgotPassword(User user) {
        logger.info("******** Resetting Password: {}",user);

        String pass = commonUtils.generateRandomString(10);
        user.setPassword(pass);
        userRepository.save(user);
        return emailService.sendNewPass(user.getEmail(),pass);
    }

    @Override
    public BasicResponse applyForLL(Application llApplication) {
        logger.info("******** Applying Learner License: {} ",llApplication);

        Address presentAddress = addressRepository.save(llApplication.getApplicant().getPresentAddress());
        Address permanentAddress = addressRepository.save(llApplication.getApplicant().getPermanentAddress());
        llApplication.getApplicant().setPermanentAddress(permanentAddress);
        llApplication.getApplicant().setPresentAddress(presentAddress);
        Applicant applicant = applicantsRepository.save(llApplication.getApplicant());
        llApplication.setApplicant(applicant);
        Application application = applicationsRepository.save(llApplication);
        return new BasicResponse(true,"Successfully applied your application number is : "+application.getApplicationNumber());
    }

    @Override
    public BasicResponse applyForDL(Application dlApplication) {
        logger.info("******** Applying Driver License: {} ",dlApplication);

        Address presentAddress = addressRepository.save(dlApplication.getApplicant().getPresentAddress());
        Address permanentAddress = addressRepository.save(dlApplication.getApplicant().getPermanentAddress());
        dlApplication.getApplicant().setPermanentAddress(permanentAddress);
        dlApplication.getApplicant().setPresentAddress(presentAddress);
        Applicant applicant = applicantsRepository.save(dlApplication.getApplicant());
        dlApplication.setApplicant(applicant);
        Application application = applicationsRepository.save(dlApplication);
        return new BasicResponse(true,"Successfully applied your application number is : "+application.getApplicationNumber());
    }

    @Override
    public BasicResponse uploadDocuments(String applicationNumber, Documents documents) throws LicenseAppException {
        logger.info("******** Uploading Documents: {}",applicationNumber);

        documents = documentsRepository.save(documents);
        Optional<Application> application = applicationsRepository.findById(applicationNumber);
        if (application.isPresent()){
            Application application1 = application.get();
            application1.setDocuments(documents);
            applicationsRepository.save(application1);
            return new BasicResponse(true,Constants.DOCUMENTS_UPDATED);
        } else {
            throw new LicenseAppException(Constants.APPLICATION_NOT_FOUND);
        }
    }

    @Override
    public Challan checkChallanByVehicleNumber(String vehicleNumber) throws LicenseAppException {
        logger.info("******** Checking Challan: {}",vehicleNumber);

        Optional<Challan> challan = challanRepository.findById(vehicleNumber);
        return challan.orElseThrow(() -> new LicenseAppException(Constants.CHALLAN_NOT_FOUND));
    }

    @Override
    public BasicResponse payChallanByVehicleNumber(String vehicleNumber) {
        logger.info("******** Paying Challan: {}",vehicleNumber);

        List<Challan> challans = challanRepository.findAll();
        List<Challan> challan = challans.stream().
                filter(challan1 ->
                        challan1.getVehicleNumber().equals(vehicleNumber) &&
                                challan1.getAmount()>0)
                .collect(Collectors.toList());
        challan.forEach(challan1 -> challan1.setAmount(0));

        challan.forEach(challan1 -> challanRepository.save(challan1));
        return new BasicResponse(true,Constants.CHALLAN_PAID);
    }

    @Override
    public BasicResponse payFees(String applicationNo, int amount, String modeOfPayment) throws LicenseAppException {
        logger.info("******** Paying Fees: {}",applicationNo);

        Optional<Application> application = applicationsRepository.findById(applicationNo);
        if (application.isPresent()){
            Application application1 = application.get();
            application1.setAmountPaid(amount);
            application1.setModeOfPayment(modeOfPayment);
            applicationsRepository.save(application1);
            emailFeesReceipt(application1.getApplicant().getEmail(),amount);
            return new BasicResponse(true,Constants.PAYMENT_DONE);
        } else {
            throw new LicenseAppException(Constants.APPLICATION_NOT_FOUND);
        }
    }

    @Override
    public BasicResponse emailFeesReceipt(String email, int amount) {
        logger.info("******** Email fee receipt {}",email);

        return emailService.sendFeesReceipt(email, amount);
    }

    @Override
    public BasicResponse bookSlotLLTest(Appointment appointment) {
        logger.info("******** Book Slot for LL Test:");
        Appointment appointment1 = appointmentRepository.save(appointment);
        return new BasicResponse(true,"Appointment saved : "+appointment1.getAppointmentNumber());
    }

    @Override
    public BasicResponse bookSlotDLTest(Appointment appointment) {
        logger.info("******** Book Slot for DL Test:");
        Appointment appointment1 = appointmentRepository.save(appointment);
        return new BasicResponse(true,"Appointment saved : "+appointment1.getAppointmentNumber());
    }

    @Override
    public List<Appointment> getAvailableSlots() {
        logger.info("******** Getting available Slots:");

        return appointmentRepository.findAll().stream().filter(
                appointment -> appointment.getTestDate().after(Calendar.getInstance().getTime())
        ).collect(Collectors.toList());
    }

    @Override
    public BasicResponse renewLL(String drivingLicenseNumber) throws LicenseAppException {
        logger.info("******** Renewing LL:");

        Optional<DrivingLicense> ll1 = drivingLicenseRepository.findById(drivingLicenseNumber);
        if(ll1.isPresent()) {
            DrivingLicense ll = ll1.get();
            Date validity= ll.getValidTill();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

            Date date = new Date();
            formatter.format(date);
            if(validity.compareTo(date) > 0) {
                Calendar c=Calendar.getInstance();
                c.setTime(validity);
                c.add(Calendar.YEAR, 5);
                ll.setValidTill(c.getTime());
                return new BasicResponse(true,Constants.LICENSE_RENEWED);
            }
            else {
                throw new LicenseAppException(Constants.LICENSE_STILL_VALID);
            }
        } else {
            throw new LicenseAppException(Constants.LICENSE_NOT_FOUND);
        }
    }

    @Override
    public BasicResponse renewDL(String drivingLicenseNumber) throws LicenseAppException {
        logger.info("******** Renewing DL:");

        Optional<DrivingLicense> dl1 = drivingLicenseRepository.findById(drivingLicenseNumber);
        if(dl1.isPresent()) {
            DrivingLicense dl = dl1.get();
            Date validity=dl.getValidTill();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            formatter.format(date);

            if(validity.compareTo(date) > 0) {
                Calendar c=Calendar.getInstance();
                c.setTime(validity);
                c.add(Calendar.YEAR, 5);
                dl.setDateOfIssue(c.getTime());
                drivingLicenseRepository.save(dl);
                return new BasicResponse(true,Constants.LICENSE_RENEWED);
            }
            else {
                throw new LicenseAppException(Constants.LICENSE_STILL_VALID);
            }
        } else {
            throw new LicenseAppException(Constants.LICENSE_NOT_FOUND);
        }
    }

    @Override
    public BasicResponse cancelAppointment(Appointment appointment) throws LicenseAppException {
        logger.info("******** Cancel appointment");

        Optional<Appointment> appointment1 =
                appointmentRepository.findById(appointment.getAppointmentNumber());
        if(appointment1.isPresent()) {
            appointmentRepository.delete(appointment);
            return new BasicResponse(true,Constants.APPOINTMENT_CANCELLED);
        } else {
            throw new LicenseAppException(Constants.APPOINTMENT_NOT_FOUND);
        }
    }
}
