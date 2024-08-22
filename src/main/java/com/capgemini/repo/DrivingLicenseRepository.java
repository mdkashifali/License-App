package com.capgemini.repo;

import com.capgemini.entity.DrivingLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DrivingLicenseRepository
 *
 * repository for driving license entity
 */
@Repository
public interface DrivingLicenseRepository extends JpaRepository<DrivingLicense,String> {
}
