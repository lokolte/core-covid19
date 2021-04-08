package com.core.covid19.models.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="role_account")
@NamedQuery(name="RoleAccount.getRolesByAccount",
        query="SELECT r FROM RoleAccount r WHERE r.id.account = :account")
public class RoleAccount {

    @EmbeddedId
    private RoleAccountPk id;

    public RoleAccount() {
    }

    public RoleAccount(RoleAccountPk id) {
        this.id = id;
    }

    public RoleAccountPk getId() {
        return id;
    }

    public void setId(RoleAccountPk id) {
        this.id = id;
    }
}
