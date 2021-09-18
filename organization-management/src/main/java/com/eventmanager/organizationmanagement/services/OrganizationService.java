package com.eventmanager.organizationmanagement.services;

import com.eventmanager.organizationmanagement.domain.models.Organization;
import com.eventmanager.organizationmanagement.domain.models.OrganizationId;
import com.eventmanager.organizationmanagement.domain.valueobjects.UserId;
import com.eventmanager.organizationmanagement.services.forms.OrganizationForm;

import java.util.Collection;
import java.util.Optional;

public interface OrganizationService
{
    OrganizationId registerOrganization(OrganizationForm form);
    void deleteOrganization(OrganizationId id, UserId userId);
    Collection<Organization> findAll();
    Optional<Organization> findById(OrganizationId id);
    void join(OrganizationId organizationId, UserId userId);
    void leave(OrganizationId organizationId, UserId userId);
    void fire(OrganizationId organizationId, UserId userId);
    void invite(OrganizationId organizationId, UserId userId);
    void uninvite(OrganizationId organizationId, UserId userId);
}
