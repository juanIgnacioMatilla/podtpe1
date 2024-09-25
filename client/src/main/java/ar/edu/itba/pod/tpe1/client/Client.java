package ar.edu.itba.pod.tpe1.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Closeable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Client implements Closeable {
    protected static final Logger logger = LoggerFactory.getLogger(Client.class);

    private final ManagedChannel channel;
    private final Action action;

    public Client(String serverAddress, Action action) {
        if(Objects.isNull(serverAddress)||Objects.isNull(action))
            throw new IllegalArgumentException();
        this.action = action;
        this.channel = ManagedChannelBuilder.forTarget(serverAddress)
                .usePlaintext()
                .build();
    }

    @Override
    public void close() {
        try {
            this.channel.shutdown().awaitTermination(10, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            logger.error("Cannot close client: "+e.getMessage());
        }
    }

    public void run(){
        action.run(this.channel);
    }
}
