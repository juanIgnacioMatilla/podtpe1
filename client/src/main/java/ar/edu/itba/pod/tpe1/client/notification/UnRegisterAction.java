package ar.edu.itba.pod.tpe1.client.notification;

import administrationService.AdministrationServiceGrpc;
import administrationService.AdministrationServiceOuterClass;
import ar.edu.itba.pod.tpe1.client.Action;
import doctorPagerService.DoctorPagerServiceGrpc;
import doctorPagerService.DoctorPagerServiceOuterClass.UnregisterDoctorRequest;
import doctorPagerService.DoctorPagerServiceOuterClass.UnregisterDoctorResponse;
import io.grpc.ManagedChannel;

import java.util.Collections;
import java.util.List;

public class UnRegisterAction extends Action {

    private static final String DOCTOR_NAME = "doctor";

    public UnRegisterAction() {
        super(List.of(DOCTOR_NAME), Collections.emptyList());
    }

    @Override
    public void run(ManagedChannel managedChannel) {
        DoctorPagerServiceGrpc.DoctorPagerServiceBlockingStub blockingStub = DoctorPagerServiceGrpc.newBlockingStub(managedChannel);
        String doctorName = this.arguments.get(DOCTOR_NAME);
        UnregisterDoctorRequest registerDoctorRequest = UnregisterDoctorRequest.newBuilder()
                .setDoctorName(doctorName)
                .build();
        
        UnregisterDoctorResponse response = blockingStub.unregisterDoctor(registerDoctorRequest);
        if(response.getSuccess()){
            System.out.println("Doctor "+response.getDoctor().getName()+" ("+response.getDoctor().getLevel()+") unregistered succesfully for pager");
        }else{
            System.out.println(response.getErrorMessage());
        }
    }
}
