package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.services.CurrentUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {

    @Override
    public String getCurrentUser() {

        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder
                        .getContext().getAuthentication().getPrincipal();
        System.out.println(user.getUsername());
        System.out.println(user.getAuthorities());
        return user.getUsername();
    }
}