package com.eventmanager.sharedkernel.domain.events.organizations;

import com.eventmanager.sharedkernel.domain.config.TopicHolder;
import com.eventmanager.sharedkernel.domain.events.DomainEvent;
import lombok.Getter;

@Getter
public class UserLeftOrganization extends DomainEvent
{
    private String userId;

    public UserLeftOrganization()
    {
        super(TopicHolder.TOPIC_USER_LEFT_ORGANIZATION);
    }

    public UserLeftOrganization(String userId)
    {
        super(TopicHolder.TOPIC_USER_LEFT_ORGANIZATION);
        this.userId = userId;
    }
}
