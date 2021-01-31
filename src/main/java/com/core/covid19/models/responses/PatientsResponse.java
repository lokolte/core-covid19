package com.core.covid19.models.responses;

import java.util.ArrayList;
import java.util.List;

public class PatientsResponse {

    private List<PersonResponse> patients;

    public PatientsResponse() {
        this.patients = new ArrayList<>();
    }

    public PatientsResponse(List<PersonResponse> persons) {
        this.patients = persons;
    }

    public List<PersonResponse> getPatients() {
        return patients;
    }

    public void setPatients(List<PersonResponse> patients) {
        this.patients = patients;
    }
}
