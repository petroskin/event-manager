package com.eventmanager.organizationmanagement.domain.valueobjects;

import com.eventmanager.sharedkernel.domain.base.DomainObjectId;
import lombok.NonNull;

import javax.persistence.Embeddable;

@Embeddable
public class UserId extends DomainObjectId
{
    protected UserId()
    {
        super(UserId.randomId(UserId.class).getId());
    }

    public UserId(@NonNull String uuid)
    {
        super(uuid);
    }
}
