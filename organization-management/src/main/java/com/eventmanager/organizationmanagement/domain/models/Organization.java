package com.eventmanager.organizationmanagement.domain.models;

import com.eventmanager.organizationmanagement.domain.valueobjects.UserId;
import com.eventmanager.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "event_manager_organization")
public class Organization extends AbstractEntity<OrganizationId>
{
    private String name;
    private String description;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "owner_id", nullable = false))
    private UserId ownerId;
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<UserId> memberIdsSet;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Event> eventsSet;
}
