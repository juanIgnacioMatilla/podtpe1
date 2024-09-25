package ar.edu.itba.pod.tpe1.client.notification;

import administrationService.AdministrationServiceGrpc;
import administrationService.AdministrationServiceOuterClass;
import ar.edu.itba.pod.tpe1.client.Action;
import doctorPagerService.DoctorPagerServiceGrpc;
import doctorPagerService.DoctorPagerServiceOuterClass.RegisterDoctorRequest;
import doctorPagerService.DoctorPagerServiceOuterClass.RegisterDoctorResponse;
import io.grpc.ManagedChannel;

import java.util.Collections;
import java.util.List;

public class RegisterAction extends Action {

    private static final String DOCTOR_NAME = "doctor";

    public RegisterAction() {
        super(List.of(DOCTOR_NAME), Collections.emptyList());
    }

    @Override
    public void run(ManagedChannel managedChannel) {
        DoctorPagerServiceGrpc.DoctorPagerServiceBlockingStub blockingStub = DoctorPagerServiceGrpc.newBlockingStub(managedChannel);
        String doctorName = this.arguments.get(DOCTOR_NAME);
        RegisterDoctorRequest registerDoctorRequest = RegisterDoctorRequest.newBuilder()
                .setDoctorName(doctorName)
                .build();
        RegisterDoctorResponse response = blockingStub.registerDoctor(registerDoctorRequest);
        if(response.getSuccess()){
            System.out.println("Doctor "+response.getDoctor().getName()+" ("+response.getDoctor().getLevel()+") registered succesfully for pager");
        }else{
            System.out.println(response.getErrorMessage());
        }
    }
}
