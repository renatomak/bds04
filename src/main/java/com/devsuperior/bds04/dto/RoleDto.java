package com.devsuperior.bds04.dto;

import com.devsuperior.bds04.entities.Role;

import java.io.Serializable;

public class RoleDto implements Serializable {
    private static final long serialVersionUID = 322L;

    private Long id;
    private String authority;

    public RoleDto(Role entity) {
        id = entity.getId();
        authority = entity.getAuthority();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
