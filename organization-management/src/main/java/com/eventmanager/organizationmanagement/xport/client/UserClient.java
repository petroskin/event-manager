package com.eventmanager.organizationmanagement.xport.client;

import com.eventmanager.organizationmanagement.domain.valueobjects.User;
import com.eventmanager.organizationmanagement.domain.valueobjects.UserId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserClient
{
    private final RestTemplate restTemplate;
    private final String serverUrl;

    public UserClient(@Value("${app.user-management.url}") String serverUrl)
    {
        this.serverUrl = serverUrl;
        this.restTemplate = new RestTemplate();
        var requestFactory = new SimpleClientHttpRequestFactory();
        this.restTemplate.setRequestFactory(requestFactory);
    }

    private UriComponentsBuilder uri()
    {
        return UriComponentsBuilder.fromUriString(this.serverUrl);
    }

    public Collection<User> findAll()
    {
        try
        {
            return restTemplate.exchange(
                    uri().path("/api/users").build().toUri(),
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<Collection<User>>() {})
                    .getBody();
        }
        catch (Exception e)
        {
            return Collections.EMPTY_LIST;
        }
    }

    public User findById(UserId id)
    {
        try
        {
            return restTemplate.exchange(
                            uri().path("/api/users/"+id.getId()).build().toUri(),
                            HttpMethod.GET, null,
                            new ParameterizedTypeReference<User>() {})
                    .getBody();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public User findByEmail(String email)
    {
        try
        {
            return restTemplate.exchange(
                            uri().path("/api/users/mail/"+email).build().toUri(),
                            HttpMethod.GET, null,
                            new ParameterizedTypeReference<User>() {})
                    .getBody();
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
