package com.eventmanager.organizationmanagement.services.forms;

import com.eventmanager.organizationmanagement.domain.valueobjects.UserId;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrganizationForm
{
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private UserId ownerId;
}
