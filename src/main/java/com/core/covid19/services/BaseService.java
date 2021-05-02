package com.core.covid19.services;

import com.core.covid19.models.entities.Role;
import com.core.covid19.models.enums.Roles;
import java.util.Set;

public class BaseService {

    protected boolean isAdmin(Set<Role> roles) {
        return hasRole(roles, Roles.ADMIN.name());
    }

    protected boolean isCoordinator(Set<Role> roles) {
        return hasRole(roles, Roles.COORDINADOR.name());
    }

    protected boolean isDoctor(Set<Role> roles) {
        return hasRole(roles, Roles.PROFESIONAL_MEDICO.name());
    }

    private boolean hasRole(Set<Role> roles, String role) {
        for (Role r : roles) {
            if (r.getName().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
