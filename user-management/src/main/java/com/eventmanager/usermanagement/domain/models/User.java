package com.eventmanager.usermanagement.domain.models;

import com.eventmanager.sharedkernel.domain.base.AbstractEntity;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "event_manager_user")
@Getter
public class User extends AbstractEntity<UserId>
{
    private String name;
    private String surname;
    private String email;
    private String password;

    protected User()
    {
        super(UserId.randomId(UserId.class));
    }

    public User(String name, String surname, String email, String password)
    {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
}
