package ar.edu.itba.pod.tpe1.server;

import ar.edu.itba.pod.tpe1.server.repositories.CareHistoryRepository;
import ar.edu.itba.pod.tpe1.server.repositories.DoctorRepository;
import ar.edu.itba.pod.tpe1.server.repositories.PatientRepository;
import ar.edu.itba.pod.tpe1.server.repositories.RoomRepository;
import ar.edu.itba.pod.tpe1.server.servants.*;
import ar.edu.itba.pod.tpe1.server.services.*;
import ar.edu.itba.pod.tpe1.server.services.interfaces.*;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class Server {
    private static Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws InterruptedException, IOException {
        logger.info(" Server Starting ...");
        int port = Optional.ofNullable(System.getProperty("port")).map(Integer::parseInt).orElse(50051);

        DoctorRepository doctorRepository = new DoctorRepository();
        CareHistoryRepository careHistoryRepository = new CareHistoryRepository();
        PatientRepository patientRepository = new PatientRepository();
        RoomRepository roomRepository = new RoomRepository();


        AdminService adminService = new AdminServiceImpl(doctorRepository,roomRepository);
        EmergencyService emergencyService = new EmergencyServiceImpl(patientRepository,doctorRepository,careHistoryRepository,roomRepository);
        NotificationService notificationService = new NotificationServiceImpl(doctorRepository);
        QueryService queryService = new QueryServiceImpl(roomRepository,patientRepository,careHistoryRepository);
        WaitingRoomService waitingRoomService = new WaitingRoomServiceImpl(patientRepository);

        io.grpc.Server server = ServerBuilder.forPort(port)
                .addService(new AdministrationServant(adminService))
                .addService(new DoctorPagerServant(notificationService,adminService))
                .addService(new EmergencyCareServant(emergencyService,queryService))
                .addService(new QueryServant(queryService))
                .addService(new WaitingRoomServant(waitingRoomService))
                .build();
        server.start();
        logger.info("Server started, listening on " + port);
        server.awaitTermination();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down gRPC server since JVM is shutting down");
            server.shutdown();
            logger.info("Server shut down");
        }));
    }}
