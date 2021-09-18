package com.eventmanager.organizationmanagement.services.impl;

import com.eventmanager.organizationmanagement.domain.exceptions.OrganizationIdNotExistException;
import com.eventmanager.organizationmanagement.domain.exceptions.UserIsNotOwnerException;
import com.eventmanager.organizationmanagement.domain.exceptions.UserNotInvitedException;
import com.eventmanager.organizationmanagement.domain.models.Organization;
import com.eventmanager.organizationmanagement.domain.models.OrganizationId;
import com.eventmanager.organizationmanagement.domain.repository.OrganizationRepository;
import com.eventmanager.organizationmanagement.domain.valueobjects.UserId;
import com.eventmanager.organizationmanagement.services.OrganizationService;
import com.eventmanager.organizationmanagement.services.forms.OrganizationForm;
import com.eventmanager.sharedkernel.domain.events.organizations.*;
import com.eventmanager.sharedkernel.infra.DomainEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService
{
    private final OrganizationRepository organizationRepository;
    private final DomainEventPublisher domainEventPublisher;
    private final Validator validator;

    @Override
    public OrganizationId registerOrganization(OrganizationForm form)
    {
        Objects.requireNonNull(form, "Organization must not be null");
        var constraintViolations = validator.validate(form);
        if (constraintViolations.size() > 0)
            throw new ConstraintViolationException("The form is not valid", constraintViolations);
        Organization newOrganization = organizationRepository.saveAndFlush(toDomainObject(form));
        domainEventPublisher.publish(new OrganizationCreated(form.getOwnerId().getId()));
        return newOrganization.getId();
    }

    @Override
    public void deleteOrganization(OrganizationId id, UserId userId)
    {
        Optional<Organization> organization = organizationRepository.findById(id);
        if (!organization.isPresent())
            throw new OrganizationIdNotExistException(id);
        if (organization.get().getOwnerId() != userId)
            throw new UserIsNotOwnerException();
        organizationRepository.delete(organization.get());
        domainEventPublisher.publish(new OrganizationDeleted(organization.get().getOwnerId().getId()));
    }

    @Override
    public Collection<Organization> findAll()
    {
        return organizationRepository.findAll();
    }

    @Override
    public Optional<Organization> findById(OrganizationId id)
    {
        return organizationRepository.findById(id);
    }

    @Override
    public void join(OrganizationId organizationId, UserId userId)
    {
        Organization org = organizationRepository.findById(organizationId).orElseThrow(() -> new OrganizationIdNotExistException(organizationId));
        org.addMember(userId);
        if (!org.isInvited(userId))
            throw new UserNotInvitedException();
        organizationRepository.saveAndFlush(org);
        domainEventPublisher.publish(new UserJoinedOrganization(userId.getId()));
    }

    @Override
    public void leave(OrganizationId organizationId, UserId userId)
    {
        Organization org = organizationRepository.findById(organizationId).orElseThrow(() -> new OrganizationIdNotExistException(organizationId));
        org.removeMember(userId);
        organizationRepository.saveAndFlush(org);
        domainEventPublisher.publish(new UserLeftOrganization(userId.getId()));
    }

    @Override
    public void fire(OrganizationId organizationId, UserId userId)
    {
        Organization org = organizationRepository.findById(organizationId).orElseThrow(() -> new OrganizationIdNotExistException(organizationId));
        org.removeMember(userId);
        organizationRepository.saveAndFlush(org);
        domainEventPublisher.publish(new UserFiredFromOrganization(userId.getId()));
    }

    @Override
    public void invite(OrganizationId organizationId, UserId userId)
    {
        Organization org = organizationRepository.findById(organizationId).orElseThrow(() -> new OrganizationIdNotExistException(organizationId));
        org.addInvite(userId);
        organizationRepository.saveAndFlush(org);
    }

    @Override
    public void uninvite(OrganizationId organizationId, UserId userId)
    {
        Organization org = organizationRepository.findById(organizationId).orElseThrow(() -> new OrganizationIdNotExistException(organizationId));
        org.removeInvite(userId);
        organizationRepository.saveAndFlush(org);
    }

    private Organization toDomainObject(OrganizationForm form)
    {
        return new Organization(form.getName(), form.getDescription(), form.getOwnerId());
    }
}
