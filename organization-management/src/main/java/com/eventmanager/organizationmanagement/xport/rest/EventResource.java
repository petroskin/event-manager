package com.eventmanager.organizationmanagement.xport.rest;

import com.eventmanager.organizationmanagement.domain.models.Event;
import com.eventmanager.organizationmanagement.domain.models.EventId;
import com.eventmanager.organizationmanagement.domain.valueobjects.UserId;
import com.eventmanager.organizationmanagement.services.EventService;
import com.eventmanager.organizationmanagement.services.forms.EventForm;
import com.eventmanager.organizationmanagement.xport.client.UserClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequestMapping("/api/events")
@AllArgsConstructor
public class EventResource
{
    private final EventService eventService;
    private final UserClient userClient;

    @GetMapping
    public Collection<Event> findAll()
    {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public Event findById(@PathVariable String id)
    {
        return eventService.findById(new EventId(id)).orElse(null);
    }

    @PostMapping
    public ResponseEntity registerEvent(@RequestBody EventForm form, HttpServletRequest req)
    {
        UserId userId = userClient.findByEmail(req.getRemoteUser()).getId();
        EventId id;
        try
        {
            id = eventService.registerEvent(form, userId);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getClass());
        }
        return ResponseEntity.ok().body(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEvent(@PathVariable String id, HttpServletRequest req)
    {
        UserId userId = userClient.findByEmail(req.getRemoteUser()).getId();
        try
        {
            eventService.deleteEvent(new EventId(id), userId);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getClass());
        }
        return ResponseEntity.ok().body(new EventId(id));
    }

    @PostMapping("/participant")
    public ResponseEntity registerParticipant(@RequestParam String id, @RequestParam String userId)
    {
        try
        {
            eventService.registerParticipant(new EventId(id), new UserId(userId));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getClass());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/participant")
    public ResponseEntity removeParticipant(@RequestParam String id, @RequestParam String userId)
    {
        try
        {
            eventService.removeParticipant(new EventId(id), new UserId(userId));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getClass());
        }
        return ResponseEntity.ok().build();
    }
}
