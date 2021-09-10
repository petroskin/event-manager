package com.eventmanager.organizationmanagement.domain.repository;

import com.eventmanager.organizationmanagement.domain.models.Event;
import com.eventmanager.organizationmanagement.domain.models.EventId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, EventId>
{
}
