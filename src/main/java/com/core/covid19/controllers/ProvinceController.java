package com.core.covid19.controllers;

import java.util.List;

import com.core.covid19.models.entities.District;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.core.covid19.models.entities.Province;
import com.core.covid19.services.ProvinceService;

@RestController
@RequestMapping("/provinces")
public class ProvinceController {
	
	@Autowired
	private ProvinceService provinceService;
	
	@GetMapping
	public List<Province> list(@RequestParam(value = "idPerson", required = false) Integer idPerson) {

		if (idPerson != null)
			return provinceService.getByPerson(idPerson);
		return provinceService.findAll();
	}

	@GetMapping("/{id}/districts")
	public List<District> getDistricts(@PathVariable("id") Integer id){
		return provinceService.getDistricts(id);
	}

}
