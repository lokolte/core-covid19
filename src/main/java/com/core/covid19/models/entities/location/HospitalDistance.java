package com.core.covid19.models.entities.location;

import com.core.covid19.models.entities.Hospital;

public class HospitalDistance implements Comparable<HospitalDistance> {
	private Double distance;

	private Hospital hospital;

	public HospitalDistance(Double distance, Hospital hospital) {
		this.distance = distance;
		this.hospital = hospital;
	}

	@Override
	public int compareTo(HospitalDistance hospitalDistance) {
		return this.getDistance().compareTo(hospitalDistance.getDistance());
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
}