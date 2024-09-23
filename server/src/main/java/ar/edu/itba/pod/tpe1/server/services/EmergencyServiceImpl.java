package ar.edu.itba.pod.tpe1.server.services;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.List;

import ar.edu.itba.pod.tpe1.server.models.CareHistory;
import ar.edu.itba.pod.tpe1.server.models.Doctor;
import ar.edu.itba.pod.tpe1.server.models.Patient;
import ar.edu.itba.pod.tpe1.server.models.Room;
import ar.edu.itba.pod.tpe1.server.repositories.CareHistoryRepository;
import ar.edu.itba.pod.tpe1.server.repositories.DoctorRepository;
import ar.edu.itba.pod.tpe1.server.repositories.PatientRepository;
import ar.edu.itba.pod.tpe1.server.repositories.RoomRepository;
import ar.edu.itba.pod.tpe1.server.services.interfaces.EmergencyService;

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

    public boolean carePatient(Room room) {
        Set<Room> rooms = roomRepo.getRooms();
        if (!rooms.contains(room)) {
            throw new RuntimeException("Room doesn't exist");
        }
        for (Room aux : rooms) {
            if (aux.equals(room)) {
                if (aux.getOccupied()) {
                    throw new RuntimeException("Room is occupied");
                }
            }
        }

        Patient toCare = null;
        Map<Integer, List<Patient>> patients = patientRepo.getPatients();
        Set<Doctor> doctors = doctorRepo.getDoctors();
        if (doctors.isEmpty())
            throw new RuntimeException("No doctors registered");
        Doctor toAttend = null;
        for (int i = 5; i >= 0; i--) {
            if (patients.get(i).isEmpty())
                continue;
            toCare = patients.get(i).getFirst();
            for (Doctor doctor : doctors) {
                if (doctor.canCare()) {
                    toAttend = doctor;
                    break;
                }
            }
        }
        if (toCare == null)
            throw new RuntimeException("No patients waiting to be cared for");
        if (toAttend == null)
            throw new RuntimeException("No available doctors to attend the patient");
        CareHistory appointment = new CareHistory(toAttend, toCare, room);
        doctorRepo.setAttending(toAttend);
        roomRepo.setUnavailable(room);
        patientRepo.removeFromWaitingList(toCare);
        return careRepo.addToCurrent(appointment);
    }

    // TODO: sacarle las exceptions, este no tiene que fallar.
    public void careAllPatients() {
        Set<Room> rooms = roomRepo.getRooms();
        Map<Integer, List<Patient>> patients = patientRepo.getPatients();
        Set<Doctor> doctors = doctorRepo.getDoctors();
        if (rooms.isEmpty())
            throw new RuntimeException("No rooms registered");
        for (Room room : rooms) {
            if (room.getOccupied())
                continue;
            Patient toCare = null;
            if (doctors.isEmpty())
                throw new RuntimeException("No doctors registered");
            Doctor toAttend = null;
            for (int i = 5; i >= 0; i--) {
                if (patients.get(i).isEmpty())
                    continue;
                toCare = patients.get(i).getFirst();
                for (Doctor doctor : doctors) {
                    if (doctor.canCare()) {
                        toAttend = doctor;
                        break;
                    }
                }
            }
            if (toCare == null)
                throw new RuntimeException("No patients waiting to be cared for");
            if (toAttend == null)
                throw new RuntimeException("No available doctors to attend the patient");
            doctorRepo.setAttending(toAttend);
            roomRepo.setUnavailable(room);
            patientRepo.removeFromWaitingList(toCare);
            CareHistory careHistory = new CareHistory(toAttend, toCare, room);
            careRepo.addToCurrent(careHistory);
        }
    }

    public boolean dischargePatient(Room room, Doctor doctor, Patient patient) {
        Queue<CareHistory> currentCare = careRepo.getCurrentAppointments();
        CareHistory aux = new CareHistory(doctor, patient, room);
        for (CareHistory careHistory : currentCare) {
            if (careHistory.equals(aux)) {
                return careRepo.removeFromCurrent(careHistory);
            }
        }
        return false;
    }

}
