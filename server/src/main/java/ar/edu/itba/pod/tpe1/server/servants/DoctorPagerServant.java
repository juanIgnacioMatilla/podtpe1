package ar.edu.itba.pod.tpe1.server.servants;

import ar.edu.itba.pod.tpe1.server.models.Doctor;
import ar.edu.itba.pod.tpe1.server.services.interfaces.AdminService;
import ar.edu.itba.pod.tpe1.server.services.interfaces.NotificationService;
import doctorPagerService.DoctorPagerServiceGrpc;
import doctorPagerService.DoctorPagerServiceOuterClass;
import io.grpc.stub.StreamObserver;
import models.doctor.DoctorOuterClass;

public class DoctorPagerServant extends DoctorPagerServiceGrpc.DoctorPagerServiceImplBase {
    private final NotificationService notificationService;
    private final AdminService adminService;

    public DoctorPagerServant(NotificationService notificationService, AdminService adminService) {
        this.notificationService = notificationService;
        this.adminService = adminService;
    }

    @Override
    public void registerDoctor(DoctorPagerServiceOuterClass.RegisterDoctorRequest request,
            StreamObserver<DoctorPagerServiceOuterClass.NotificationResponse> responseObserver) {
        DoctorPagerServiceOuterClass.NotificationResponse.Builder responseBuilder = DoctorPagerServiceOuterClass.NotificationResponse
                .newBuilder();
        if (request.getDoctorName().isEmpty()) {
            throw new IllegalArgumentException("Doctor name is required");
        }
        Doctor doctor = adminService.getDoctor(request.getDoctorName());
        notificationService.register(doctor, responseObserver);
        responseObserver
                .onNext();
    }

    @Override
    public void unregisterDoctor(DoctorPagerServiceOuterClass.UnregisterDoctorRequest request,
            StreamObserver<DoctorPagerServiceOuterClass.UnregisterDoctorResponse> responseObserver) {
        DoctorPagerServiceOuterClass.UnregisterDoctorResponse.Builder responseBuilder = DoctorPagerServiceOuterClass.UnregisterDoctorResponse
                .newBuilder();
        try {
            if (request.getDoctorName().isEmpty()) {
                throw new IllegalArgumentException("Doctor name is required");
            }
            Doctor doctor = adminService.getDoctor(request.getDoctorName());
            notificationService.unregister(doctor);
            responseBuilder.setDoctor(DoctorOuterClass.Doctor.newBuilder()
                    .setLevel(doctor.getLevel())
                    .setName(doctor.getName())
                    .setStatus(doctor.getStatus())
                    .build());
            responseBuilder.setSuccess(true);
        } catch (IllegalArgumentException e) {
            responseBuilder
                    .setSuccess(false)
                    .setErrorMessage("Invalid request: " + e.getMessage());
        } catch (RuntimeException e) {
            responseBuilder
                    .setSuccess(false)
                    .setErrorMessage("Server error: " + e.getMessage());
        }
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
