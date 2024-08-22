package com.capgemini.util;

import com.capgemini.entity.Gender;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 * Common Utilities
 */
@Component
public class CommonUtils {

    /**
     *  Generate any length alphanumeric string
     * @param length length of the string
     * @return String
     */
    public String generateRandomString(int length) {
        Random random = new SecureRandom();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            String alphabets = "0123456789QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
            buffer.append(alphabets.charAt(random.nextInt(alphabets.length())));
        }
        return new String(buffer);
    }


    /**
     *  Changing String to Gender enum
     * @param s any string
     * @return Gender
     */
    public Gender fromString(String s){
        if (s.toLowerCase().startsWith("m")){
            return Gender.MALE;
        } else if (s.toLowerCase().startsWith("f")) {
            return Gender.FEMALE;
        } else {
            return Gender.OTHER;
        }
    }

    /**
     *  Parsing Date from string
     * @param s string
     * @return Date
     * @throws ParseException if string invalid
     */
    public Date convertFromString(String s) throws ParseException {
        return new SimpleDateFormat("yyyy/MM/dd").parse(s);
    }
}
