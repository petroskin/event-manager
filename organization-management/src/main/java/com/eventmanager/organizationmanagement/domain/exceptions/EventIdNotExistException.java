package com.eventmanager.organizationmanagement.domain.exceptions;

import com.eventmanager.organizationmanagement.domain.models.EventId;

public class EventIdNotExistException extends RuntimeException
{
    public EventIdNotExistException(EventId id)
    {
        super("Event id " + id.getId() + " does not exist.");
    }
}
