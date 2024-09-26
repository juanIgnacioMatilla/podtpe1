package ar.edu.itba.pod.tpe1.client.query;

import administrationService.AdministrationServiceGrpc;
import administrationService.AdministrationServiceOuterClass;
import ar.edu.itba.pod.tpe1.client.Action;
import io.grpc.ManagedChannel;
import models.patient.PatientOuterClass.Patient;
import models.room.RoomOuterClass.Room;
import queryService.QueryServiceGrpc;
import queryService.QueryServiceOuterClass.QueryRoomsResponse;
import queryService.QueryServiceOuterClass.QueryWaitingRoomResponse;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import com.google.protobuf.Empty;

public class QueryWaitingRoomAction extends Action {

    private static final String PATH = "outPath";

    public QueryWaitingRoomAction() {
        super(List.of(PATH), Collections.emptyList());
    }

    @Override
    public void run(ManagedChannel managedChannel) {
        QueryServiceGrpc.QueryServiceBlockingStub blockingStub = QueryServiceGrpc.newBlockingStub(managedChannel);
        String path = this.arguments.get(PATH);

        QueryWaitingRoomResponse response = blockingStub.queryWaitingRoom(Empty.newBuilder().build());
        if (response.getSuccess()) {
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                out.println("Patient,Level");
                for (Patient p : response.getPatientsList()) {
                    out.println(p.getName() + ",(" + p.getLevel() + ")");
                }
            } catch (Exception e) {
                // logger.error(e.getMessage());
            }
        } else {
            System.out.println(response.getErrorMessage());
        }
    }
}
