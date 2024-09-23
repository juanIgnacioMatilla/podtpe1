package ar.edu.itba.pod.tpe1.server.services;

import java.util.Set;

import ar.edu.itba.pod.tpe1.server.models.Doctor;
import ar.edu.itba.pod.tpe1.server.repositories.DoctorRepository;
import ar.edu.itba.pod.tpe1.server.services.interfaces.NotificationService;

/**
 * NotificationService
 */
public class NotificationServiceImpl implements NotificationService {
    DoctorRepository doctorRepo;

    public NotificationServiceImpl(DoctorRepository doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    public boolean register(Doctor doctor) {
        if (doctor == null)
            throw new RuntimeException("Doctor doesn't exist");
        return doctorRepo.registerDoctorNotif(doctor);
    }

    public boolean unregister(Doctor doctor) {
        if (doctor == null)
            throw new RuntimeException("Doctor doesn't exist");
        return doctorRepo.unregisterDoctorNotif(doctor);
    }

}
