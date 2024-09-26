package ar.edu.itba.pod.tpe1.client.query.actions;

import administrationService.AdministrationServiceGrpc;
import administrationService.AdministrationServiceOuterClass;
import ar.edu.itba.pod.tpe1.client.Action;
import io.grpc.ManagedChannel;
import models.room.RoomOuterClass.Room;
import queryService.QueryServiceGrpc;
import queryService.QueryServiceOuterClass.QueryRoomsResponse;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import com.google.protobuf.Empty;

public class QueryRoomsAction extends Action {

    private static final String PATH = "outPath";

    public QueryRoomsAction() {
        super(List.of(PATH), Collections.emptyList());
    }

    @Override
    public void run(ManagedChannel managedChannel) {
        QueryServiceGrpc.QueryServiceBlockingStub blockingStub = QueryServiceGrpc.newBlockingStub(managedChannel);
        String path = this.arguments.get(PATH);
        QueryRoomsResponse response = blockingStub.queryRooms(Empty.newBuilder().build());
        if (response.getSuccess()) {
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                out.println("Room,Status,Patient,Doctor");
                for (Room r : response.getRoomsList()) {
                    if (r.getOccupied()) {
                        out.println(r.getRoomNumber() + "," + r.getOccupied() + "," + r.getPatient().getName() + " ("
                                + r.getPatient().getLevel() + ")"
                                + "," + r.getDoctor().getName() + " (" + r.getDoctor().getLevel() + ")");
                    } else {
                        out.println(r.getRoomNumber() + ",Free," + ",");
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println(response.getErrorMessage());
        }

    }
}
