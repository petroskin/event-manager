package com.eventmanager.organizationmanagement.xport.rest;

import com.eventmanager.organizationmanagement.domain.exceptions.OrganizationIdNotExistException;
import com.eventmanager.organizationmanagement.domain.exceptions.UserIsNotOwnerException;
import com.eventmanager.organizationmanagement.domain.models.Organization;
import com.eventmanager.organizationmanagement.domain.models.OrganizationId;
import com.eventmanager.organizationmanagement.domain.valueobjects.UserId;
import com.eventmanager.organizationmanagement.services.OrganizationService;
import com.eventmanager.organizationmanagement.services.forms.OrganizationForm;
import com.eventmanager.organizationmanagement.xport.client.UserClient;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/organizations")
@AllArgsConstructor
public class OrganizationResource
{
    private final OrganizationService organizationService;
    private final UserClient userClient;

    @GetMapping
    public Collection<Organization> findAll()
    {
        return organizationService.findAll();
    }

    @GetMapping("/{id}")
    public Organization findById(@PathVariable String id)
    {
        return organizationService.findById(new OrganizationId(id)).orElse(null);
    }

    @PostMapping
    public ResponseEntity registerOrganization(@RequestBody OrganizationForm form, HttpServletRequest req)
    {
        form.setOwnerId(userClient.findByEmail(req.getRemoteUser()).getId());
        OrganizationId id;
        try
        {
            id = organizationService.registerOrganization(form);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getClass());
        }
        return ResponseEntity.ok().body(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteOrganization(@PathVariable String id, HttpServletRequest req)
    {
        UserId userId = userClient.findByEmail(req.getRemoteUser()).getId();
        try
        {
            organizationService.deleteOrganization(new OrganizationId(id), userId);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getClass());
        }
        return ResponseEntity.ok().body(new OrganizationId(id));
    }

    @PostMapping("/join")
    public ResponseEntity join(@RequestParam String id, @RequestParam String userId)
    {
        try
        {
            organizationService.join(new OrganizationId(id), new UserId(userId));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getClass());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/leave")
    public ResponseEntity leave(@RequestParam String id, @RequestParam String userId)
    {
        try
        {
            organizationService.leave(new OrganizationId(id), new UserId(userId));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getClass());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/fire")
    public ResponseEntity fire(@RequestParam String id, @RequestParam String userId)
    {
        try
        {
            organizationService.fire(new OrganizationId(id), new UserId(userId));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getClass());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/invite")
    public ResponseEntity invite(@RequestParam String id, @RequestParam String userId, HttpServletRequest req)
    {
        Optional<Organization> org = organizationService.findById(new OrganizationId(id));
        if (!org.isPresent())
            throw new OrganizationIdNotExistException(new OrganizationId(id));
        if (!Objects.equals(req.getRemoteUser(), userClient.findById(org.get().getOwnerId()).getEmail()))
            throw new UserIsNotOwnerException();
        try
        {
            organizationService.invite(new OrganizationId(id), new UserId(userId));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getClass());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/uninvite")
    public ResponseEntity uninvite(@RequestParam String id, @RequestParam String userId, HttpServletRequest req)
    {
        Optional<Organization> org = organizationService.findById(new OrganizationId(id));
        if (!org.isPresent())
            throw new OrganizationIdNotExistException(new OrganizationId(id));
        if (!Objects.equals(req.getRemoteUser(), userClient.findById(org.get().getOwnerId()).getEmail()))
            throw new UserIsNotOwnerException();
        try
        {
            organizationService.uninvite(new OrganizationId(id), new UserId(userId));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getClass());
        }
        return ResponseEntity.ok().build();
    }
}
