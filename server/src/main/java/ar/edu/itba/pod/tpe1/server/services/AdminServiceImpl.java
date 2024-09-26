package ar.edu.itba.pod.tpe1.server.services;


import ar.edu.itba.pod.tpe1.server.models.Doctor;
import ar.edu.itba.pod.tpe1.server.repositories.DoctorRepository;
import ar.edu.itba.pod.tpe1.server.repositories.RoomRepository;
import ar.edu.itba.pod.tpe1.server.services.interfaces.AdminService;
import models.doctor.DoctorOuterClass;

public class AdminServiceImpl implements AdminService {
    DoctorRepository doctorRepository;
    RoomRepository roomRepo;

    public static Integer MAX_LEVEL = 5;
    public static Integer MIN_LEVEL = 1;

    public AdminServiceImpl(DoctorRepository doctorRepository, RoomRepository roomRepo) {
        this.doctorRepository = doctorRepository;
        this.roomRepo = roomRepo;
    }

    @Override
    public int addRoom() {
        return roomRepo.addRoom().getId();
    }

    @Override
    public Doctor addDoctor(String name, Integer level) {
        if (level <= MIN_LEVEL || level >= MAX_LEVEL)
            throw new IllegalArgumentException("Level "+level+" is invalid. Must be between "+MIN_LEVEL+" and "+MAX_LEVEL);
        Doctor doctor = new Doctor(name, level);
        if (!doctorRepository.addDoctor(doctor))
            throw new RuntimeException("Doctor "+name+" already exists");
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
