package ar.edu.itba.pod.tpe1.client.notification;

import ar.edu.itba.pod.tpe1.client.Action;
import doctorPagerService.DoctorPagerServiceGrpc;
import doctorPagerService.DoctorPagerServiceOuterClass.RegisterDoctorRequest;
import doctorPagerService.DoctorPagerServiceOuterClass.NotificationResponse;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.google.common.util.concurrent.ListenableFutureTask;

public class RegisterAction extends Action {

    private static final String DOCTOR_NAME = "doctor";

    public RegisterAction() {
        super(List.of(DOCTOR_NAME), Collections.emptyList());
    }

    @Override
    public void run(ManagedChannel managedChannel) {
        DoctorPagerServiceGrpc.DoctorPagerServiceStub asyncStub = DoctorPagerServiceGrpc.newStub(managedChannel);

        String doctorName = this.arguments.get(DOCTOR_NAME);

        RegisterDoctorRequest registerDoctorRequest = RegisterDoctorRequest.newBuilder()
                .setDoctorName(doctorName)
                .build();

        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<NotificationResponse> notificationObserver = new StreamObserver<NotificationResponse>() {

            @Override
            public void onNext(NotificationResponse registerDoctorResponse) {
                System.out.println("hola");
                String output = getMessage(registerDoctorResponse.getNotificationCase().getNumber(),
                        registerDoctorResponse);
                System.out.println(output);
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error in receiving notifications: " +
                        throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {

                System.out.println("Server completed sending notifications.");
                latch.countDown();
            }

            public String getMessage(int number, NotificationResponse response) {
                switch (number) {
                    case 1:
                        return response.getChangeStatus();
                    case 2:
                        return response.getAttending();
                    case 3:
                        return response.getFinishAttending();
                    case 4:
                        return response.getRegister();
                    default:
                        break;
                }
                System.out.println("error message");
                latch.countDown();
                return "";
            }
        };

        asyncStub.registerDoctor(registerDoctorRequest, notificationObserver);

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread was interrupted: " + e.getMessage());
        }

    }
}
