package com.eventmanager.usermanagement.domain.models;

import com.eventmanager.sharedkernel.domain.base.DomainObjectId;
import lombok.NonNull;

public class UserId extends DomainObjectId
{
    private UserId()
    {
        super(UserId.randomId(UserId.class).getId());
    }

    public UserId(@NonNull String uuid)
    {
        super(uuid);
    }
}
