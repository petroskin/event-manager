package com.eventmanager.organizationmanagement.services.forms;

import com.eventmanager.organizationmanagement.domain.models.Organization;
import com.eventmanager.organizationmanagement.domain.valueobjects.Address;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class EventForm
{
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Address location;
    @NotNull
    private Organization organization;
}
