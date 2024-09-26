package ar.edu.itba.pod.tpe1.client.waitingRoom.actions;

import ar.edu.itba.pod.tpe1.client.Action;
import io.grpc.ManagedChannel;
import waitingRoomService.WaitingRoomServiceGrpc;
import waitingRoomService.WaitingRoomServiceOuterClass;

import java.util.Collections;
import java.util.List;

public class CheckPatientAction extends Action {
    private static final String PATIENT_NAME = "patient";

    public CheckPatientAction() {
        super(List.of(PATIENT_NAME), Collections.emptyList());
    }

    @Override
    public void run(ManagedChannel managedChannel) {
        WaitingRoomServiceGrpc.WaitingRoomServiceBlockingStub blockingStub = WaitingRoomServiceGrpc.newBlockingStub(managedChannel);
        String patientName = this.arguments.get(PATIENT_NAME);
        WaitingRoomServiceOuterClass.CheckPatientWaitingTimeRequest request = WaitingRoomServiceOuterClass.CheckPatientWaitingTimeRequest.newBuilder()
                .setPatientName(patientName)
                .build();
        WaitingRoomServiceOuterClass.CheckPatientWaitingTimeResponse response = blockingStub.checkPatient(request);
        if(response.getSuccess()){
            System.out.println("Patient "+response.getPatient().getName()+" ("+response.getPatient().getLevel()+")  is in the waiting room with "+response.getPatientsAhead()+" patients ahead");
        }else{
            System.out.println(response.getErrorMessage());
        }
    }
}
