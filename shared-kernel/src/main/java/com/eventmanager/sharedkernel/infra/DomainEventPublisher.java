package com.eventmanager.sharedkernel.infra;

import com.eventmanager.sharedkernel.domain.events.DomainEvent;

public interface DomainEventPublisher
{
    void publish(DomainEvent event);
}
