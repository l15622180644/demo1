package com.lzk.demosecurity.core.provider;


import com.lzk.demosecurity.core.SecurityUserInfo;
import com.lzk.demosecurity.core.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@AllArgsConstructor
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private SecurityService securityService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SecurityUserInfo userDetails = (SecurityUserInfo)securityService.loadUserByUsername(authentication.getPrincipal().toString());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
