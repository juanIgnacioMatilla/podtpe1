package ar.edu.itba.pod.tpe1.server.servants;

import ar.edu.itba.pod.tpe1.server.Server;
import ar.edu.itba.pod.tpe1.server.models.Room;
import ar.edu.itba.pod.tpe1.server.services.interfaces.EmergencyService;
import ar.edu.itba.pod.tpe1.server.services.interfaces.QueryService;
import com.google.protobuf.Empty;
import emergencyCareService.EmergencyCareServiceGrpc;
import emergencyCareService.EmergencyCareServiceOuterClass;
import io.grpc.stub.StreamObserver;
import models.doctor.DoctorOuterClass;
import models.patient.PatientOuterClass;
import models.room.RoomOuterClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import queryService.QueryServiceOuterClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class EmergencyCareServant extends EmergencyCareServiceGrpc.EmergencyCareServiceImplBase {
    EmergencyService emergencyService;
    QueryService queryService;

    public EmergencyCareServant(EmergencyService emergencyService, QueryService queryService) {
        this.emergencyService = emergencyService;
        this.queryService = queryService;
    }

    @Override
    public void carePatient(EmergencyCareServiceOuterClass.CarePatientRequest request, StreamObserver<EmergencyCareServiceOuterClass.CarePatientResponse> responseObserver) {
        EmergencyCareServiceOuterClass.CarePatientResponse.Builder responseBuilder = EmergencyCareServiceOuterClass.CarePatientResponse.newBuilder();
        try {
            Room room = emergencyService.carePatient(request.getRoomNumber());
            responseBuilder.setRoom(roomProtoConverter(room));
            responseBuilder.setSuccess(true);
        } catch (RuntimeException e) {
            responseBuilder
                    .setSuccess(false)
                    .setErrorMessage(e.getMessage());
        }
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void careAllPatients(Empty request, StreamObserver<EmergencyCareServiceOuterClass.CareAllPatientResponse> responseObserver) {
        EmergencyCareServiceOuterClass.CareAllPatientResponse.Builder builder = EmergencyCareServiceOuterClass.CareAllPatientResponse.newBuilder();
        try {
            List<Room> affectedRooms = emergencyService.careAllPatients();
            List<Room> rooms = queryService.queryRooms();
            List<EmergencyCareServiceOuterClass.AuxRoom> auxRooms = new ArrayList<>();
            rooms.forEach(room -> {
                if (affectedRooms.contains(room)) {
                    auxRooms.add(EmergencyCareServiceOuterClass.AuxRoom.newBuilder()
                            .setAffected(true)
                            .setRoom(roomProtoConverter(room))
                            .build());
                } else {
                    auxRooms.add(EmergencyCareServiceOuterClass.AuxRoom.newBuilder()
                            .setAffected(false)
                            .setRoom(roomProtoConverter(room))
                            .build());
                }
            });
            builder.addAllRoom(auxRooms);
            builder.setSuccess(true);
        } catch (IllegalArgumentException e) {
            builder
                    .setSuccess(false)
                    .setErrorMessage("Invalid request: " + e.getMessage());
        } catch (RuntimeException e) {
            builder
                    .setSuccess(false)
                    .setErrorMessage(e.getMessage());
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void dischargePatient(EmergencyCareServiceOuterClass.DischargePatientRequest
                                         request, StreamObserver<EmergencyCareServiceOuterClass.DischargePatientResponse> responseObserver) {
        EmergencyCareServiceOuterClass.DischargePatientResponse.Builder responseBuilder = EmergencyCareServiceOuterClass.DischargePatientResponse.newBuilder();
        try {
            if (request.getDoctorName().isEmpty()) {
                throw new IllegalArgumentException("Doctor name is required");
            }
            if (request.getPatientName().isEmpty()) {
                throw new IllegalArgumentException("Patient name is required");
            }
            if (request.getRoomNumber() <= 0) {
                throw new IllegalArgumentException("Room number is invalid");
            }

            Room room = emergencyService.dischargePatient(request.getRoomNumber(), request.getDoctorName(), request.getPatientName());
            responseBuilder
                    .setDoctor(
                            DoctorOuterClass.Doctor.newBuilder()
                                    .setName(room.getDoctor().getName())
                                    .setLevel(room.getDoctor().getLevel())
                                    .setStatus(room.getDoctor().getStatus())
                                    .build())
                    .setPatient(
                            PatientOuterClass.Patient.newBuilder()
                                    .setName(room.getPatient().getName())
                                    .setLevel(room.getPatient().getEmergencyLevel())
                                    .build()
                    )
                    .setRoomNumber(room.getId())
                    .build();
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

    private RoomOuterClass.Room roomProtoConverter(Room room) {
        RoomOuterClass.Room roomProto;
        if (Objects.nonNull(room.getDoctor())) {
            roomProto = RoomOuterClass.Room.newBuilder().
                    setDoctor(
                            DoctorOuterClass.Doctor.newBuilder()
                                    .setName(room.getDoctor().getName())
                                    .setLevel(room.getDoctor().getLevel())
                                    .setStatus(room.getDoctor().getStatus())
                                    .build())
                    .setPatient(
                            PatientOuterClass.Patient.newBuilder()
                                    .setName(room.getPatient().getName())
                                    .setLevel(room.getPatient().getEmergencyLevel())
                                    .build()
                    )
                    .setOccupied(room.getOccupied())
                    .setRoomNumber(room.getId())
                    .build();

        } else {
            roomProto = RoomOuterClass.Room.newBuilder()
                    .setOccupied(room.getOccupied())
                    .setRoomNumber(room.getId())
                    .build();
        }
        return roomProto;
    }
}
