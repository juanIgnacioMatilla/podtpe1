package ar.edu.itba.pod.tpe1.server.services;

import java.util.Optional;
import java.util.Set;

import ar.edu.itba.pod.tpe1.server.models.Doctor;
import ar.edu.itba.pod.tpe1.server.repositories.DoctorRepository;
import ar.edu.itba.pod.tpe1.server.services.interfaces.NotificationService;
import doctorPagerService.DoctorPagerServiceOuterClass;
import io.grpc.stub.StreamObserver;

/**
 * NotificationService
 */
public class NotificationServiceImpl implements NotificationService {
    DoctorRepository doctorRepo;

    public NotificationServiceImpl(DoctorRepository doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    public Doctor register(Doctor doctor,
            StreamObserver<DoctorPagerServiceOuterClass.NotificationResponse> responseObserver) {
        if (doctor.getPageable())
            throw new RuntimeException("Doctor is already registered for paging");
        Optional<Doctor> optDoc = doctorRepo.registerDoctorNotif(doctor, responseObserver);
        Doctor doc = optDoc.orElseThrow(()->new RuntimeException("No doctor found"));
        doc.getObserver().onNext(DoctorPagerServiceOuterClass.NotificationResponse.newBuilder()
                .setRegister(doctor.toString() + " registred succesfully for pager").build());
        return doc;
    }

    public Doctor unregister(Doctor doctor) {
        if (!doctor.getPageable())
            throw new RuntimeException("Doctor is not registered for paging");
        Optional<Doctor> optDoc = doctorRepo.unregisterDoctorNotif(doctor);
        return optDoc.orElseThrow(()-> new RuntimeException("No doctor found"));
    }

}
