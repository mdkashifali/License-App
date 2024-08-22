package com.capgemini.exception;

/**
 * LicenseAppException
 *
 * wrapper exception for all the api logic related exceptions
 *
 */
public class LicenseAppException extends Exception {

    public LicenseAppException(String msg) {
        super(msg);
    }
}
