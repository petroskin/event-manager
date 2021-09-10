package com.eventmanager.organizationmanagement.domain.models;

import com.eventmanager.sharedkernel.domain.base.DomainObjectId;

public class OrganizationId extends DomainObjectId
{
    private OrganizationId()
    {
        super(OrganizationId.randomId(OrganizationId.class).getId());
    }

    public OrganizationId(String uuid)
    {
        super(uuid);
    }
}
