package com.capgemini.repo;

import com.capgemini.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * AppointmentRepository
 *
 * repository for application entity
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,String> {
}
