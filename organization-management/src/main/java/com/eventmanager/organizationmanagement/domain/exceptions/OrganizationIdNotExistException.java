package com.eventmanager.organizationmanagement.domain.exceptions;

import com.eventmanager.organizationmanagement.domain.models.OrganizationId;

public class OrganizationIdNotExistException extends RuntimeException
{
    public OrganizationIdNotExistException(OrganizationId id)
    {
        super("Organization id " + id.getId() + " does not exist.");
    }
}
