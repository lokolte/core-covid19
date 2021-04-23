package com.core.covid19.services;

import com.core.covid19.models.entities.Account;
import com.core.covid19.models.entities.Person;
import com.core.covid19.models.entities.Role;
import com.core.covid19.models.enums.Roles;
import com.core.covid19.models.responses.RoleResponse;
import com.core.covid19.repos.PersonRepo;
import com.core.covid19.repos.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PersonRepo personRepo;

    public List<RoleResponse> getAll() {

        List<Role> roles = roleRepo.findAll();
        List<RoleResponse> res = new ArrayList<>();
        for (Role r : roles) {
            res.add(new RoleResponse(r));
        }
        return res;
    }

    public List<RoleResponse> getByAccount(Integer idPerson) {
        Optional<Person> person = personRepo.findById(idPerson);

        if(!person.isPresent()) return new ArrayList<>();

        List<Role> all = roleRepo.findAll();
        Account account = person.get().getAccounts().stream().collect(Collectors.toList()).get(0);
        Set<Role> roles = account.getRoles();
        List<RoleResponse> res = new ArrayList<>();
        for (Role r1 : all) {
            boolean contains = false;
            if (!r1.getName().equals(Roles.CIVIL.name())) {
                for (Role r2 : roles) {
                    if (r1.getId().equals(r2.getId())) {
                        contains = true;
                        break;
                    }
                }
                if (!contains) res.add(new RoleResponse(r1));
            }
        }
        return res;
    }
}
