package com.eventmanager.organizationmanagement.services.impl;

import com.eventmanager.organizationmanagement.domain.exceptions.EventIdNotExistException;
import com.eventmanager.organizationmanagement.domain.exceptions.OrganizationIdNotExistException;
import com.eventmanager.organizationmanagement.domain.exceptions.UserIsNotOwnerException;
import com.eventmanager.organizationmanagement.domain.models.Event;
import com.eventmanager.organizationmanagement.domain.models.EventId;
import com.eventmanager.organizationmanagement.domain.models.Organization;
import com.eventmanager.organizationmanagement.domain.models.OrganizationId;
import com.eventmanager.organizationmanagement.domain.repository.EventRepository;
import com.eventmanager.organizationmanagement.domain.valueobjects.UserId;
import com.eventmanager.organizationmanagement.services.EventService;
import com.eventmanager.organizationmanagement.services.forms.EventForm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class EventSericeImpl implements EventService
{

    private final EventRepository eventRepository;
    private final Validator validator;

    @Override
    public EventId registerEvent(EventForm form, UserId userId)
    {
        Objects.requireNonNull(form, "Event must not be null");
        var constraintViolations = validator.validate(form);
        if (constraintViolations.size() > 0)
            throw new ConstraintViolationException("The form is not valid", constraintViolations);
        if (!form.getOrganization().getOwnerId().equals(userId))
            throw new UserIsNotOwnerException();
        Event newEvent = eventRepository.saveAndFlush(toDomainObject(form));
        return newEvent.getId();
    }

    @Override
    public void deleteEvent(EventId id, UserId userId)
    {
        Optional<Event> event = eventRepository.findById(id);
        if (!event.isPresent())
            throw new EventIdNotExistException(id);
        if (!event.get().getOrganization().getOwnerId().equals(userId))
            throw new UserIsNotOwnerException();
        eventRepository.delete(event.get());
    }

    @Override
    public Collection<Event> findAll()
    {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> findById(EventId id)
    {
        return eventRepository.findById(id);
    }

    @Override
    public void registerParticipant(EventId eventId, UserId userId)
    {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventIdNotExistException(eventId));
        event.registerParticipant(userId);
        eventRepository.saveAndFlush(event);
    }

    @Override
    public void removeParticipant(EventId eventId, UserId userId)
    {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventIdNotExistException(eventId));
        event.removeParticipant(userId);
        eventRepository.saveAndFlush(event);
    }

    private Event toDomainObject(EventForm form)
    {
        return new Event(form.getName(), form.getDescription(), form.getLocation(), Instant.now(), form.getOrganization());
    }
}
