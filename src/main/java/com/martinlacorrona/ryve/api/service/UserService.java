package com.martinlacorrona.ryve.api.service;

import com.martinlacorrona.ryve.api.model.UserModel;
import com.martinlacorrona.ryve.api.model.UserModelWithOutPassword;

public interface UserService {
    /**
     * Busca usuario por mail
     * @param mail mail del usuario
     * @return el modelo del usuario
     */
    UserModel findByMail(String mail);

    /**
     * Borra usuario por email
     * @param mail mail del que se desea borrar
     */
    void deleteByMail(String mail);

    /**
     * Registra usuario pasanbdole un modelo
     * @param userModel el usuario a registrar
     * @return el usuario registrado
     */
    UserModel register(UserModel userModel);

    /**
     * Devuelve el usuario sin password, para que no haiga errores de seguridad
     * @return el usuario sin contraseña
     */
    UserModelWithOutPassword getUserUserModelWithOutPassword();

    /**
     * Actualiza al usuario pero no devuelve cotnraseña en el mismo
     * @param userModel el que se actualiza
     * @return el usuario actualziado
     */
    UserModelWithOutPassword updateUser(UserModel userModel);

    /**
     * Actualiza al usuario
     * @param userModel el que se actualiza
     * @return el usuario actualziado
     */
    UserModel update(UserModel userModel);
}
