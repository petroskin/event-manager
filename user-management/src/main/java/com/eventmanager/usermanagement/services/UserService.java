package com.eventmanager.usermanagement.services;

import com.eventmanager.usermanagement.domain.models.User;
import com.eventmanager.usermanagement.domain.models.UserId;
import com.eventmanager.usermanagement.services.forms.RegisterForm;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.Optional;

public interface UserService extends UserDetailsService
{
    UserId register(RegisterForm registerForm);
    Collection<User> findAll();
    Optional<User> findById(UserId id);
    Optional<User> findByEmail(String email);
}
