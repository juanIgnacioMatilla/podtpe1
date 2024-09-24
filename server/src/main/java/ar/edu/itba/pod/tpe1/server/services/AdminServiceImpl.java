package ar.edu.itba.pod.tpe1.server.services;

import java.util.Set;

import ar.edu.itba.pod.tpe1.server.models.Doctor;
import ar.edu.itba.pod.tpe1.server.models.Room;
import ar.edu.itba.pod.tpe1.server.repositories.DoctorRepository;
import ar.edu.itba.pod.tpe1.server.repositories.RoomRepository;
import ar.edu.itba.pod.tpe1.server.services.interfaces.AdminService;

public class AdminServiceImpl implements AdminService {
    DoctorRepository doctorRepository;
    RoomRepository roomRepo;

    public AdminServiceImpl(DoctorRepository doctorRepository, RoomRepository roomRepo) {
        this.doctorRepository = doctorRepository;
        this.roomRepo = roomRepo;
    }

    @Override
    public void addRoom() {
        roomRepo.addRoom();
    }

    @Override
    public Doctor addDoctor(String name, Integer maxLevel) {
        if (maxLevel <= 0 || maxLevel >= 9)
            throw new RuntimeException("maxLevel not permited");
        Doctor doctor = new Doctor(name, maxLevel);
        if (!doctorRepository.addDoctor(doctor))
            throw new RuntimeException("doctor already exists");
        return doctor;
    }

    @Override
    public Doctor setDoctor(Doctor doctor, Doctor.Status status) {
        return doctorRepository.getDoctors().stream()
                .filter(d -> d.equals(doctor))
                .findFirst()
                .map(d -> {
                    d.setStatus(status);
                    return d;
                }).get();
    }

    @Override
    public Doctor.Status checkDoctor(Doctor doctor) {
        return doctorRepository.getDoctors().stream()
                .filter(d -> d.equals(doctor))
                .findFirst()
                .map(d -> {
                    return d.getStatus();
                }).get();
    }
}
