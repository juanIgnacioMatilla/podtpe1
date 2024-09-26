package ar.edu.itba.pod.tpe1.client.query.actions;

import administrationService.AdministrationServiceGrpc;
import administrationService.AdministrationServiceOuterClass;
import ar.edu.itba.pod.tpe1.client.Action;
import io.grpc.ManagedChannel;
import models.careHistory.CareHistoryOuterClass.CareHistory;
import models.room.RoomOuterClass.Room;
import queryService.QueryServiceGrpc;
import queryService.QueryServiceOuterClass.QueryCaresRequest;
import queryService.QueryServiceOuterClass.QueryCaresResponse;
import queryService.QueryServiceOuterClass.QueryRoomsResponse;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import com.google.protobuf.Empty;

public class QueryCaresAction extends Action {

    private static final String PATH = "outPath";
    private static final String roomNumber = "room";
    public QueryCaresAction() {
        super(List.of(PATH), Collections.emptyList());
    }

    @Override
    public void run(ManagedChannel managedChannel) {
        QueryServiceGrpc.QueryServiceBlockingStub blockingStub = QueryServiceGrpc.newBlockingStub(managedChannel);
        String path = this.arguments.get(PATH);
        Integer room = Integer.valueOf(this.arguments.get(roomNumber));
        QueryCaresRequest queryCaresRequest = QueryCaresRequest.newBuilder().setRoomNumber(room).build();
        QueryCaresResponse response = blockingStub.queryCares(queryCaresRequest);
        try(PrintWriter out = new PrintWriter(new FileWriter(path))){
            out.println("Room,Patient,Doctor");
            for(CareHistory ch : response.getCareHistoryList() ){
                out.println(ch.getRoomNumber()+","+ch.getPatient().getName()+" (" + ch.getPatient().getLevel()+")"
                    +","+ch.getDoctor().getName() + " (" + ch.getDoctor().getLevel()+")" );
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}
