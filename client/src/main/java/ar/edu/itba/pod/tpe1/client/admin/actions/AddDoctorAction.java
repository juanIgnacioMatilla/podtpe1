package ar.edu.itba.pod.tpe1.client.admin.actions;

import administrationService.AdministrationServiceGrpc;
import administrationService.AdministrationServiceOuterClass;
import ar.edu.itba.pod.tpe1.client.Action;
import io.grpc.ManagedChannel;

import java.util.Collections;
import java.util.List;

public class AddDoctorAction extends Action {

    private static final String DOCTOR_NAME = "doctor";
    private static final String DOCTOR_LEVEL = "level";

    public AddDoctorAction() {
        super(List.of(DOCTOR_NAME, DOCTOR_LEVEL), Collections.emptyList());
    }

    @Override
    public void run(ManagedChannel managedChannel) {
        AdministrationServiceGrpc.AdministrationServiceBlockingStub blockingStub = AdministrationServiceGrpc.newBlockingStub(managedChannel);
        String doctorName = this.arguments.get(DOCTOR_NAME);
        Integer doctorLevel = Integer.valueOf(this.arguments.get(DOCTOR_LEVEL));
        AdministrationServiceOuterClass.AddDoctorRequest addDoctorRequest = AdministrationServiceOuterClass.AddDoctorRequest.newBuilder()
                .setDoctorName(doctorName)
                .setLevel(doctorLevel)
                .build();
        AdministrationServiceOuterClass.AddDoctorResponse response = blockingStub.addDoctor(addDoctorRequest);
        if(response.getSuccess()){
            System.out.println("Doctor "+response.getDoctor().getName()+" ("+response.getDoctor().getLevel()+") added successfully");
        }else{
            System.out.println(response.getErrorMessage());
        }
    }
}
