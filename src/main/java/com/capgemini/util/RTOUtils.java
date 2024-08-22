package com.capgemini.util;

import com.capgemini.dto.RTOLoginRequest;
import com.capgemini.entity.RTOOfficer;
import org.springframework.stereotype.Component;

/**
 * Parsing RTOOfficer related DTOs to entities
 */
@Component
public class RTOUtils {

    public RTOOfficer toRTOOfficer(RTOLoginRequest request){
        RTOOfficer officer = new RTOOfficer();
        officer.setEmail(request.getEmail());
        officer.setPassword(request.getPassword());
        return officer;
    }
}
