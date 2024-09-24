package ar.edu.itba.pod.tpe1.server.repositories;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import ar.edu.itba.pod.tpe1.server.models.Patient;

public class PatientRepository {
    private Map<Integer, List<Patient>> patients;

    public PatientRepository() {
        patients = new ConcurrentHashMap<>();
        for (int i = 1; i <= 5; i++) {
            patients.put(i, Collections.synchronizedList(new ArrayList<>()));
        }
    }

    public Map<Integer, List<Patient>> getPatients() {
        return patients;
    }

    public Patient addPatient(Patient patient) {
        if (!patients.get(patient.getEmergencyLevel()).add(patient))
            throw new RuntimeException("Patient already registered");
        return patient;
    }

    public void removeFromWaitingList(Patient toCare) {
        if (toCare == null || toCare.getEmergencyLevel() >= 5 || toCare.getEmergencyLevel() <= 0)
            return;
        patients.get(toCare.getEmergencyLevel()).remove(toCare);
    }

}
