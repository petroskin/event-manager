package com.eventmanager.organizationmanagement.domain.repository;

import com.eventmanager.organizationmanagement.domain.models.Organization;
import com.eventmanager.organizationmanagement.domain.models.OrganizationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, OrganizationId>
{
}
