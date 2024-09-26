package ar.edu.itba.pod.tpe1.server.servants;

import administrationService.AdministrationServiceGrpc;
import administrationService.AdministrationServiceOuterClass;
import ar.edu.itba.pod.tpe1.server.models.Doctor;
import ar.edu.itba.pod.tpe1.server.services.AdminServiceImpl;
import ar.edu.itba.pod.tpe1.server.services.interfaces.AdminService;
import doctorPagerService.DoctorPagerServiceOuterClass.NotificationResponse;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import models.doctor.DoctorOuterClass;

public class AdministrationServant extends AdministrationServiceGrpc.AdministrationServiceImplBase {

    private final AdminService adminService;

    public AdministrationServant(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void addRoom(Empty request,
            StreamObserver<AdministrationServiceOuterClass.AddRoomResponse> responseObserver) {
        int newRoom = adminService.addRoom();
        AdministrationServiceOuterClass.AddRoomResponse addRoomResponse = AdministrationServiceOuterClass.AddRoomResponse
                .newBuilder().setRoomNumber(newRoom).build();
        responseObserver.onNext(addRoomResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void addDoctor(AdministrationServiceOuterClass.AddDoctorRequest request,
            StreamObserver<AdministrationServiceOuterClass.AddDoctorResponse> responseObserver) {
        AdministrationServiceOuterClass.AddDoctorResponse.Builder responseBuilder = AdministrationServiceOuterClass.AddDoctorResponse
                .newBuilder();
        try {
            if (request.getDoctorName().isEmpty()) {
                throw new IllegalArgumentException("Doctor name is required");
            }
            Doctor doctor = adminService.addDoctor(request.getDoctorName(), request.getLevel());
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
                    .setErrorMessage(e.getMessage());
        }
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void setDoctor(AdministrationServiceOuterClass.SetDoctorStatusRequest request,
            StreamObserver<AdministrationServiceOuterClass.SetDoctorStatusResponse> responseObserver) {
        AdministrationServiceOuterClass.SetDoctorStatusResponse.Builder responseBuilder = AdministrationServiceOuterClass.SetDoctorStatusResponse
                .newBuilder();
        try {
            if (request.getDoctorName().isEmpty()) {
                throw new IllegalArgumentException("Doctor name is required");
            }
            if (request.getStatus() == DoctorOuterClass.DoctorStatus.ATTENDING)
                throw new IllegalArgumentException("Can't set status to attending in this way");
            Doctor updatedDoctor = adminService.setDoctor(request.getDoctorName(), request.getStatus());
            responseBuilder.setDoctor(DoctorOuterClass.Doctor.newBuilder()
                    .setLevel(updatedDoctor.getLevel())
                    .setName(updatedDoctor.getName())
                    .setStatus(updatedDoctor.getStatus())
                    .build());
            responseBuilder.setSuccess(true);
            if (updatedDoctor.getPageable()) {
                updatedDoctor.getObserver().onNext(NotificationResponse.newBuilder()
                        .setChangeStatus(updatedDoctor.toString() + "is now " + updatedDoctor.getStatus().toString())
                        .build());
            }
        } catch (IllegalArgumentException e) {
            responseBuilder
                    .setSuccess(false)
                    .setErrorMessage("Invalid request: " + e.getMessage());
        } catch (RuntimeException e) {
            responseBuilder
                    .setSuccess(false)
                    .setErrorMessage(e.getMessage());
        }
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void checkDoctor(AdministrationServiceOuterClass.CheckDoctorStatusRequest request,
            StreamObserver<AdministrationServiceOuterClass.CheckDoctorStatusResponse> responseObserver) {
        AdministrationServiceOuterClass.CheckDoctorStatusResponse.Builder responseBuilder = AdministrationServiceOuterClass.CheckDoctorStatusResponse
                .newBuilder();
        try {
            if (request.getDoctorName().isEmpty()) {
                throw new IllegalArgumentException("Doctor name is required");
            }
            Doctor doctor = adminService.getDoctor(request.getDoctorName());
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
                    .setErrorMessage(e.getMessage());
        }
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

}
