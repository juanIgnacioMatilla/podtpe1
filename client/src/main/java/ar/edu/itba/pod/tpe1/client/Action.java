package ar.edu.itba.pod.tpe1.client;

import io.grpc.ManagedChannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Action {
    protected final Map<String,String> arguments;

    public Action(List<String> requiredArguments, List<String> optionalArguments) {
        this.arguments = new HashMap<>();
        requiredArguments.forEach( arg -> {
            String argValue = System.getProperty(arg);
            if(Objects.isNull(argValue) || argValue.isEmpty())
                throw new IllegalArgumentException();
            else
                arguments.put(arg,argValue);
        });
        optionalArguments.forEach( arg -> {
            String argValue = System.getProperty(arg);
            if(Objects.nonNull(argValue) && !argValue.isEmpty())
                arguments.put(arg,argValue);
        });
    }



    public abstract void run(ManagedChannel managedChannel);
}
