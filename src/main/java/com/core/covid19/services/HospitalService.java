package com.core.covid19.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.covid19.models.entities.Account;
import com.core.covid19.models.entities.Hospital;
import com.core.covid19.models.entities.Location;
import com.core.covid19.models.entities.location.HospitalDistance;
import com.core.covid19.models.responses.HospitalsResponse;
import com.core.covid19.repos.AccountRepo;
import com.core.covid19.repos.HospitalRepo;

@Service
public class HospitalService {
	@Autowired
	private AccountRepo accountRepo;

	@Autowired
	private HospitalRepo hospitalRepo;
	
	private Integer maxHospitals = 2;

	public HospitalsResponse getTenCloser(String email) {
		Account account = accountRepo.findByEmail(email);

		List<Hospital> hospitals = hospitalRepo.findAll();

		List<HospitalDistance> hospitalsDistance = new ArrayList<HospitalDistance>();

		for(Hospital hospital : hospitals)
			hospitalsDistance.add(new HospitalDistance(distanceOf(account.getPerson().getLocation(), hospital.getLocation()), hospital));

		hospitalsDistance = hospitalsDistance.stream().sorted().collect(Collectors.toList());

		return new HospitalsResponse(account.getPerson(), tenClosers(hospitalsDistance));
	}

	private List<Hospital> tenClosers(List<HospitalDistance> closers) {
		List<Hospital> tenHospitalClosers = new ArrayList<Hospital>();
		for(HospitalDistance hospitalDistance : closers)
			if(tenHospitalClosers.size() < maxHospitals)
				tenHospitalClosers.add(hospitalDistance.getHospital());
			else break;

		return tenHospitalClosers;
	}

	private Double distanceOf(Location a, Location b) {
		return Math.sqrt(square(a.getLatitude() - b.getLatitude()) + square(a.getLongitude() - b.getLongitude()));
	}

	private double square(double n) {
		return n*n;
	}
}