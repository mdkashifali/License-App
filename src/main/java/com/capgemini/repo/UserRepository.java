package com.capgemini.repo;

import com.capgemini.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 *
 * repository for User entity
 */
@Repository
public interface UserRepository extends JpaRepository<User,String> {
}
