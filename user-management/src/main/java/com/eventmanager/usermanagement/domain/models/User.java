package com.eventmanager.usermanagement.domain.models;

import com.eventmanager.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "event_manager_user")
public class User extends AbstractEntity<UserId>
{
    private String name;
    private String surname;
    private String email;
    private String password;
}
