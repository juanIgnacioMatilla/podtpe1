package ar.edu.itba.pod.tpe1.client.waitingRoom.actions;

import administrationService.AdministrationServiceGrpc;
import administrationService.AdministrationServiceOuterClass;
import ar.edu.itba.pod.tpe1.client.Action;
import io.grpc.ManagedChannel;
import models.patient.PatientOuterClass;
import waitingRoomService.WaitingRoomServiceGrpc;
import waitingRoomService.WaitingRoomServiceOuterClass;

import java.util.Collections;
import java.util.List;

public class AddPatientAction extends Action {

    private static final String PATIENT_NAME = "patient";
    private static final String PATIENT_LEVEL = "level";

    public AddPatientAction() {
        super(List.of(PATIENT_NAME, PATIENT_LEVEL), Collections.emptyList());
    }

    @Override
    public void run(ManagedChannel managedChannel) {
        WaitingRoomServiceGrpc.WaitingRoomServiceBlockingStub blockingStub = WaitingRoomServiceGrpc.newBlockingStub(managedChannel);
        String patientName = this.arguments.get(PATIENT_NAME);
        Integer patientLevel = Integer.valueOf(this.arguments.get(PATIENT_LEVEL));
        WaitingRoomServiceOuterClass.AddPatientRequest request = WaitingRoomServiceOuterClass.AddPatientRequest.newBuilder()
                .setPatientName(patientName)
                .setEmergencyLevel(patientLevel)
                .build();
        WaitingRoomServiceOuterClass.AddPatientResponse response = blockingStub.addPatient(request);
        if(response.getSuccess()){
            System.out.println("Patient "+response.getPatient().getName()+" ("+response.getPatient().getLevel()+") is in the waiting room");
        }else{
            System.out.println(response.getErrorMessage());
        }
    }
}
