package ar.edu.itba.pod.tpe1.server.repositories;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import ar.edu.itba.pod.tpe1.server.models.Doctor;
import doctorPagerService.DoctorPagerServiceOuterClass;
import doctorPagerService.DoctorPagerServiceOuterClass.NotificationResponse;
import io.grpc.stub.StreamObserver;
import models.doctor.DoctorOuterClass;

public class DoctorRepository {
    private Set<Doctor> doctors;

    public DoctorRepository() {
        doctors = Collections.synchronizedSet(new TreeSet<>());
    }

    public Boolean addDoctor(Doctor doctor) {
        return doctors.add(doctor);
    }

    public TreeSet<Doctor> getDoctors() {
        return new TreeSet<>(doctors);
    }

    public Optional<Doctor> getDoctorByName(String name) {
        return doctors.stream()
                .filter(d -> d.getName().equals(name))
                .findFirst();
    }

    public Optional<Doctor> registerDoctorNotif(Doctor doctor,
            StreamObserver<DoctorPagerServiceOuterClass.NotificationResponse> responseObserver) {

        return doctors.stream()
                .filter(d -> d.equals(doctor))
                .findFirst()
                .map(d -> {
                    d.setPageable(true);
                    d.setObserver(responseObserver);
                    return d;
                });
    }

    public Optional<Doctor> unregisterDoctorNotif(Doctor doctor) {
        return doctors.stream()
                .filter(d -> d.equals(doctor))
                .findFirst()
                .map(d -> {
                    d.setPageable(false);
                    d.getObserver().onNext(DoctorPagerServiceOuterClass.NotificationResponse.newBuilder()
                            .setUnregister(doctor.toString() + " unregistered succesfully from pager").build());
                    d.getObserver().onCompleted();
                    return d;
                });
    }

    public Optional<Doctor> setStatus(Doctor doctor, DoctorOuterClass.DoctorStatus status) {
        return doctors.stream()
                .filter(d -> d.equals(doctor))
                .findFirst()
                .map(d -> {
                    if (d.getPageable())
                        d.getObserver().onNext(DoctorPagerServiceOuterClass.NotificationResponse.newBuilder()
                                .setStatus(d.toString() + " status changed to " + status).build());
                    d.setStatus(status);
                    return d;
                });
    }
}
