package com.eventmanager.usermanagement.services.impl;

import com.eventmanager.usermanagement.domain.exceptions.EmailTakenException;
import com.eventmanager.usermanagement.domain.exceptions.UserNotFoundException;
import com.eventmanager.usermanagement.domain.models.User;
import com.eventmanager.usermanagement.domain.models.UserId;
import com.eventmanager.usermanagement.domain.repository.UserRepository;
import com.eventmanager.usermanagement.services.UserService;
import com.eventmanager.usermanagement.services.forms.RegisterForm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService
{

    private final UserRepository userRepository;
    private final Validator validator;

    @Override
    public UserId register(RegisterForm registerForm)
    {
        Objects.requireNonNull(registerForm, "User must not be null");
        var constraintViolations = validator.validate(registerForm);
        if (constraintViolations.size() > 0)
            throw new ConstraintViolationException("The form is not valid", constraintViolations);
        if (userRepository.findByEmail(registerForm.getEmail()).isPresent())
            throw new EmailTakenException();
        User newUser = userRepository.saveAndFlush(toDomainObject(registerForm));
        return newUser.getId();
    }

    @Override
    public Collection<User> findAll()
    {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(UserId id)
    {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    @Override
    public void joinedOrganization(UserId id)
    {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.joinedOrganization();
        userRepository.saveAndFlush(user);
    }

    @Override
    public void leftOrganization(UserId id)
    {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.leftOrganization();
        userRepository.saveAndFlush(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException
    {
        Optional<User> user = this.findByEmail(s);
        if (user.isEmpty())
            return null;
        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(),
                user.get().getPassword(),
                List.of(new GrantedAuthority[]{new SimpleGrantedAuthority("ROLE_USER")})
        );
    }

    private User toDomainObject(RegisterForm registerForm)
    {
        return new User(registerForm.getName(), registerForm.getSurname(), registerForm.getEmail(), registerForm.getPassword());
    }
}
