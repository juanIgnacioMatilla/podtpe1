package ar.edu.itba.pod.tpe1.server.services.interfaces;

import ar.edu.itba.pod.tpe1.server.models.Doctor;
import doctorPagerService.DoctorPagerServiceOuterClass;
import io.grpc.stub.StreamObserver;

/**
 * NotificationService
 */
public interface NotificationService {
    Doctor register(Doctor doctor,
            StreamObserver<DoctorPagerServiceOuterClass.NotificationResponse> responseObserver);

    Doctor unregister(Doctor doctor);
}
