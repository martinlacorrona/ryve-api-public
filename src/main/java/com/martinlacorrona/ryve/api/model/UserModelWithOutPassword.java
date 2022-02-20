package com.martinlacorrona.ryve.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserModelWithOutPassword implements Serializable {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("mail")
    private String mail;

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("regdate")
    private Date regdate;

    @JsonProperty("lastlogin")
    private Date lastlogin;
}
