package com.eventmanager.organizationmanagement.domain.valueobjects;

import com.eventmanager.sharedkernel.domain.base.ValueObject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class User implements ValueObject
{
    private final UserId id;
    private final String name;
    private final String surname;
    private final String email;
    private final String password;

    private User()
    {
        this.id = UserId.randomId(UserId.class);
        name = surname = email = password = null;
    }

    @JsonCreator
    public User(@JsonProperty("id") UserId id,
                @JsonProperty("name") String name,
                @JsonProperty("surname") String surname,
                @JsonProperty("email") String email,
                @JsonProperty("password") String password)
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
}
