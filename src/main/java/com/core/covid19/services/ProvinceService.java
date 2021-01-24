package com.core.covid19.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.covid19.models.entities.Province;
import com.core.covid19.repos.ProvinceRepo;

@Service
public class ProvinceService {
	
	@Autowired
	private ProvinceRepo provinceRepo;
	
	public List<Province> findAll() {
		return provinceRepo.findAll();
	}

}
