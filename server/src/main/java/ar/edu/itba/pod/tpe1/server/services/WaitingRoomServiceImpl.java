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
    public Patient addPatient(Patient newPatient) {
        Integer level = newPatient.getEmergencyLevel();
        if (level <= 0 || level > 5)
            throw new RuntimeException("level not allowed");
        return patientRepo.addPatient(newPatient);
    }

    @Override
    public Patient updateLevel(Patient patient, Integer level) {
        if (level <= 0 || level > 5)
            throw new RuntimeException("level not allowed");
        return patientRepo.getPatients().get(patient.getEmergencyLevel()).stream()
                .filter(p -> p.equals(patient))
                .findFirst()
                .map(p -> {
                    p.setEmergencyLevel(level);
                    return p;
                }).get();
    }

    @Override
    public Integer checkPatient(Patient patient) {
        Map<Integer, List<Patient>> patients = patientRepo.getPatients();
        Integer out = 0;
        for (int i = 5; i > patient.getEmergencyLevel(); i--) {
            out += patients.get(i).size();
        }
        out += patients.get(patient.getEmergencyLevel()).indexOf(patient);
        return out;
    }

}
