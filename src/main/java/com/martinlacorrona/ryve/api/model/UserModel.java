package com.martinlacorrona.ryve.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserModel implements Serializable {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("mail")
    private String mail;

    @JsonProperty("password")
    private String password;

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("regdate")
    private Date regdate;

    @JsonProperty("lastlogin")
    private Date lastlogin;

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", mail='" + mail + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", regdate=" + regdate +
                ", lastlogin=" + lastlogin +
                '}';
    }
}
