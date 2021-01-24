package com.core.covid19.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.covid19.models.entities.Province;
import com.core.covid19.services.ProvinceService;

@RestController
@RequestMapping("/provinces")
public class ProvinceController {
	
	@Autowired
	private ProvinceService provinceService;
	
	@GetMapping
	public List<Province> list(){
		return provinceService.findAll();
	}

}
