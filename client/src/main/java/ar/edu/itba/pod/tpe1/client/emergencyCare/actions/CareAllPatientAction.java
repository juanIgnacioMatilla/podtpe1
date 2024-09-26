package ar.edu.itba.pod.tpe1.client.emergencyCare.actions;

import ar.edu.itba.pod.tpe1.client.Action;
import com.google.protobuf.Empty;
import emergencyCareService.EmergencyCareServiceGrpc;
import emergencyCareService.EmergencyCareServiceOuterClass;
import io.grpc.ManagedChannel;

import java.util.Collections;
import java.util.List;

public class CareAllPatientAction extends Action {


    public CareAllPatientAction() {
        super(Collections.emptyList(), Collections.emptyList());
    }

    @Override
    public void run(ManagedChannel managedChannel) {
        EmergencyCareServiceGrpc.EmergencyCareServiceBlockingStub blockingStub = EmergencyCareServiceGrpc.newBlockingStub(managedChannel);
        EmergencyCareServiceOuterClass.CareAllPatientResponse response = blockingStub.careAllPatients(Empty.newBuilder().build());
        if(response.getSuccess()) {
            response.getRoomList().forEach(auxRoom -> {
                if (auxRoom.getRoom().getOccupied()) {
                    if (auxRoom.getAffected()) {
                        System.out.printf("Patient %s (%d) and Doctor %s (%d) are now in Room #%d %n",
                                auxRoom.getRoom().getPatient().getName(),
                                auxRoom.getRoom().getPatient().getLevel(),
                                auxRoom.getRoom().getDoctor().getName(),
                                auxRoom.getRoom().getDoctor().getLevel(),
                                auxRoom.getRoom().getRoomNumber()
                        );
                    } else {
                        System.out.printf("Room #%d remains Occupied %n", auxRoom.getRoom().getRoomNumber());
                    }
                } else {
                    System.out.printf("Room #%d remains Free %n", auxRoom.getRoom().getRoomNumber());
                }
            });
        }else{
            System.out.println(response.getErrorMessage());
        }
    }
}
