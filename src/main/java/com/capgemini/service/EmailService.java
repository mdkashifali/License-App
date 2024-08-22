package com.capgemini.service;

import com.capgemini.constants.Constants;
import com.capgemini.dto.BasicResponse;
import com.capgemini.entity.DrivingLicense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * This method will send compose and send the otp message
     * @param email user's email
     * @param otp otp sent to the email
     */
    public void sendOTP(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(Constants.SENDER_EMAIL);
        message.setSubject(Constants.EMAIL_SUBJECT);
        message.setText("Your otp is : "+otp);
        mailSender.send(message);
    }

    /**
     * This method will send compose and send the new password message
     * @param email user's email
     * @param pass password sent to the email
     * @return BasicResponse object for api response
     */
    public BasicResponse sendNewPass(String email,String pass) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(Constants.SENDER_EMAIL);
        message.setSubject(Constants.EMAIL_SUBJECT);
        message.setText("Your pass is : "+pass+" pls change it soon");
        mailSender.send(message);
        return new BasicResponse(true,"New Password Sent!");
    }

    /**
     * This method will send compose and send the fees receipt message
     * @param email user's email
     * @param amount amount paid by the user
     * @return BasicResponse object for api response
     */
    public BasicResponse sendFeesReceipt(String email, int amount){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(Constants.SENDER_EMAIL);
        message.setSubject(Constants.EMAIL_SUBJECT);
        message.setText("Your fees is paid. Amount: "+amount);
        mailSender.send(message);
        return new BasicResponse(true,"Email Sent to : "+email);
    }

    /**
     * This method will send compose and send the driving license message
     * @param email user's email
     * @param drivingLicense user's license
     * @return BasicResponse object for api reply
     */
    public BasicResponse sendDrivingLicense(String email, DrivingLicense drivingLicense) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(Constants.SENDER_EMAIL);
        message.setSubject(Constants.EMAIL_SUBJECT);
        message.setText(drivingLicense.toString());
        mailSender.send(message);
        return new BasicResponse(true,"Email Sent to : "+email);
    }

}
