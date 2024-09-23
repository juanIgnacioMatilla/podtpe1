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
    public boolean addPatient(Patient newPatient) {
        Integer level = newPatient.getEmergencyLevel();
        if (level <= 0 || level > 5)
            return false;
        return patientRepo.addPatient(newPatient);
    }

    @Override
    public boolean updateLevel(String name, Integer level) {
        if (level <= 0 || level > 5)
            return false;
        Map<Integer, List<Patient>> allPatients = patientRepo.getPatients();
        List<Patient> patients = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            patients.addAll(allPatients.get(i));
        }
        Boolean flag = false;
        for (Patient p : patients) {
            if (p.getName().compareTo(name) == 0) {
                p.setEmergencyLevel(level);
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public Integer checkPatient(String name) {
        Map<Integer, List<Patient>> patients = patientRepo.getPatients();
        Patient a = null;
        for (Integer lev : patients.keySet()) {
            for (Patient aux : patients.get(lev)) {
                if (aux.getName().compareTo(name) == 0) {
                    a = aux;
                }
            }
        }
        if (a == null)
            throw new RuntimeException("Patient doesn't exist");
        Integer out = 0;
        Integer level = 5;
        while (level > a.getEmergencyLevel()) {
            out += patients.get(level).size();
            level--;
        }
        // TODO: chequear si indexOf devuelve 0 para la primer posicion.
        out += patients.get(level).indexOf(name);
        return out;
    }

}
