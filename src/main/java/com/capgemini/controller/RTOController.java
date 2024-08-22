package com.capgemini.controller;

import com.capgemini.dto.*;
import com.capgemini.entity.*;
import com.capgemini.exception.LicenseAppException;
import com.capgemini.service.RTOService;
import com.capgemini.util.RTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * RTOController
 *
 * contains all the endpoints for the rto related services
 */
@RestController
@RequestMapping("/api/rto")
public class RTOController {

    @Autowired
    RTOService rtoService;

    @Autowired
    RTOUtils rtoUtils;

    @PostMapping("/login")
    public BasicResponse rtoLogin(@RequestBody @Valid RTOLoginRequest request) throws LicenseAppException {
        RTOOfficer officer = rtoUtils.toRTOOfficer(request);
        return rtoService.login(officer);
    }

    @GetMapping("/applications/rejected")
    public List<Application> getRejectedApplications(@RequestBody @Valid RejectApplicationsRequest request) {
        return rtoService.getAllRejectedApplications();
    }

    @GetMapping("/applications/pending")
    public List<Application> getPendingApplications(@RequestBody @Valid PendingApplicationsRequest request) {
        return rtoService.getAllPendingApplications();
    }

    @GetMapping("/applications/approved")
    public List<Application> getApprovedApplications(@RequestBody @Valid ApprovedApplicationsRequest request) {
        return rtoService.getAllApprovedApplications();
    }

    @GetMapping("/applications/{id}")
    public Application getApplicationByID(@PathVariable("id") String id) {
        return rtoService.viewApplicationById(id);
    }

    @GetMapping("/challan")
    public List<Challan> checkChallan(@RequestBody @Valid GetAllChallanRequest request) {
        return rtoService.checkAllChallan();
    }

    @GetMapping("/challan/{vehicle}")
    public List<Challan> checkChallanByVehicleNo(@PathVariable("vehicle") String vehicle) {
        return rtoService.checkChallanByVehicleNumber(vehicle);
    }

    @PutMapping("/results/{id}")
    public Application modifyResultsByID(
            @PathVariable("id") String id,
            @RequestBody @Valid ModifyResultsRequest request) throws LicenseAppException {
        return rtoService.modifyTestResultById(id,
                request.isStatus()? ApplicationStatus.APPROVED:
                        ApplicationStatus.REJECTED);
    }

    @GetMapping("/generate/dl")
    public DrivingLicense generateDL(@RequestBody @Valid GenerateDLRequest request) throws LicenseAppException {
        return rtoService.generateDrivingLicense(request.getApplicationNumber(), request.getRtoEmail());
    }

    @GetMapping("/generate/ll")
    public DrivingLicense generateLL(@RequestBody @Valid GenerateLLRequest request) throws LicenseAppException {
        return rtoService.generateLearnerLicense(request.getApplicationNumber(), request.getRtoEmail());
    }

    @PostMapping("/email")
    public BasicResponse emailDL(@RequestBody @Valid EmailDLRequest request) {
        return rtoService.emailLicense(request.getEmail(), request.getDrivingLicense());
    }
}
