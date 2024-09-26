package ar.edu.itba.pod.tpe1.server.servants;

import ar.edu.itba.pod.tpe1.server.models.CareHistory;
import ar.edu.itba.pod.tpe1.server.models.Patient;
import ar.edu.itba.pod.tpe1.server.models.Room;
import ar.edu.itba.pod.tpe1.server.services.interfaces.QueryService;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import models.careHistory.CareHistoryOuterClass;
import models.doctor.DoctorOuterClass;
import models.patient.PatientOuterClass;
import models.room.RoomOuterClass;
import queryService.QueryServiceGrpc;
import queryService.QueryServiceOuterClass;

import java.util.*;

public class QueryServant extends QueryServiceGrpc.QueryServiceImplBase {

    QueryService queryService;

    public QueryServant(QueryService queryService) {
        this.queryService = queryService;
    }

    @Override
    public void queryRooms(Empty request, StreamObserver<QueryServiceOuterClass.QueryRoomsResponse> responseObserver) {
        QueryServiceOuterClass.QueryRoomsResponse.Builder responseBuilder = QueryServiceOuterClass.QueryRoomsResponse.newBuilder();
        try {
            List<Room> rooms = queryService.queryRooms();
            List<RoomOuterClass.Room> roomList = rooms.stream().map(room -> {
                    RoomOuterClass.Room roomProto;
                    if (room.getOccupied()) {
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
        ).toList();
        responseBuilder.addAllRooms(roomList);            
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
    public void queryWaitingRoom(Empty request, StreamObserver<QueryServiceOuterClass.QueryWaitingRoomResponse> responseObserver) {
        QueryServiceOuterClass.QueryWaitingRoomResponse.Builder responseBuilder = QueryServiceOuterClass.QueryWaitingRoomResponse.newBuilder();
        try {
            List<Patient> waitingRoom = queryService.queryWaitingRoom();
            List<PatientOuterClass.Patient> patients = waitingRoom.stream()
                    .map(patient ->
                    PatientOuterClass.Patient.newBuilder()
                            .setName(patient.getName())
                            .setLevel(patient.getEmergencyLevel())
                            .build()
            ).toList();
            responseBuilder.addAllPatients(patients);
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
    public void queryCares(QueryServiceOuterClass.QueryCaresRequest request, StreamObserver<QueryServiceOuterClass.QueryCaresResponse> responseObserver) {
        QueryServiceOuterClass.QueryCaresResponse.Builder responseBuilder = QueryServiceOuterClass.QueryCaresResponse.newBuilder();
        try {
            List<CareHistory> careHistories;
            if(request.hasRoomNumber()) {
                careHistories = queryService.queryCares(request.getRoomNumber());
            }else{
                careHistories = queryService.queryCares(null);
            }
            List<CareHistoryOuterClass.CareHistory> careHistoryList = careHistories.stream().map(careHistory ->
                    CareHistoryOuterClass.CareHistory.newBuilder().
                            setDoctor(
                                    DoctorOuterClass.Doctor.newBuilder()
                                            .setName(careHistory.getDoctor().getName())
                                            .setLevel(careHistory.getDoctor().getLevel())
                                            .setStatus(careHistory.getDoctor().getStatus())
                                            .build())
                            .setPatient(
                                    PatientOuterClass.Patient.newBuilder()
                                            .setName(careHistory.getPatient().getName())
                                            .setLevel(careHistory.getPatient().getEmergencyLevel())
                                            .build()
                            )
                            .setRoomNumber(careHistory.getRoomId())
                            .build()
            ).toList();
            responseBuilder.addAllCareHistory(careHistoryList);
            responseBuilder.setSuccess(true);
        } catch (RuntimeException e) {
            responseBuilder
                    .setSuccess(false)
                    .setErrorMessage(e.getMessage());
        }
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
