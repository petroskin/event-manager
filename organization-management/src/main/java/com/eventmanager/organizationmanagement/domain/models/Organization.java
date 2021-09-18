package com.eventmanager.organizationmanagement.domain.models;

import com.eventmanager.organizationmanagement.domain.exceptions.MemberOfOrganizationException;
import com.eventmanager.organizationmanagement.domain.valueobjects.UserId;
import com.eventmanager.sharedkernel.domain.base.AbstractEntity;
import lombok.Getter;
import lombok.NonNull;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "event_manager_organization")
@Getter
public class Organization extends AbstractEntity<OrganizationId>
{
    private String name;
    private String description;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "owner_id", nullable = false))
    private UserId ownerId;
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<UserId> memberIdsSet = new HashSet<>();
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<UserId> invitedIdsSet = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "organization")
    private Set<Event> eventsSet = new HashSet<>();

    protected Organization()
    {
        super(OrganizationId.randomId(OrganizationId.class));
    }

    public Organization(@NonNull String name, @NonNull String description, @NonNull UserId ownerId)
    {
        super(OrganizationId.randomId(OrganizationId.class));
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
    }

    public void addMember(@NonNull UserId id) throws MemberOfOrganizationException
    {
        Objects.requireNonNull(id, "User ID must not be null");
        if (memberIdsSet.contains(id))
            throw new MemberOfOrganizationException("User is already a member of the organization");
        memberIdsSet.add(id);
        invitedIdsSet.remove(id);
    }

    public void removeMember(@NonNull UserId id)
    {
        Objects.requireNonNull(id, "User ID must not be null");
        memberIdsSet.remove(id);
    }

    public void addInvite(@NonNull UserId id) throws MemberOfOrganizationException
    {
        Objects.requireNonNull(id, "User ID must not be null");
        if (memberIdsSet.contains(id))
            throw new MemberOfOrganizationException("User is already a member of the organization");
        invitedIdsSet.add(id);
    }

    public void removeInvite(@NonNull UserId id)
    {
        Objects.requireNonNull(id, "User ID must not be null");
        invitedIdsSet.remove(id);
    }

    public boolean isMember(@NonNull UserId id)
    {
        Objects.requireNonNull(id, "User ID must not be null");
        return memberIdsSet.contains(id);
    }

    public boolean isInvited(@NonNull UserId id)
    {
        Objects.requireNonNull(id, "User ID must not be null");
        return invitedIdsSet.contains(id);
    }
}
