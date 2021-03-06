package com.martinlacorrona.ryve.api.dao;

import com.martinlacorrona.ryve.api.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    UserEntity findByMail(String mail);
    void deleteByMail(String mail);

}
