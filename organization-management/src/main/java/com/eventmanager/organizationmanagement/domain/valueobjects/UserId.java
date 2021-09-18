package com.eventmanager.organizationmanagement.domain.valueobjects;

import com.eventmanager.sharedkernel.domain.base.DomainObjectId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
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
