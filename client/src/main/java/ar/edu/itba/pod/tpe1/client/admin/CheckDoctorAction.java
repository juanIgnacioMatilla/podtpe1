package ar.edu.itba.pod.tpe1.client.admin;

import administrationService.AdministrationServiceGrpc;
import administrationService.AdministrationServiceOuterClass;
import ar.edu.itba.pod.tpe1.client.Action;
import io.grpc.ManagedChannel;
import models.doctor.DoctorOuterClass.DoctorStatus;

import java.util.Collections;
import java.util.List;

public class CheckDoctorAction extends Action {

    private static final String DOCTOR_NAME = "doctor";

    public CheckDoctorAction() {
        super(List.of(DOCTOR_NAME), Collections.emptyList());
    }

    @Override
    public void run(ManagedChannel managedChannel) {
        AdministrationServiceGrpc.AdministrationServiceBlockingStub blockingStub = AdministrationServiceGrpc.newBlockingStub(managedChannel);
        String doctorName = this.arguments.get(DOCTOR_NAME);
        AdministrationServiceOuterClass.CheckDoctorStatusRequest checkDoctorRequest = AdministrationServiceOuterClass.CheckDoctorStatusRequest.newBuilder()
                .setDoctorName(doctorName)
                .build();
        AdministrationServiceOuterClass.CheckDoctorStatusResponse response = blockingStub.checkDoctor(checkDoctorRequest);
        if(response.getSuccess()){
            System.out.println("Doctor "+response.getDoctor().getName()+" ("+response.getDoctor().getLevel()+") is "+response.getDoctor().getStatus().toString().toLowerCase());
        }else{
            System.out.println(response.getErrorMessage());
        }
    }

}
