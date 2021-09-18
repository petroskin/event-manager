package com.eventmanager.sharedkernel.domain.events.organizations;

import com.eventmanager.sharedkernel.domain.config.TopicHolder;
import com.eventmanager.sharedkernel.domain.events.DomainEvent;
import lombok.Getter;

@Getter
public class OrganizationCreated extends DomainEvent
{
    private String userId;

    public OrganizationCreated()
    {
        super(TopicHolder.TOPIC_ORGANIZATION_CREATED);
    }

    public OrganizationCreated(String userId)
    {
        super(TopicHolder.TOPIC_ORGANIZATION_CREATED);
        this.userId = userId;
    }
}
