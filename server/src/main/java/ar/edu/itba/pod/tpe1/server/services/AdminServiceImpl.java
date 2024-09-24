package ar.edu.itba.pod.tpe1.server.services;


import ar.edu.itba.pod.tpe1.server.models.Doctor;
import ar.edu.itba.pod.tpe1.server.repositories.DoctorRepository;
import ar.edu.itba.pod.tpe1.server.repositories.RoomRepository;
import ar.edu.itba.pod.tpe1.server.services.interfaces.AdminService;
import models.doctor.DoctorOuterClass;

public class AdminServiceImpl implements AdminService {
    DoctorRepository doctorRepository;
    RoomRepository roomRepo;

    public AdminServiceImpl(DoctorRepository doctorRepository, RoomRepository roomRepo) {
        this.doctorRepository = doctorRepository;
        this.roomRepo = roomRepo;
    }

    @Override
    public int addRoom() {
        return roomRepo.addRoom().getId();
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
    public Doctor setDoctor(String doctorName, DoctorOuterClass.DoctorStatus status) {
        Doctor doctor = getDoctor(doctorName);
        //TODO: check wheter or not the doctr it can change his status
        return doctorRepository.setStatus(doctor,status).orElseThrow();
    }


    @Override
    public Doctor getDoctor(String name) {
        return doctorRepository.getDoctorByName(name).orElseThrow();
    }
}
