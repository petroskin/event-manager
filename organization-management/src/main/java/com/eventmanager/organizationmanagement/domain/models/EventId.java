package com.eventmanager.organizationmanagement.domain.models;

import com.eventmanager.sharedkernel.domain.base.DomainObjectId;

public class EventId extends DomainObjectId
{
    private EventId()
    {
        super(EventId.randomId(EventId.class).getId());
    }

    public EventId(String uuid)
    {
        super(uuid);
    }
}
