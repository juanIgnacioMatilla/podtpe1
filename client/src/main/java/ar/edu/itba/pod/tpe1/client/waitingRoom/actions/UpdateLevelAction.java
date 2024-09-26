package ar.edu.itba.pod.tpe1.client.waitingRoom.actions;

import ar.edu.itba.pod.tpe1.client.Action;
import io.grpc.ManagedChannel;
import waitingRoomService.WaitingRoomServiceGrpc;
import waitingRoomService.WaitingRoomServiceOuterClass;

import java.util.Collections;
import java.util.List;

public class UpdateLevelAction extends Action {
    private static final String PATIENT_NAME = "patient";
    private static final String PATIENT_LEVEL = "level";

    public UpdateLevelAction() {
        super(List.of(PATIENT_NAME, PATIENT_LEVEL), Collections.emptyList());
    }

    @Override
    public void run(ManagedChannel managedChannel) {
        WaitingRoomServiceGrpc.WaitingRoomServiceBlockingStub blockingStub = WaitingRoomServiceGrpc.newBlockingStub(managedChannel);
        String patientName = this.arguments.get(PATIENT_NAME);
        Integer patientLevel = Integer.valueOf(this.arguments.get(PATIENT_LEVEL));
        WaitingRoomServiceOuterClass.UpdatePatientLevelRequest request = WaitingRoomServiceOuterClass.UpdatePatientLevelRequest.newBuilder()
                .setPatientName(patientName)
                .setNewEmergencyLevel(patientLevel)
                .build();
        WaitingRoomServiceOuterClass.UpdatePatientLevelResponse response = blockingStub.updatePatientLevel(request);
        if(response.getSuccess()){
            System.out.println("Patient "+response.getPatient().getName()+" ("+response.getPatient().getLevel()+") is in the waiting room");
        }else{
            System.out.println(response.getErrorMessage());
        }
    }
}
