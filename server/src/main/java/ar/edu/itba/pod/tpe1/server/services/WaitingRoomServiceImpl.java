package ar.edu.itba.pod.tpe1.server.services;

import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import ar.edu.itba.pod.tpe1.server.models.CareHistory;
import ar.edu.itba.pod.tpe1.server.models.Patient;
import ar.edu.itba.pod.tpe1.server.models.Room;
import ar.edu.itba.pod.tpe1.server.repositories.CareHistoryRepository;
import ar.edu.itba.pod.tpe1.server.repositories.PatientRepository;
import ar.edu.itba.pod.tpe1.server.repositories.RoomRepository;
import ar.edu.itba.pod.tpe1.server.services.interfaces.WaitingRoomService;

public class WaitingRoomServiceImpl implements WaitingRoomService {
    PatientRepository patientRepo;
    CareHistoryRepository careHistoryRepo;
    RoomRepository roomRepo;

    public WaitingRoomServiceImpl(PatientRepository patientRepository, CareHistoryRepository careHistoryRepo,
            RoomRepository roomRepo) {
        this.patientRepo = patientRepository;
        this.careHistoryRepo = careHistoryRepo;
        this.roomRepo = roomRepo;
    }

    @Override
    public Patient addPatient(String name, int level) {
        if (level < AdminServiceImpl.MIN_LEVEL || level > AdminServiceImpl.MAX_LEVEL)
            throw new RuntimeException("level not allowed");
        Set<Room> rooms = roomRepo.getRooms();
        Queue<CareHistory> history = careHistoryRepo.getHistory();
        Optional<Room> maybeInRoom = rooms.stream().filter(r -> {
            if(r.getOccupied())
                return r.getPatient().getName().equals(name);
            else
                return false;
        }).findFirst();
        Optional<CareHistory> maybeInHistory = history.stream().filter(ch -> ch.getPatient().getName().equals(name))
                .findFirst();
        if (maybeInRoom.isPresent())
            throw new RuntimeException("Patient already exists and is being attended");
        if (maybeInHistory.isPresent())
            throw new RuntimeException("Patient has been attended previously");
        return patientRepo.addPatient(new Patient(name, level));
    }

    @Override
    public Patient updateLevel(String name, Integer level) {
        if (level <= 0 || level > 5)
            throw new RuntimeException("level not allowed");
        return patientRepo.getPatients().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .map(p -> {
                    p.setEmergencyLevel(level);
                    return p;
                }).orElseThrow(() -> new RuntimeException("No patient under that name"));
    }

    @Override
    public synchronized Integer checkPatient(String name) {
        TreeSet<Patient> patients = patientRepo.getPatients();
        Patient patient = patientRepo.getPatient(name).orElseThrow(()->new RuntimeException("No patient found with under this name: "+name));
        int out = 0;
        for (Patient p : patients) {
            if (p.equals(patient))
                break;
            out++;
        }
        return out;
    }

    @Override
    public Patient getPatient(String name) {
        return patientRepo.getPatient(name).orElseThrow(()-> new RuntimeException("No patient found under this name: "+name));
    }

}
