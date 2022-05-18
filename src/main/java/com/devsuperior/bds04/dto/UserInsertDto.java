package com.devsuperior.bds04.dto;

import com.devsuperior.bds04.entities.User;
import com.devsuperior.bds04.services.validation.UserInsertValid;

import java.io.Serializable;


@UserInsertValid
public class UserInsertDto extends UserDto implements Serializable {
    private static final long serialVersionUID = -481795253773365696L;

    private String password;

    public UserInsertDto(User entity) {
        super(entity);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
