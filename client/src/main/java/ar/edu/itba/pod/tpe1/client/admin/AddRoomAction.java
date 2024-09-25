package ar.edu.itba.pod.tpe1.client.admin;

import administrationService.AdministrationServiceGrpc;
import administrationService.AdministrationServiceOuterClass;
import ar.edu.itba.pod.tpe1.client.Action;
import io.grpc.ManagedChannel;

import java.util.Collections;
import java.util.List;

import com.google.protobuf.Empty;

public class AddRoomAction extends Action {


    public AddRoomAction() {
        super(Collections.emptyList(), Collections.emptyList());
    }

    @Override
    public void run(ManagedChannel managedChannel) {
        AdministrationServiceGrpc.AdministrationServiceBlockingStub blockingStub = AdministrationServiceGrpc.newBlockingStub(managedChannel);
        AdministrationServiceOuterClass.AddRoomResponse response = blockingStub.addRoom(Empty.newBuilder().build());
        System.out.println("Room #"+response.getRoomNumber()+" added successfully");
    }
}
