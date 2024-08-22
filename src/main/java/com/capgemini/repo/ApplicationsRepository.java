package com.capgemini.repo;

import com.capgemini.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ApplicationsRepository
 *
 * repository for application entity
 */
@Repository
public interface ApplicationsRepository extends JpaRepository<Application,String> {

}
