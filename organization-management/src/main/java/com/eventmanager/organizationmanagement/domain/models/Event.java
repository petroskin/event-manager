package com.eventmanager.organizationmanagement.domain.models;

import com.eventmanager.organizationmanagement.domain.exceptions.PassedEventException;
import com.eventmanager.organizationmanagement.domain.valueobjects.Address;
import com.eventmanager.organizationmanagement.domain.valueobjects.UserId;
import com.eventmanager.sharedkernel.domain.base.AbstractEntity;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "event_manager_event")
@Getter
public class Event extends AbstractEntity<EventId>
{
    private String name;
    private String description;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "event_street")),
            @AttributeOverride(name = "number", column = @Column(name = "event_number")),
            @AttributeOverride(name = "city", column = @Column(name = "event_city")),
            @AttributeOverride(name = "country", column = @Column(name = "event_country")),
    })
    private Address location;
    private Instant dateTime;
    @ManyToOne(fetch = FetchType.EAGER)
    private Organization organization;
    @ElementCollection
    private Set<UserId> participantsIdsSet = new HashSet<>();

    protected Event()
    {
        super(EventId.randomId(EventId.class));
    }

    public Event(@NonNull String name, @NonNull String description, @NonNull Address location, Instant dateTime, Organization organization)
    {
        super(EventId.randomId(EventId.class));
        this.name = name;
        this.description = description;
        this.location = location;
        if (dateTime.isBefore(Instant.now()))
            throw new IllegalArgumentException("Invalid date specified");
        this.dateTime = dateTime;
        this.organization = organization;
        participantsIdsSet.addAll(organization.getMemberIdsSet());
    }

    public void registerParticipant(@NonNull UserId id) throws PassedEventException
    {
        Objects.requireNonNull(id, "User ID must not be null");
        if (this.getDateTime().isBefore(Instant.now()))
            throw new PassedEventException("Invalid event - this event has passed");
        participantsIdsSet.add(id);
    }

    public void removeParticipant(@NonNull UserId id)
    {
        Objects.requireNonNull(id, "User ID must not be null");
        participantsIdsSet.remove(id);
    }
}
