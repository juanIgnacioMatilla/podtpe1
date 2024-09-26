package ar.edu.itba.pod.tpe1.client.admin.actions;

import administrationService.AdministrationServiceGrpc;
import administrationService.AdministrationServiceOuterClass;
import ar.edu.itba.pod.tpe1.client.Action;
import io.grpc.ManagedChannel;
import models.doctor.DoctorOuterClass.DoctorStatus;

import java.util.Collections;
import java.util.List;

public class SetDoctorAction extends Action {

    private static final String DOCTOR_NAME = "doctor";
    private static final String DOCTOR_AVAILABILITY = "availability";

    public SetDoctorAction() {
        super(List.of(DOCTOR_NAME, DOCTOR_AVAILABILITY), Collections.emptyList());
    }

    @Override
    public void run(ManagedChannel managedChannel) {
        AdministrationServiceGrpc.AdministrationServiceBlockingStub blockingStub = AdministrationServiceGrpc.newBlockingStub(managedChannel);
        String doctorName = this.arguments.get(DOCTOR_NAME);
        DoctorStatus doctorAvailability = getAvailabilityStatus(this.arguments.get(DOCTOR_AVAILABILITY));
        AdministrationServiceOuterClass.SetDoctorStatusRequest setDoctorRequest = AdministrationServiceOuterClass.SetDoctorStatusRequest.newBuilder()
                .setDoctorName(doctorName)
                .setStatus(doctorAvailability)
                .build();
        AdministrationServiceOuterClass.SetDoctorStatusResponse response = blockingStub.setDoctor(setDoctorRequest);
        if(response.getSuccess()){
            System.out.println("Doctor "+response.getDoctor().getName()+" ("+response.getDoctor().getLevel()+") is "+response.getDoctor().getStatus().toString().toLowerCase());
        }else{
            System.out.println(response.getErrorMessage());
        }
    }

    private DoctorStatus getAvailabilityStatus(String availability){
        if(availability.compareTo("available") == 0)
            return DoctorStatus.AVAILABLE;
        if(availability.compareTo("attending") == 0)
            return DoctorStatus.ATTENDING;
        return DoctorStatus.UNAVAILABLE;
    }
}
