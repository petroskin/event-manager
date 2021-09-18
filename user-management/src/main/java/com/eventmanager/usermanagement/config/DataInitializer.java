package com.eventmanager.usermanagement.config;

import com.eventmanager.usermanagement.domain.models.User;
import com.eventmanager.usermanagement.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class DataInitializer
{
    private final UserRepository userRepository;

    @PostConstruct
    public void initData()
    {
        User u1 = new User("u1", "u1", "u1@a.b", "u1pw");
        User u2 = new User("u2", "u2", "u2@a.b", "u2pw");
        if (userRepository.findAll().isEmpty())
            userRepository.saveAll(Arrays.asList(u1, u2));
    }
}
