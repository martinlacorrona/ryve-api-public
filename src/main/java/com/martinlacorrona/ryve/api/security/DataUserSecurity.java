package com.martinlacorrona.ryve.api.security;

import com.martinlacorrona.ryve.api.dao.UserDao;
import com.martinlacorrona.ryve.api.entities.UserEntity;
import com.martinlacorrona.ryve.api.model.UserModel;
import com.martinlacorrona.ryve.api.model.UserModelWithOutPassword;
import com.martinlacorrona.ryve.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DataUserSecurity {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    private final ModelMapper mapper = new ModelMapper();

    public UserModel getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        } else {
            return userService.findByMail((String) authentication.getPrincipal());
        }
    }

    public UserEntity getUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        } else {
            return userDao.findByMail((String) authentication.getPrincipal());
        }
    }

    public UserModelWithOutPassword getUserWithOutPassword() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        } else {
            return mapper
                    .map(userService.findByMail((String) authentication.getPrincipal()), UserModelWithOutPassword.class);
        }
    }
}
