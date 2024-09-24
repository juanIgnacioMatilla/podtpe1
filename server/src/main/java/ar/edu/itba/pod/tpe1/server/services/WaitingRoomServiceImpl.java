package ar.edu.itba.pod.tpe1.server.services;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import ar.edu.itba.pod.tpe1.server.models.Patient;
import ar.edu.itba.pod.tpe1.server.repositories.PatientRepository;
import ar.edu.itba.pod.tpe1.server.services.interfaces.WaitingRoomService;

public class WaitingRoomServiceImpl implements WaitingRoomService {
    PatientRepository patientRepo;

    public WaitingRoomServiceImpl(PatientRepository patientRepository) {
        this.patientRepo = patientRepository;
    }

    @Override
    public Patient addPatient(String name, int level) {
        if (level <= 0 || level > 5)
            throw new RuntimeException("level not allowed");
        return patientRepo.addPatient(new Patient(name,level));
    }

    @Override
    public Patient updateLevel(String name, Integer level) {
        Patient patient = patientRepo.getPatient(name).orElseThrow();
        if (level <= 0 || level > 5)
            throw new RuntimeException("level not allowed");
        return patientRepo.getPatients().get(patient.getEmergencyLevel()).stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .map(p -> {
                    p.setEmergencyLevel(level);
                    return p;
                }).orElseThrow();
    }

    @Override
    public Integer checkPatient(String name) {
        Map<Integer, List<Patient>> patients = patientRepo.getPatients();
        Patient patient = patientRepo.getPatient(name).orElseThrow();
        int out = 0;
        for (int i = 5; i > patient.getEmergencyLevel(); i--) {
            out += patients.get(i).size();
        }
        out += patients.get(patient.getEmergencyLevel()).indexOf(patient);
        return out;
    }

    @Override
    public Patient getPatient(String name) {
        return patientRepo.getPatient(name).orElseThrow();
    }

}
