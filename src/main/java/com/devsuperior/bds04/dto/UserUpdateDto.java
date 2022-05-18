package com.devsuperior.bds04.dto;

import com.devsuperior.bds04.entities.User;

import java.io.Serializable;


public class UserUpdateDto extends UserDto implements Serializable {
    private static final long serialVersionUID = -481795253773365696L;


    public UserUpdateDto(User entity) {
        super(entity);
    }
}
