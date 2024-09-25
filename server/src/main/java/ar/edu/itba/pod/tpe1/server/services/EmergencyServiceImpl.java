package ar.edu.itba.pod.tpe1.server.services;

import java.util.List;
import java.util.*;

import ar.edu.itba.pod.tpe1.server.models.CareHistory;
import ar.edu.itba.pod.tpe1.server.models.Doctor;
import ar.edu.itba.pod.tpe1.server.models.Patient;
import ar.edu.itba.pod.tpe1.server.models.Room;
import ar.edu.itba.pod.tpe1.server.repositories.CareHistoryRepository;
import ar.edu.itba.pod.tpe1.server.repositories.DoctorRepository;
import ar.edu.itba.pod.tpe1.server.repositories.PatientRepository;
import ar.edu.itba.pod.tpe1.server.repositories.RoomRepository;
import ar.edu.itba.pod.tpe1.server.services.interfaces.EmergencyService;
import models.doctor.DoctorOuterClass;

import javax.print.Doc;

public class EmergencyServiceImpl implements EmergencyService {
    private PatientRepository patientRepo;
    private DoctorRepository doctorRepo;
    private CareHistoryRepository careRepo;
    private RoomRepository roomRepo;

    public EmergencyServiceImpl(PatientRepository patientRepository, DoctorRepository doctorRepository,
            CareHistoryRepository careHistoryRepository, RoomRepository roomRepository) {
        this.doctorRepo = doctorRepository;
        this.patientRepo = patientRepository;
        this.careRepo = careHistoryRepository;
        this.roomRepo = roomRepository;
    }

    public Room carePatient(Integer roomId) {
        Set<Room> rooms = roomRepo.getRooms();
        Optional<Room> maybeRoom = rooms.stream().filter(r -> r.getId() == roomId).findFirst();
        if(maybeRoom.isEmpty())
            throw new RuntimeException("Room doesn't exist");
        Room room = maybeRoom.get();
        if(room.getOccupied())
            throw new RuntimeException("Room is occupied");
        Patient toCare = null;
        TreeSet<Patient> patients = patientRepo.getPatients();
        TreeSet<Doctor> doctors = doctorRepo.getDoctors();
        if (doctors.isEmpty())
            throw new RuntimeException("No doctors registered");
        Doctor toAttend = null;
        if (patients.isEmpty())
            throw new RuntimeException("No patients waiting to be attended");
        while(toAttend == null && !patients.isEmpty()){
            toCare = patients.first();
            for (Doctor doctor : doctors) {
                if (doctor.canCare(toCare.getEmergencyLevel())) {
                    toAttend = doctor;
                    break;
                }
            }
            if(toAttend == null)
                patients.remove(toCare);
        }
        if (toCare == null)
            throw new RuntimeException("No patients waiting to be cared for");
        if (toAttend == null)
            throw new RuntimeException("No available doctors to attend the patient");
        CareHistory appointment = new CareHistory(toAttend, toCare, room.getId());
        doctorRepo.setStatus(toAttend, DoctorOuterClass.DoctorStatus.ATTENDING);
        this.occupyRoom(room.getId(), toAttend,toCare);
        patientRepo.removeFromWaitingList(toCare);
        return this.occupyRoom(room.getId(), toAttend,toCare);
    }

    public List<Room> careAllPatients() {
        Set<Room> rooms = roomRepo.getRooms();
        List<Room> out = new ArrayList<>();
        Room aux = null;
        
        for (Room room : rooms) {
            if(!room.getOccupied()){
                aux = carePatient(room.getId());
                out.add(aux);
            }
        }
        return out;
    }

    @Override
    public Room dischargePatient(Integer roomId, String doctorName, String patientName) {
        Doctor doctor = doctorRepo.getDoctors().stream()
                .filter(d -> d.getName().equals(doctorName))
                .findFirst().orElseThrow();
        Room room = roomRepo.updateRoom(roomId,null,null,false).orElseThrow();
        return room;
    }

    private Room occupyRoom(Integer roomId,Doctor doctor, Patient patient){
        return roomRepo.updateRoom(roomId,patient,doctor,true).orElseThrow();
    }


}
