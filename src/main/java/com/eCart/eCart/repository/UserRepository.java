package com.eCart.eCart.repository;

import com.eCart.eCart.entity.User;
import com.eCart.eCart.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>findFirstByEmail(String email);
    User findByRole(UserRole userRole);

}
