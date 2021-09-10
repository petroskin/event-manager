package com.eventmanager.organizationmanagement.domain.models;

import com.eventmanager.organizationmanagement.domain.valueobjects.Address;
import com.eventmanager.organizationmanagement.domain.valueobjects.UserId;
import com.eventmanager.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "event_manager_event")
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
    private Set<UserId> participantsIdsSet;
}
