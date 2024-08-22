package com.capgemini.repo;

import com.capgemini.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantsRepository extends JpaRepository<Applicant,Integer> {
}
