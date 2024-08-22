package com.capgemini.repo;

import com.capgemini.entity.RTOOfficer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * RTOOfficerRepository
 *
 * repository for RTOOfficer entity
 */
@Repository
public interface RTOOfficerRepository extends JpaRepository<RTOOfficer,String> {

}
