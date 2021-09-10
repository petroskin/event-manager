package com.eventmanager.organizationmanagement.services;

import com.eventmanager.organizationmanagement.domain.models.Event;
import com.eventmanager.organizationmanagement.domain.models.EventId;
import com.eventmanager.organizationmanagement.domain.valueobjects.UserId;
import com.eventmanager.organizationmanagement.services.forms.EventForm;

import java.util.Collection;
import java.util.Optional;

public interface EventService
{
    EventId registerEvent(EventForm form);
    void deleteEvent(EventId id);
    Collection<Event> findAll();
    Optional<Event> findById(EventId id);
    void registerParticipant(EventId eventId, UserId userId);
    void removeParticipant(EventId eventId, UserId userId);
}
