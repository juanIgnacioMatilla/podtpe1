package ar.edu.itba.pod.tpe1.server.repositories;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import ar.edu.itba.pod.tpe1.server.models.Patient;

public class PatientRepository {
    private Set<Patient> patients;

    public PatientRepository() {
        patients = Collections.synchronizedSet(new TreeSet<>());
    }

    public TreeSet<Patient> getPatients() {
        return new TreeSet<>(patients);
    }

    public Patient addPatient(Patient patient) {
        if (!patients.add(patient))
            throw new RuntimeException("Patient already registered");
        return patient;
    }

    public void removeFromWaitingList(Patient toCare) {
        if (toCare == null)
            return;
        patients.remove(toCare);
    }

    public synchronized Optional<Patient> getPatient(String name) {
        return patients.stream()
                .filter(patient -> patient.getName().equals(name))
                .findFirst();
    }

}
