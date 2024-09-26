package ar.edu.itba.pod.tpe1.client.emergencyCare.actions;

import ar.edu.itba.pod.tpe1.client.Action;
import emergencyCareService.EmergencyCareServiceGrpc;
import emergencyCareService.EmergencyCareServiceOuterClass;
import io.grpc.ManagedChannel;
import waitingRoomService.WaitingRoomServiceGrpc;
import waitingRoomService.WaitingRoomServiceOuterClass;

import java.util.Collections;
import java.util.List;

public class CarePatientAction extends Action {
    private static final String ROOM_NUMBER = "room";

    public CarePatientAction() {
        super(List.of(ROOM_NUMBER), Collections.emptyList());
    }

    @Override
    public void run(ManagedChannel managedChannel) {
        EmergencyCareServiceGrpc.EmergencyCareServiceBlockingStub blockingStub = EmergencyCareServiceGrpc.newBlockingStub(managedChannel);
        Integer roomNumber = Integer.valueOf(this.arguments.get(ROOM_NUMBER));
        EmergencyCareServiceOuterClass.CarePatientRequest request = EmergencyCareServiceOuterClass.CarePatientRequest.newBuilder()
                .setRoomNumber(roomNumber)
                .build();
        EmergencyCareServiceOuterClass.CarePatientResponse response = blockingStub.carePatient(request);
        if(response.getSuccess()){
            if(response.getRoom().getOccupied()) {
                System.out.printf("Patient %s (%d) and Doctor %s (%d) are now in Room #%d %n",
                        response.getRoom().getPatient().getName(),
                        response.getRoom().getPatient().getLevel(),
                        response.getRoom().getDoctor().getName(),
                        response.getRoom().getDoctor().getLevel(),
                        response.getRoom().getRoomNumber()
                );
            }else{
                System.out.printf("Room #%d remains Free %n",response.getRoom().getRoomNumber());
            }
        }else{
            System.out.println(response.getErrorMessage());
        }
    }
}
