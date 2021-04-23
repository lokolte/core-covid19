package com.core.covid19.controllers;

import com.core.covid19.models.responses.RoleResponse;
import com.core.covid19.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService service;

    @GetMapping
    public List<RoleResponse> listAll(@RequestParam(value = "idPerson", required = false) Integer idPerson) {
        if (idPerson != null)
            return service.getByAccount(idPerson);
        return service.getAll();
    }
}
