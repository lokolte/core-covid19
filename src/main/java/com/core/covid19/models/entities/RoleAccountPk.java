package com.core.covid19.models.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RoleAccountPk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="account_id", nullable=false)
    private Integer account;

    @Column(name="role_id", nullable=false)
    private Integer role;

    public RoleAccountPk() {
    }

    public RoleAccountPk(Integer account, Integer role) {
        this.account = account;
        this.role = role;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
