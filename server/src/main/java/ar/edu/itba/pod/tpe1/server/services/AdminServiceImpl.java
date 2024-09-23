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
    public boolean addDoctor(String name, Integer maxLevel) {
        if (maxLevel <= 0 || maxLevel >= 9)
            return false;
        Doctor doctor = new Doctor(name, maxLevel);
        return doctorRepository.addDoctor(doctor);
    }

    @Override
    public boolean setDoctor(String name, Doctor.Status status) {
        Set<Doctor> doctors = doctorRepository.getDoctors();
        if (!doctors.contains(new Doctor(name, 1)))
            return false;
        // deberiamos hacer esto dentro de doctor repository en una funcion
        // syncrhonized?
        for (Doctor doctor : doctors) {
            if (doctor.getName().compareTo(name) == 0)
                doctor.setStatus(status);
        }
        return true;
    }

    @Override
    public Doctor.Status checkDoctor(String name) {
        Doctor doc = doctorRepository.getDoctor(name);
        if (doc == null)
            throw new RuntimeException("Doctor doesn't exist");
        return doc.getStatus();
    }
}
