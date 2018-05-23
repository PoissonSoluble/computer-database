package com.excilys.cdb.web.config;

import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.excilys.cdb.service.IUserAuthenticationService;

@Component
final class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private IUserAuthenticationService auth;

    public TokenAuthenticationProvider(IUserAuthenticationService pAuth) {
        auth = pAuth;
    }

    @Override
    protected void additionalAuthenticationChecks(final UserDetails user,
            final UsernamePasswordAuthenticationToken auth) {
        // Nothing to do
    }

    @Override
    protected UserDetails retrieveUser(final String username,
            final UsernamePasswordAuthenticationToken authentication) {
        final Object token = authentication.getCredentials();
        return Optional.ofNullable(token).map(String::valueOf).flatMap(auth::findByToken).orElseThrow(
                () -> new UsernameNotFoundException("Cannot find user with authentication token=" + token));
    }
}