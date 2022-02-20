package com.martinlacorrona.ryve.api.security;

import com.martinlacorrona.ryve.api.config.PasswordEncoderBean;
import com.martinlacorrona.ryve.api.model.UserModel;
import com.martinlacorrona.ryve.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class RyveAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoderBean passwordEncoderBean;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String mail = authentication.getName();
        String passwordWithoutEncode = authentication.getCredentials().toString();

        UserModel userModel = userService.findByMail(mail);
        if (userModel == null) return null;

        String passwordEncoded = userModel.getPassword();

        PasswordEncoder passwordEncoder = passwordEncoderBean.encoder();

        if(passwordEncoder.matches(passwordWithoutEncode, passwordEncoded)) {
            //LOGIN OK, update lastLogin
            userModel.setLastlogin(new Date());
            userService.update(userModel);

            return new UsernamePasswordAuthenticationToken(
                    mail, passwordWithoutEncode, new ArrayList<>());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
