package com.eventmanager.usermanagement.xport.events;

import com.eventmanager.sharedkernel.domain.config.TopicHolder;
import com.eventmanager.sharedkernel.domain.events.DomainEvent;
import com.eventmanager.sharedkernel.domain.events.organizations.OrganizationCreated;
import com.eventmanager.usermanagement.domain.models.UserId;
import com.eventmanager.usermanagement.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserEventListener
{
    private final UserService userService;

    @KafkaListener(topics = TopicHolder.TOPIC_ORGANIZATION_CREATED, groupId = "userManagement")
    public void consumeOrganizationCreatedEvent(String jsonMessage)
    {
        try
        {
            OrganizationCreated event = DomainEvent.fromJson(jsonMessage, OrganizationCreated.class);
            userService.joinedOrganization(new UserId(event.getUserId()));
        }
        catch (Exception e)
        {}
    }

    @KafkaListener(topics = TopicHolder.TOPIC_ORGANIZATION_DELETED, groupId = "userManagement")
    public void consumeOrganizationDeletedEvent(String jsonMessage)
    {
        try
        {
            OrganizationCreated event = DomainEvent.fromJson(jsonMessage, OrganizationCreated.class);
            userService.leftOrganization(new UserId(event.getUserId()));
        }
        catch (Exception e)
        {}
    }

    @KafkaListener(topics = TopicHolder.TOPIC_USER_FIRED_FROM_ORGANIZATION, groupId = "userManagement")
    public void consumeUserFiredFromOrganizationEvent(String jsonMessage)
    {
        try
        {
            OrganizationCreated event = DomainEvent.fromJson(jsonMessage, OrganizationCreated.class);
            userService.leftOrganization(new UserId(event.getUserId()));
        }
        catch (Exception e)
        {}
    }

    @KafkaListener(topics = TopicHolder.TOPIC_USER_JOINED_ORGANIZATION, groupId = "userManagement")
    public void consumeUserJoinedOrganizationEvent(String jsonMessage)
    {
        try
        {
            OrganizationCreated event = DomainEvent.fromJson(jsonMessage, OrganizationCreated.class);
            userService.joinedOrganization(new UserId(event.getUserId()));
        }
        catch (Exception e)
        {}
    }

    @KafkaListener(topics = TopicHolder.TOPIC_USER_LEFT_ORGANIZATION, groupId = "userManagement")
    public void consumeUserLeftOrganizationEvent(String jsonMessage)
    {
        try
        {
            OrganizationCreated event = DomainEvent.fromJson(jsonMessage, OrganizationCreated.class);
            userService.leftOrganization(new UserId(event.getUserId()));
        }
        catch (Exception e)
        {}
    }
}
