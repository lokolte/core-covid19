package com.core.covid19.repos;

import com.core.covid19.models.entities.RoleAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleAccountRepo extends JpaRepository<RoleAccount, Integer> {

    public List<RoleAccount> getRolesByAccount(int account);
}
