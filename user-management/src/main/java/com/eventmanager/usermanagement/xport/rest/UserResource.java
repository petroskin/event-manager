package com.eventmanager.usermanagement.xport.rest;

import com.eventmanager.usermanagement.domain.models.User;
import com.eventmanager.usermanagement.domain.models.UserId;
import com.eventmanager.usermanagement.services.UserService;
import com.eventmanager.usermanagement.services.forms.RegisterForm;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Collection;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserResource
{
    private final UserService userService;

    @GetMapping
    public Collection<User> findAll()
    {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable String id)
    {
        return userService.findById(new UserId(id)).orElse(null);
    }

    @GetMapping("/mail/{email}")
    public User findByEmail(@PathVariable String email)
    {
        return userService.findByEmail(email).orElse(null);
    }

    @PostMapping
    public ResponseEntity register(@RequestBody RegisterForm form)
    {
        UserId id;
        try
        {
            id = userService.register(form);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getClass());
        }
        return ResponseEntity.ok().body(id);
    }
}
