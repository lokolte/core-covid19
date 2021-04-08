package com.core.covid19.models.responses;

import com.core.covid19.models.entities.Role;

public class RoleResponse {

    private Integer id;
    private String name;

    public RoleResponse() {
    }

    public RoleResponse(Role r) {
        this.id = r.getId();
        this.name = r.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
