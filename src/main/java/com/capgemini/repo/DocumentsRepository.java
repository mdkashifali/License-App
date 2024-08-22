package com.capgemini.repo;

import com.capgemini.entity.Documents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentsRepository extends JpaRepository<Documents,Integer> {
}
