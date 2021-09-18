package com.eventmanager.sharedkernel.domain.events.organizations;

import com.eventmanager.sharedkernel.domain.config.TopicHolder;
import com.eventmanager.sharedkernel.domain.events.DomainEvent;
import lombok.Getter;

@Getter
public class OrganizationDeleted extends DomainEvent
{
    private String userId;

    public OrganizationDeleted()
    {
        super(TopicHolder.TOPIC_ORGANIZATION_DELETED);
    }

    public OrganizationDeleted(String userId)
    {
        super(TopicHolder.TOPIC_ORGANIZATION_DELETED);
        this.userId = userId;
    }
}
