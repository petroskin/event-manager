package com.eventmanager.usermanagement.domain.repository;

import com.eventmanager.usermanagement.domain.models.User;
import com.eventmanager.usermanagement.domain.models.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, UserId>
{
    Optional<User> findByEmail(String email);
}
