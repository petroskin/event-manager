package com.eventmanager.usermanagement.domain.dto;

import com.eventmanager.usermanagement.domain.models.User;
import com.eventmanager.usermanagement.domain.models.UserId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserDetailsDto
{
    private UserId userId;
    private String email;

    public static UserDetailsDto of(User user)
    {
        UserDetailsDto ret = new UserDetailsDto();
        ret.email = user.getEmail();
        ret.userId = user.getId();
        return ret;
    }
}
