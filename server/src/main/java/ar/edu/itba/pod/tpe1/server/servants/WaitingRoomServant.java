package ar.edu.itba.pod.tpe1.server.servants;


import ar.edu.itba.pod.tpe1.server.models.Patient;
import ar.edu.itba.pod.tpe1.server.services.interfaces.WaitingRoomService;
import io.grpc.stub.StreamObserver;
import models.patient.PatientOuterClass;
import waitingRoomService.WaitingRoomServiceGrpc;
import waitingRoomService.WaitingRoomServiceOuterClass;

public class WaitingRoomServant extends WaitingRoomServiceGrpc.WaitingRoomServiceImplBase {

    WaitingRoomService waitingRoomService;

    public WaitingRoomServant(WaitingRoomService waitingRoomService) {
        this.waitingRoomService = waitingRoomService;
    }

    @Override
    public void addPatient(WaitingRoomServiceOuterClass.AddPatientRequest request, StreamObserver<WaitingRoomServiceOuterClass.AddPatientResponse> responseObserver) {
        WaitingRoomServiceOuterClass.AddPatientResponse.Builder responseBuilder = WaitingRoomServiceOuterClass.AddPatientResponse.newBuilder();
        try {
            if (request.getPatientName().isEmpty()) {
                throw new IllegalArgumentException("Patient name is required");
            }
            if (request.getEmergencyLevel() == 0) {
                throw new IllegalArgumentException("Patient level is required");
            }
            Patient patient = waitingRoomService.addPatient(request.getPatientName(),request.getEmergencyLevel());
            responseBuilder.setPatient(PatientOuterClass.Patient.newBuilder()
                    .setLevel(patient.getEmergencyLevel())
                    .setName(patient.getName())
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
    public void updatePatientLevel(WaitingRoomServiceOuterClass.UpdatePatientLevelRequest request, StreamObserver<WaitingRoomServiceOuterClass.UpdatePatientLevelResponse> responseObserver) {
        WaitingRoomServiceOuterClass.UpdatePatientLevelResponse.Builder responseBuilder = WaitingRoomServiceOuterClass.UpdatePatientLevelResponse.newBuilder();
        try {
            if (request.getPatientName().isEmpty()) {
                throw new IllegalArgumentException("Patient name is required");
            }
            if (request.getNewEmergencyLevel() == 0) {
                throw new IllegalArgumentException("Patient level is required");
            }
            Patient patient = waitingRoomService.updateLevel(request.getPatientName(),request.getNewEmergencyLevel());
            responseBuilder.setPatient(PatientOuterClass.Patient.newBuilder()
                    .setLevel(patient.getEmergencyLevel())
                    .setName(patient.getName())
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
    public void checkPatient(WaitingRoomServiceOuterClass.CheckPatientWaitingTimeRequest request, StreamObserver<WaitingRoomServiceOuterClass.CheckPatientWaitingTimeResponse> responseObserver) {
        WaitingRoomServiceOuterClass.CheckPatientWaitingTimeResponse.Builder responseBuilder = WaitingRoomServiceOuterClass.CheckPatientWaitingTimeResponse.newBuilder();
        try {
            if (request.getPatientName().isEmpty()) {
                throw new IllegalArgumentException("Patient name is required");
            }
            Integer patientAhead = waitingRoomService.checkPatient(request.getPatientName());
            Patient patient = waitingRoomService.getPatient(request.getPatientName());
            responseBuilder.setPatient(PatientOuterClass.Patient.newBuilder()
                    .setLevel(patient.getEmergencyLevel())
                    .setName(patient.getName())
                    .build());
            responseBuilder.setPatientsAhead(patientAhead);
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
