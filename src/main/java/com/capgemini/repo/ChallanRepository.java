package com.capgemini.repo;

import com.capgemini.entity.Challan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ChallanRepository
 *
 * repository for challan entity
 */
@Repository
public interface ChallanRepository extends JpaRepository<Challan,String> {

}
