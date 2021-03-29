package com.core.covid19.services;

import com.core.covid19.models.entities.Account;
import com.core.covid19.models.entities.Person;
import com.core.covid19.models.entities.Role;
import com.core.covid19.models.enums.Roles;
import com.core.covid19.models.responses.PersonResponse;
import com.core.covid19.repos.PersonRepo;
import com.core.covid19.repos.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoordinatorService {

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private RoleRepo roleRepo;

    public List<PersonResponse> getCoordinators() {

        Role role = roleRepo.findByName(Roles.COORDINADOR.toString());
        List<Account> accounts = personRepo.getAccounts();
        List<Person> persons = new ArrayList<>();
        for (Account a : accounts) {
            for (Role r : a.getRoles()) {
                if (r.getId() == role.getId())
                    persons.add(a.getPerson());
            }
        }

        List<PersonResponse> list = new ArrayList<>();
        for (Person p : persons) list.add(new PersonResponse(p));
        return list;
    }
}
