package ru.gbax.messaging.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.gbax.messaging.entity.User;

/**
 * Created by abayanov
 * Date: 18.08.14
 */
@Service
public class SecurityService {

    public User getSecurityPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ( authentication == null ) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        return principal instanceof User ? (User) principal : null;
    }

}