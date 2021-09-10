package com.eventmanager.organizationmanagement.services.impl;

import com.eventmanager.organizationmanagement.domain.exceptions.OrganizationIdNotExistException;
import com.eventmanager.organizationmanagement.domain.models.Organization;
import com.eventmanager.organizationmanagement.domain.models.OrganizationId;
import com.eventmanager.organizationmanagement.domain.repository.OrganizationRepository;
import com.eventmanager.organizationmanagement.domain.valueobjects.UserId;
import com.eventmanager.organizationmanagement.services.OrganizationService;
import com.eventmanager.organizationmanagement.services.forms.OrganizationForm;
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
    private final Validator validator;

    @Override
    public OrganizationId registerOrganization(OrganizationForm form)
    {
        Objects.requireNonNull(form, "Organization must not be null");
        var constraintViolations = validator.validate(form);
        if (constraintViolations.size() > 0)
            throw new ConstraintViolationException("The form is not valid", constraintViolations);
        Organization newOrganization = organizationRepository.saveAndFlush(toDomainObject(form));
        return newOrganization.getId();
    }

    @Override
    public void deleteOrganization(OrganizationId id)
    {
        Optional<Organization> organization = organizationRepository.findById(id);
        organization.ifPresent(organizationRepository::delete);
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
        organizationRepository.saveAndFlush(org);
    }

    @Override
    public void leave(OrganizationId organizationId, UserId userId)
    {
        Organization org = organizationRepository.findById(organizationId).orElseThrow(() -> new OrganizationIdNotExistException(organizationId));
        org.removeMember(userId);
        organizationRepository.saveAndFlush(org);
    }

    @Override
    public void fire(OrganizationId organizationId, UserId userId)
    {
        Organization org = organizationRepository.findById(organizationId).orElseThrow(() -> new OrganizationIdNotExistException(organizationId));
        org.removeMember(userId);
        organizationRepository.saveAndFlush(org);
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
