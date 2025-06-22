package org.ecommerce.spring_security_poc.repositories;

import org.ecommerce.spring_security_poc.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
