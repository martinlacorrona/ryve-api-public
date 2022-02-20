package com.martinlacorrona.ryve.api.service.impl;

import com.martinlacorrona.ryve.api.config.PasswordEncoderBean;
import com.martinlacorrona.ryve.api.dao.NotificationTokenDao;
import com.martinlacorrona.ryve.api.dao.UserDao;
import com.martinlacorrona.ryve.api.entities.NotificationTokenEntity;
import com.martinlacorrona.ryve.api.entities.UserEntity;
import com.martinlacorrona.ryve.api.exception.ErrorMessage;
import com.martinlacorrona.ryve.api.exception.RestException;
import com.martinlacorrona.ryve.api.model.UserModel;
import com.martinlacorrona.ryve.api.model.UserModelWithOutPassword;
import com.martinlacorrona.ryve.api.security.DataUserSecurity;
import com.martinlacorrona.ryve.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DataUserSecurity dataUserSecurity;

    //Mapper
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private PasswordEncoderBean passwordEncoderBean;

    @Override
    public UserModel findByMail(String mail) {
        UserEntity userEntity = userDao.findByMail(mail);
        if(userEntity == null)
            return null;
        return modelMapper.map(userEntity, UserModel.class);
    }

    @Override
    @Transactional
    public void deleteByMail(String mail) {
        userDao.deleteByMail(mail);
    }

    @Override
    public UserModel register(UserModel userModel) {
        if(userDao.findByMail(userModel.getMail()) != null) {
            throw new RestException(HttpStatus.BAD_REQUEST, ErrorMessage.ALREADY_REGISTER_USER_WITH_MAIL, new String[]{userModel.getMail()});
        }
        userModel.setPassword(passwordEncoderBean.encoder().encode(userModel.getPassword()));

        UserEntity userEntity = modelMapper.map(userModel, UserEntity.class);
        userEntity = userDao.save(userEntity);

        return modelMapper.map(userEntity, UserModel.class);
    }

    @Override
    public UserModelWithOutPassword getUserUserModelWithOutPassword() {
        return dataUserSecurity.getUserWithOutPassword();
    }

    @Override
    public UserModel update(UserModel userModel) {
        UserEntity userEntity = userDao.findByMail(userModel.getMail());

        userEntity.setName(userModel.getName());
        userEntity.setSurname(userModel.getSurname());
        userEntity.setLastlogin(userModel.getLastlogin());
        userEntity.setMail(userModel.getMail());

        return modelMapper.map(userDao.save(userEntity), UserModel.class);
    }

    @Override
    public UserModelWithOutPassword updateUser(UserModel userModel) {
        UserEntity userEntity = userDao.findByMail(userModel.getMail());
        if(!dataUserSecurity.getUser().getMail().equals(userModel.getMail())) {
            throw new RestException(HttpStatus.UNAUTHORIZED, ErrorMessage.CHANGE_YOUR_PROFILE, new String[]{userModel.getMail()});
        }
        userEntity.setName(userModel.getName());
        userEntity.setSurname(userModel.getSurname());
        if(userModel.getPassword() != null) //Contraseña solo cambiar si se pasa
            userEntity.setPassword(passwordEncoderBean.encoder().encode(userModel.getPassword()));

        userEntity = userDao.save(userEntity);

        return modelMapper.map(userEntity, UserModelWithOutPassword.class);
    }
}
