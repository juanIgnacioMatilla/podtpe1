package ar.edu.itba.pod.tpe1.client.emergencyCare.actions;

import ar.edu.itba.pod.tpe1.client.Action;
import emergencyCareService.EmergencyCareServiceGrpc;
import emergencyCareService.EmergencyCareServiceOuterClass;
import io.grpc.ManagedChannel;

import java.util.Collections;
import java.util.List;

public class DischargePatientAction extends Action {
    private static final String ROOM_NUMBER = "room";
    private static final String DOCTOR_NAME = "doctor";
    private static final String PATIENT_NAME = "patient";

    public DischargePatientAction() {
        super(List.of(ROOM_NUMBER, DOCTOR_NAME, PATIENT_NAME), Collections.emptyList());
    }

    @Override
    public void run(ManagedChannel managedChannel) {
        EmergencyCareServiceGrpc.EmergencyCareServiceBlockingStub blockingStub = EmergencyCareServiceGrpc.newBlockingStub(managedChannel);
        Integer roomNumber = Integer.valueOf(this.arguments.get(ROOM_NUMBER));
        String doctorName = this.arguments.get(DOCTOR_NAME);
        String patientName = this.arguments.get(PATIENT_NAME);

        EmergencyCareServiceOuterClass.DischargePatientRequest request = EmergencyCareServiceOuterClass.DischargePatientRequest.newBuilder()
                .setDoctorName(doctorName)
                .setPatientName(patientName)
                .setRoomNumber(roomNumber)
                .build();
        EmergencyCareServiceOuterClass.DischargePatientResponse response = blockingStub.dischargePatient(request);
        if (response.getSuccess()) {
            System.out.printf("Patient %s (%d) has been discharged from Doctor %s (%d) and the Room " +
                            "#%d is now Free %n",
                    response.getPatient().getName(),
                    response.getPatient().getLevel(),
                    response.getDoctor().getName(),
                    response.getDoctor().getLevel(),
                    response.getRoomNumber()
            );
        } else {
            System.out.println(response.getErrorMessage());
        }
    }
}
