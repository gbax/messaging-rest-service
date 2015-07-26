package ru.gbax.messaging.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.gbax.messaging.entity.User;
import ru.gbax.messaging.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Сервис авторизации
 */

@Component
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    @Autowired
    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String login = authentication.getName();
        String password = (String) authentication.getCredentials();
        List<User> usersByLogin = userService.getUsersByLogin(login);
        User user;
        if (usersByLogin.size() != 1) {
            throw new BadCredentialsException("Пользователь не найден в системе");
        } else {
            user = usersByLogin.get(0);
        }

        if (!StringUtils.equals(password, user.getPassword())) {
            throw new BadCredentialsException("Неверный пароль");
        }
        final String role = user.getRole();
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
