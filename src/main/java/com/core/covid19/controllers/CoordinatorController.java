package com.core.covid19.controllers;

import com.core.covid19.models.entities.Person;
import com.core.covid19.models.requests.DoctorRequest;
import com.core.covid19.models.responses.PersonResponse;
import com.core.covid19.services.AccountService;
import com.core.covid19.services.CoordinatorService;
import com.core.covid19.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coordinators")
public class CoordinatorController {

    @Autowired
    private CoordinatorService service;

    @Autowired
    private PersonService personService;

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<PersonResponse> getCoordinators() {
        return service.getCoordinators();
    }

    @PostMapping
    public void update(@RequestBody DoctorRequest data) {
        Person p = new Person(data);
        personService.modify(data.getEmail(), p);
    }

    @PostMapping("/new")
    public void insert(@RequestBody DoctorRequest data) throws Exception {
        accountService.insertCoordinador(data);
    }
}
