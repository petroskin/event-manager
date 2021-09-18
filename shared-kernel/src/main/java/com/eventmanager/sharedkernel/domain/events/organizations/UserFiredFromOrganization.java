package com.eventmanager.sharedkernel.domain.events.organizations;

import com.eventmanager.sharedkernel.domain.config.TopicHolder;
import com.eventmanager.sharedkernel.domain.events.DomainEvent;
import lombok.Getter;

@Getter
public class UserFiredFromOrganization extends DomainEvent
{
    private String userId;

    public UserFiredFromOrganization()
    {
        super(TopicHolder.TOPIC_USER_FIRED_FROM_ORGANIZATION);
    }

    public UserFiredFromOrganization(String userId)
    {
        super(TopicHolder.TOPIC_USER_FIRED_FROM_ORGANIZATION);
        this.userId = userId;
    }
}
