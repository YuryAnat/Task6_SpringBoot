package com.yuryanat.task6.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Service
public class SuccessHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        if (authorities.stream().filter(r -> r.getAuthority().equals("ADMIN")).count() > 0) {
            redirectStrategy.sendRedirect(request, response, "/admin");
        } else if (authorities.stream().filter(r -> r.getAuthority().equals("USER")).count() > 0) {
            redirectStrategy.sendRedirect(request, response, "/user");
        } else {
            throw new IllegalStateException();
        }
    }
}
