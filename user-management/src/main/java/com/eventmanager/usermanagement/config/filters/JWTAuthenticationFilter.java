package com.eventmanager.usermanagement.config.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.eventmanager.usermanagement.config.JWTAuthConstants;
import com.eventmanager.usermanagement.domain.dto.UserDetailsDto;
import com.eventmanager.usermanagement.domain.exceptions.PasswordsDoNotMatchException;
import com.eventmanager.usermanagement.domain.models.User;
import com.eventmanager.usermanagement.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@AllArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
        org.springframework.security.core.userdetails.User creds = null;
        try
        {
            creds = new ObjectMapper().readValue(request.getInputStream(), org.springframework.security.core.userdetails.User.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if (creds == null)
            throw new UsernameNotFoundException("Invalid credentials");
        UserDetails userDetails = userService.loadUserByUsername(creds.getUsername());
        if (!passwordEncoder.matches(creds.getPassword(), userDetails.getPassword()))
            throw new PasswordsDoNotMatchException("Invalid credentials");
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException
    {
        User userDetails = (User) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject(new ObjectMapper().writeValueAsString(UserDetailsDto.of(userDetails)))
                .withExpiresAt(new Date(System.currentTimeMillis() + JWTAuthConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(JWTAuthConstants.SECRET));
        response.addHeader(JWTAuthConstants.HEADER_STRING, JWTAuthConstants.TOKEN_PREFIX + token);
        response.getWriter().append(token);
    }
}
