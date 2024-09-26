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
import doctorPagerService.DoctorPagerServiceOuterClass;
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
        if (maybeRoom.isEmpty())
            throw new RuntimeException("Room doesn't exist");
        Room room = maybeRoom.get();
        if (room.getOccupied())
            throw new RuntimeException("Room is occupied");
        Patient toCare = null;
        TreeSet<Patient> patients = patientRepo.getPatients();
        TreeSet<Doctor> doctors = doctorRepo.getDoctors();
        if (doctors.isEmpty())
            throw new RuntimeException("No doctors registered");
        Doctor toAttend = null;
        if (patients.isEmpty())
            throw new RuntimeException("No patients waiting to be attended");
        synchronized (patients) {
            while (toAttend == null && !patients.isEmpty()) {
                toCare = patients.first();

                for (Doctor doctor : doctors) {
                    if (doctor.canCare(toCare.getEmergencyLevel())) {
                        toAttend = doctor;
                        break;
                    }
                }
                if (toAttend == null)
                    patients.remove(toCare);
            }
        }
        if (toCare == null)
            throw new RuntimeException("No patients waiting to be cared for");
        if (toAttend == null)
            throw new RuntimeException("No available doctors to attend the patient");
        doctorRepo.setStatus(toAttend, DoctorOuterClass.DoctorStatus.ATTENDING);
        Room outputRoom = this.occupyRoom(room.getId(), toAttend, toCare);
        patientRepo.removeFromWaitingList(toCare);
        if (toAttend.getPageable())
            toAttend.getObserver().onNext(DoctorPagerServiceOuterClass.NotificationResponse.newBuilder()
                    .setAttending(toCare.toString() + " and " + toAttend.toString() + " are now in " + room.toString())
                    .build());

        return outputRoom;
    }

    public synchronized List<Room> careAllPatients() {
        Set<Room> rooms = roomRepo.getRooms();
        List<Room> out = new ArrayList<>();
        TreeSet<Patient> patients = patientRepo.getPatients();
        TreeSet<Doctor> doctors = doctorRepo.getDoctors();
        for (Room room : rooms) {
            if (!room.getOccupied()) {
                Patient toCare = null;
                Doctor toAttend = null;
                while (toAttend == null && !patients.isEmpty()) {
                    toCare = patients.first();
                    for (Doctor doctor : doctors) {
                        if (doctor.canCare(toCare.getEmergencyLevel())) {
                            toAttend = doctor;
                            break;
                        }
                    }
                    if (toAttend == null)
                        patients.remove(toCare);
                }
                if (toCare != null && toAttend != null) {
                    doctorRepo.setStatus(toAttend, DoctorOuterClass.DoctorStatus.ATTENDING);
                    Room outputRoom = this.occupyRoom(room.getId(), toAttend, toCare);
                    patientRepo.removeFromWaitingList(toCare);
                    if (toAttend.getPageable())
                        toAttend.getObserver().onNext(DoctorPagerServiceOuterClass.NotificationResponse.newBuilder()
                                .setAttending(toCare.toString() + " and " + toAttend.toString() + " are now in "
                                        + room.toString())
                                .build());
                    out.add(outputRoom);
                }
            }
        }
        return out;
    }

    @Override
    public Room dischargePatient(Integer roomId, String doctorName, String patientName) {
        if (doctorRepo.getDoctorByName(doctorName).isEmpty())
            throw new IllegalArgumentException("Doctor with name " + patientName + " does not exists");

        Room room = roomRepo.getRooms().stream()
                .filter(r -> r.getId().equals(roomId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Room with number " + roomId + " not found"));
        if (!room.getOccupied())
            throw new RuntimeException("Room #" + roomId + " is not occupied cannot be vacated");
        Doctor doctor = room.getDoctor();
        Patient patient = room.getPatient();
        if (Objects.isNull(patient) || !patient.getName().equals(patientName))
            throw new IllegalArgumentException("Patient with name " + patientName + " not in Room #" + roomId);
        if (Objects.isNull(doctor) ||!doctor.getName().equals(doctorName))
            throw new IllegalArgumentException("Doctor with name " + doctorName + " not in Room #" + roomId);

        careRepo.addToHistory(new CareHistory(room.getDoctor(), room.getPatient(), roomId));
        doctorRepo.setStatus(room.getDoctor(), DoctorOuterClass.DoctorStatus.AVAILABLE);

        vacateRoom(roomId);
        Room outputRoom = new Room(roomId);
        outputRoom.setPatient(patient);
        outputRoom.setDoctor(doctor);
        if (doctor.getPageable())
            doctor.getObserver().onNext(DoctorPagerServiceOuterClass.NotificationResponse.newBuilder()
                    .setFinishAttending(patient.toString() + " has been discharged from " + doctor.toString()
                            + " and the " + room.toString() + " is now Free")
                    .build());
        return outputRoom;
    }

    private Room occupyRoom(Integer roomId, Doctor doctor, Patient patient) {
        if (!doctorRepo.getDoctors().contains(doctor))
            throw new IllegalArgumentException("Doctor " + doctor.getName() + " not found");
        if (!patientRepo.getPatients().contains(patient))
            throw new IllegalArgumentException("Patient " + patient.getName() + " not found");
        return roomRepo.updateRoom(roomId, patient, doctor, true)
                .orElseThrow(() -> new IllegalStateException("Room #" + roomId + " not found"));
    }

    private Room vacateRoom(Integer roomId) {
        return roomRepo.updateRoom(roomId, null, null, false)
                .orElseThrow(() -> new IllegalStateException("Room #" + roomId + " not found"));
    }

}
