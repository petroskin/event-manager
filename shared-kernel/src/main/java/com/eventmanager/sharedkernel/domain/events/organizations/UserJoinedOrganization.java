package com.eventmanager.sharedkernel.domain.events.organizations;

import com.eventmanager.sharedkernel.domain.config.TopicHolder;
import com.eventmanager.sharedkernel.domain.events.DomainEvent;
import lombok.Getter;

@Getter
public class UserJoinedOrganization extends DomainEvent
{
    private String userId;

    public UserJoinedOrganization()
    {
        super(TopicHolder.TOPIC_USER_JOINED_ORGANIZATION);
    }

    public UserJoinedOrganization(String userId)
    {
        super(TopicHolder.TOPIC_USER_JOINED_ORGANIZATION);
        this.userId = userId;
    }
}
