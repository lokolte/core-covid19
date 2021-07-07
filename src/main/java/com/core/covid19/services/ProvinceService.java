package com.core.covid19.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.core.covid19.models.entities.District;
import com.core.covid19.models.entities.Person;
import com.core.covid19.repos.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.covid19.models.entities.Province;
import com.core.covid19.repos.ProvinceRepo;

@Service
public class ProvinceService {
	
	@Autowired
	private ProvinceRepo provinceRepo;

	@Autowired
	PersonRepo personRepo;
	
	public List<Province> findAll() {
		return provinceRepo.findAll();
	}

	public List<Province> getByPerson(int id) {

		List<Province> all = provinceRepo.findAll();
		Person p = personRepo.getOne(id);
		if (p != null && p.getProvincesDoctor() != null) {
			Set<Province> provincesPerson = p.getProvincesDoctor();
			List<Integer> ids = provincesPerson.stream().map(Province::getId).collect(Collectors.toList());
			List<Province> res = new ArrayList<>();
			for (Province province : all) {
				int idProvince = province.getId();
				if (!ids.contains(idProvince))
					res.add(province);
			}
			return res;
		}
		return all;
	}

	public List<District> getDistricts(int id) {
		return provinceRepo.getDistricts(id);
	}

}
