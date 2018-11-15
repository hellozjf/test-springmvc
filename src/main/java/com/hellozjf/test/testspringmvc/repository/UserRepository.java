package com.hellozjf.test.testspringmvc.repository;

import com.hellozjf.test.testspringmvc.dataobject.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hellozjf
 */
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
