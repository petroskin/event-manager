package com.eventmanager.usermanagement.services.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
public class RegisterForm
{
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String email;
    @NotNull
    private String password;
}
