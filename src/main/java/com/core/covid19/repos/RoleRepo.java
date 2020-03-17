package com.core.covid19.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.covid19.models.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

	Role findByName(String name);

}
